/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.hangyang.knnspark.inf;

/**
 * 该类是每条样本数据的抽象
 *
 * @author hangyang
 */
public abstract class Entity {

    //该样本的类别
    public Object category;
    //该样本的属性集合
    public Object[] attrbutes;

    //计算样本间的距离
    public abstract double distance(Entity entity);

}
