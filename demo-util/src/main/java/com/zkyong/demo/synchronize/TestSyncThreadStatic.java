package com.zkyong.demo.synchronize;

public class TestSyncThreadStatic {
    public static void main(String[] args) {
        SyncThreadStatic syncThread1 = new SyncThreadStatic();
        SyncThreadStatic syncThread2 = new SyncThreadStatic();
        Thread thread1 = new Thread(syncThread1, "SyncThread1");
        Thread thread2 = new Thread(syncThread2, "SyncThread2");
        thread1.start();
        thread2.start();
    }
}
