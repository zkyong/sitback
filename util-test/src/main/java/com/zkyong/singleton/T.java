package com.zkyong.singleton;
public class T {

    private static volatile T instance;

    private T() {
    }

    public static T getInstance() {
        if (null == instance) {
            synchronized (T.class) {
                if (null == instance) {
                    instance = new T();
                }
            }
        }

        return instance;
    }

}