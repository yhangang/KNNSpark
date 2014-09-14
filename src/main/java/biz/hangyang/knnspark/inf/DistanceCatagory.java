/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.hangyang.knnspark.inf;

/**
 * 输入样本与训练样本的距离及对应的分类
 *
 * @author hangyang
 */
public abstract class DistanceCatagory {

    //输入样本与训练样本的距离
    public Double distance;
    //训练样本的类别
    public Object category;

    public abstract Double getDistance();

    public abstract Double getCategory();
}
