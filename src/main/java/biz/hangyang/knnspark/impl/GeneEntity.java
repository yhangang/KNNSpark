/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.hangyang.knnspark.impl;

import biz.hangyang.knnspark.inf.Entity;

/**
 * 企业时段基因的Entity的实现
 *
 * @author hangyang
 */
public class GeneEntity extends Entity {

    //初始化样本类，'',"分隔的数据第一条为类别，后面为时段基因
    public GeneEntity(String line) {
        String[] str = line.split(",", -1);
        this.category = (String) str[0];
        this.attrbutes = new String[str.length - 1];
        for (int i = 1; i < str.length; i++) {
            this.attrbutes[i - 1] = str[i];
        }
    }

    @Override
    public double distance(Entity entity) {
        Double distance = 0.0;
        //计算时段基因海明距离
        for (int i = 0; i < this.attrbutes.length; i++) {
            //时段基因不等长，返回无限大距离
            if(((String)this.attrbutes[i]).length()!=((String)entity.attrbutes[i]).length()){
                return Double.MAX_VALUE;
            }else{
                for(int j=0;j<((String)this.attrbutes[i]).length();j++){
                    if(((String)this.attrbutes[i]).charAt(j)!=((String)entity.attrbutes[i]).charAt(j)){
                        distance=distance+1;
                    }
                }
            }
        }
        return distance;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.category).append(",");
        for (int i = 0; i < this.attrbutes.length; i++) {
            sb.append(this.attrbutes[i]).append(",");
        }
        return sb.substring(0, sb.length() - 1);
    }

//        public static void main(String[] args) {
//        Entity entity = new GeneEntity("ST,000");
//        Entity entity2 = new GeneEntity("非ST,111");
//        System.out.println(entity.distance(entity2));
//        System.out.println(entity.category);
//         System.out.println(entity.toString());
//    }
}
