/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.hangyang.knnspark.spark;

import biz.hangyang.knnspark.impl.DemoDistanceCatagory;
import biz.hangyang.knnspark.impl.DemoEntity;
import biz.hangyang.knnspark.inf.Entity;
import biz.hangyang.knnspark.tools.KDistance;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.PairFlatMapFunction;
import org.apache.spark.api.java.function.PairFunction;
import scala.Tuple2;

/**
 * 读取源数据，生成每条测试数据对应的K个最小距离及对应分类，然后返回预测的分类
 *
 * @author hangyang
 */
public class KNNClassifySpark {

    public static JavaPairRDD<Entity, Object> calKDistance(final String trainingDataPath, String testingDataPath, final int k,
            JavaSparkContext sc, int partition) {
        JavaRDD<String> testingDataRDD = sc.textFile(testingDataPath, partition);
        //将文本数据转化为Entity类
        JavaRDD<Entity> testingEntityRDD = testingDataRDD.map(new Function<String, Entity>() {
            @Override
            public Entity call(String line) throws Exception {
                return new DemoEntity(line);
            }
        });
        //读取训练数据，依次计算距离，保留最小的K个距离，生成KV对
        JavaPairRDD<Entity, KDistance> ekRDD = testingEntityRDD.mapPartitionsToPair(new PairFlatMapFunction<Iterator<Entity>, Entity, KDistance>() {
            @Override
            public Iterable<Tuple2<Entity, KDistance>> call(Iterator<Entity> t) throws Exception {
                //初始化该PARTITION的测试数据
                List<Entity> entityList = new ArrayList<>();
                while (t.hasNext()) {
                    entityList.add(t.next());
                }
                //初始化距离LIST
                List<KDistance> kDistanceList = new ArrayList<>();
                for (int i = 0; i < entityList.size(); i++) {
                    kDistanceList.add(new KDistance(k));
                }

                //流式读取hdfs文本文件
                Configuration conf = new Configuration();
                FileSystem fs = FileSystem.get(URI.create(trainingDataPath), conf);
                FSDataInputStream in = fs.open(new Path(trainingDataPath));
                BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                String line;
                while ((line = br.readLine()) != null) {
                    Entity lineEntity = new DemoEntity(line);
                    for (int i = 0; i < entityList.size(); i++) {
                        kDistanceList.get(i).add(new DemoDistanceCatagory(lineEntity.distance(entityList.get(i)), lineEntity.category));
                    }
                }

                List<Tuple2<Entity, KDistance>> tList = new ArrayList<>();
                for (int i = 0; i < entityList.size(); i++) {
                    tList.add(new Tuple2<>(entityList.get(i), kDistanceList.get(i)));
                }
                return tList;
            }
        });
        JavaPairRDD<Entity, Object> eoRDD = ekRDD.mapToPair(new PairFunction<Tuple2<Entity, KDistance>, Entity, Object>() {
            @Override
            public Tuple2<Entity, Object> call(Tuple2<Entity, KDistance> t) throws Exception {
                KDistance kDistance = t._2();
                Object catagory = KDistance.getCatagory(kDistance.get());
                return new Tuple2<>(t._1(), catagory);
            }
        });

        return eoRDD;
    }
}
