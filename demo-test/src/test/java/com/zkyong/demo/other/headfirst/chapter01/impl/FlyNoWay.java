package com.zkyong.demo.other.headfirst.chapter01.impl;

import com.zkyong.demo.other.headfirst.chapter01.FlyBehavior;

public class FlyNoWay implements FlyBehavior {
    public void fly() {
        System.out.println("I can’t fly");
    }
}