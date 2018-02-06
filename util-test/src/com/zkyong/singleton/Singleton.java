package com.zkyong.singleton;
public class Singleton {
    private Singleton() {
        System.out.println("new Singleton()");
    }

    private static class SingletoContainer {
        private static Singleton instance = new Singleton();
    }

    public static Singleton getInstance() {
        return SingletoContainer.instance;
    }
}
