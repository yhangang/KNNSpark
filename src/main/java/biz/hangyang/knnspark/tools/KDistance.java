/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package biz.hangyang.knnspark.tools;

import biz.hangyang.knnspark.impl.DemoDistanceCatagory;
import biz.hangyang.knnspark.inf.DistanceCatagory;

/**
 *用于存放前K个最小的距离及类别
 * @author hangyang
 */
public class KDistance {
    //存放最小的K个距离及对应类别
    DistanceCatagory[] distances;
    //构造函数
    public KDistance(int k){
        distances = new DemoDistanceCatagory[k];
    }
    
    public void add(DistanceCatagory distance){
        for(int i=0;i<distances.length;i++){
            
        }
    }
    
}
