/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.hangyang.knnspark.run;

import biz.hangyang.knnspark.inf.Entity;
import biz.hangyang.knnspark.spark.KNNClassifySpark;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import scala.Tuple2;

/**
 *
 * @author hangyang
 */
public class Run {

    public static void main(String[] args) {
        //训练数据路径
        final String trainingDataPath = "hdfs://localhost:9000/user/hangyang/knn/training2.txt";
        //测试数据路径
        String testingDataPath = "hdfs://localhost:9000/user/hangyang/knn/testing2.txt";
        //测试数据路径
        String resultDataPath = "hdfs://localhost:9000/user/hangyang/knn/result";
        //K值大小
        final int k = 10;

        //初始化spark配置
        SparkConf conf = new SparkConf().setAppName("KNN");
//        conf.setMaster("spark://hadoop01.audaque:7060");
        conf.setMaster("local[2]");
//        conf.set("spark.executor.memory", "1g");
//        conf.set("spark.cores.max", "20");
        int partition = 2;
        JavaSparkContext sc = new JavaSparkContext(conf);

        JavaPairRDD<Entity, Object> eoRDD = KNNClassifySpark.calKDistance(trainingDataPath, testingDataPath, k, sc, partition);

        JavaRDD<String> strRDD = eoRDD.map(new Function<Tuple2<Entity, Object>, String>() {
            @Override
            public String call(Tuple2<Entity, Object> v1) throws Exception {
                return "预测类别：" + String.valueOf(v1._2()) + ",实际类别：" + v1._1().toString();
            }
        });
        strRDD.saveAsTextFile(resultDataPath);

    }

}
