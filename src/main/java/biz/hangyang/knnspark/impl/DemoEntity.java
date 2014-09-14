/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package biz.hangyang.knnspark.impl;

import biz.hangyang.knnspark.inf.Entity;

/**
 *根据实际数据类型对抽象类Entity的实现
 * @author hangyang
 */
public class DemoEntity extends Entity{
    
    //初始化样本类，'',"分隔的数据第一条为类别，后面依次为属性
    public DemoEntity(String line){
        String[] str = line.split(",", -1);
        this.category = (String)str[0];
        this.attrbutes = new Double[str.length-1];
        for(int i=1;i<str.length;i++){
            this.attrbutes[i-1]=Double.valueOf(str[i]);
        }
    }
    
    @Override
    public double distance(Entity entity){
        Double distance=0.0;
        //计算样本间欧式距离
        for(int i=0;i<this.attrbutes.length;i++){
            distance += Math.pow((Double)this.attrbutes[i]-(Double)entity.attrbutes[i], 2);
        }
        distance = Math.pow(distance, 0.5);
        return distance;
    }
    
}
