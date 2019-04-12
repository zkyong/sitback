package com.zkyong.demo.headfirst.chapter01;

import com.zkyong.demo.headfirst.chapter01.impl.FlyWithWings;
import com.zkyong.demo.headfirst.chapter01.impl.Quack;

public class MallardDuck extends Duck {
    public MallardDuck() {
        quackBehavior = new Quack();
        flyBehavior = new FlyWithWings();
    }

    public void display() {
        System.out.println("I’m a real Mallard duck");
    }
}