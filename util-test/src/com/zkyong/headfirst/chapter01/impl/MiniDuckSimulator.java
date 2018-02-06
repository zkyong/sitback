package com.zkyong.headfirst.chapter01.impl;

import com.zkyong.headfirst.chapter01.Duck;
import com.zkyong.headfirst.chapter01.MallardDuck;
import com.zkyong.headfirst.chapter01.ModelDuck;

public class MiniDuckSimulator {
    public static void main(String[] args) {
        Duck mallard = new MallardDuck();
        mallard.performQuack();
        mallard.performFly();
        
        Duck model = new ModelDuck();
        model.performQuack();
        model.performFly();
        model.setFlyBehavior(new FlyRocketPowered());
        model.performFly();
    }
}
