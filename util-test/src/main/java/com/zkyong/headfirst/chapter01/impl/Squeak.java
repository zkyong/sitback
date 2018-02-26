package com.zkyong.headfirst.chapter01.impl;

import com.zkyong.headfirst.chapter01.QuackBehavior;

public class Squeak implements QuackBehavior {
    public void quack() {
        System.out.println("Squeak");
    }
}