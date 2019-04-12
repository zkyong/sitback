package com.zkyong.demo.headfirst.chapter01;

import com.zkyong.demo.headfirst.chapter01.impl.FlyNoWay;
import com.zkyong.demo.headfirst.chapter01.impl.Quack;

public class ModelDuck extends Duck {
    public ModelDuck() {
        flyBehavior = new FlyNoWay();
        quackBehavior = new Quack();
    }

    public void display() {
        System.out.println("I’m a model duck");
    }
}