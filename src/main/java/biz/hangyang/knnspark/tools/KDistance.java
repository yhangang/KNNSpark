/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.hangyang.knnspark.tools;

import biz.hangyang.knnspark.impl.DemoDistanceCatagory;
import biz.hangyang.knnspark.inf.DistanceCatagory;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * 用于存放前K个最小的距离及类别，主要作用是实现自动排序 前K个距离从小到大排列
 *
 * @author hangyang
 */
public class KDistance {

    //存放最小的K个距离及对应类别
    DistanceCatagory[] distances;

    //构造函数
    public KDistance(int k) {
        //初始化数组长度K
        distances = new DistanceCatagory[k];
        //初始化数组元素，距离赋最大浮点值
        for (int i = 0; i < distances.length; i++) {
            distances[i] = new DemoDistanceCatagory(Double.MAX_VALUE, null);
        }
    }

    /**
     * 将distance加入最小距离数组，根据距离大小决定是否加入
     *
     * @param distance
     */
    public void add(DistanceCatagory distance) {
        //如果该距离比最小距离数组中最大距离要大，则舍弃，数组中最后一位是最大距离
        if (distance.distance >= distances[distances.length - 1].distance) {
            return;
        }
        //否则依次向前比较，找到合适的位置插入，形成新的有序距离
        for (int i = distances.length - 2; i >= 0; i--) {
            if (distance.distance >= distances[i].distance) {
                distances[i + 1] = distance;
                return;
            } else {
                distances[i + 1] = distances[i];
                if (i == 0) {
                    distances[i] = distance;
                }
            }
        }
    }

    /**
     * 返回最小距离数组
     *
     * @return
     */
    public DistanceCatagory[] get() {
        return distances;
    }

    /**
     * 从K个最近邻点中找出出现频率最大的类别
     *
     * @param distances
     * @return
     */
    public static Object getCatagory(DistanceCatagory[] distances) {
        Map<Object, Integer> map = new TreeMap<>();
        //记录最大频率及对应的类别
        Object category = null;
        int maxFreq = -1;

        for (int i = 0; i < distances.length; i++) {
            if (map.get(distances[i].category) != null) {
                int value = map.get(distances[i].category);
                map.put(distances[i].category, value + 1);
            } else {
                map.put(distances[i].category, 1);
            }
        }
        for (Object key : map.keySet()) {
            Integer count = map.get(key);
            if (count > maxFreq) {
                maxFreq = count;
                category = key;
            }
        }
        return category;
    }

    /**
     *重载getCatagory()方法
     * 预先定义类别和对应权值，找出类别最多的那个
     *
     * @param distances
     * @param weightMap
     * @return
     */
    public static Object getCatagory(DistanceCatagory[] distances, Map<Object, Double> weightMap) {
        //存放各类别样本的数量
        Map<Object, Double> map = new TreeMap<>();
        Object category = null;
        Double maxFreq = -1.0;

        for (Object key : weightMap.keySet()) {
            map.put(key, 0.0);
        }
        for (int i = 0; i < distances.length; i++) {
            if (map.containsKey(distances[i].category)) {
                Double count = map.get(distances[i].category);
                count = count + 1;
                map.put(distances[i].category, count);
            }
        }
        for (Object key : map.keySet()) {
            Double count = map.get(key);
            count = count * weightMap.get(key);
            if (count > maxFreq) {
                maxFreq = count;
                category = key;
            }
        }
        return category;
    }

//    public static void main(String[] args) {
//        Map<Object, Double> weightMap = new HashMap<>();
//        weightMap.put("男", 0.5);
//        weightMap.put("女", 0.5);
//        KDistance k = new KDistance(6);
//        k.add(new DemoDistanceCatagory(3.0, "男"));
//        k.add(new DemoDistanceCatagory(2.0, "男"));
//        k.add(new DemoDistanceCatagory(1.0, "男"));
//        k.add(new DemoDistanceCatagory(1.5, "女"));
//        k.add(new DemoDistanceCatagory(0.3, "女"));
//        k.add(new DemoDistanceCatagory(0.3, "女"));
//        for (int i = 0; i < k.distances.length; i++) {
//            System.out.println(k.distances[i].distance);
//        }
//        System.out.println(KDistance.getCatagory(k.get()));
//    }
}
