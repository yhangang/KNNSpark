/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.hangyang.knnspark.run;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;

/**
 *
 * @author hangyang
 */
public class Run {

    public static void main(String[] args) {
        String srcFile = "hdfs://localhost:9000/user/hangyang/match/schema.txt";
        String srcFile1 = "hdfs://localhost:9000/user/hangyang/match/pattern.txt";

        //初始化spark配置
        SparkConf conf = new SparkConf().setAppName("Schema Matching");
//        conf.setMaster("spark://hadoop01.audaque:7060");
        conf.setMaster("local[2]");
//        conf.set("spark.executor.memory", "1g");
//        conf.set("spark.cores.max", "20");
        JavaSparkContext sc = new JavaSparkContext(conf);
    }

}
