package com.zkyong.headfirst.chapter01.impl;

import com.zkyong.headfirst.chapter01.FlyBehavior;

public class FlyWithWings implements FlyBehavior {

    @Override
    public void fly() {
        System.out.println("I’m flying!!");
    }

}
