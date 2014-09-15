/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package biz.hangyang.knnspark.impl;

import biz.hangyang.knnspark.inf.DistanceCatagory;

/**
 *DistanceCatagory的实现
 * @author hangyang
 */
public class DemoDistanceCatagory  extends DistanceCatagory{
    //初始化类别和距离
  public DemoDistanceCatagory(Double distance,Object category){
      this.distance = distance;
      this.category = (String)category;
  }

    @Override
    public Double getDistance() {
        return this.distance;
    }

    @Override
    public Object getCategory() {
        return (String)this.category;
    }
    
}
