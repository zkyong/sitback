package com.zkyong.demo.other.headfirst.chapter01.impl;

import com.zkyong.demo.other.headfirst.chapter01.QuackBehavior;

public class Quack implements QuackBehavior {
    public void quack() {
        System.out.println("Quack");
    }
}