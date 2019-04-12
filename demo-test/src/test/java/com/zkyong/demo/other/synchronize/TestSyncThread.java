package com.zkyong.demo.other.synchronize;

public class TestSyncThread {
    public static void main(String[] args) throws InterruptedException {
        SyncThread syncThread = new SyncThread();
        Thread thread1 = new Thread(syncThread, "SyncThread1");
        Thread thread2 = new Thread(syncThread, "SyncThread2");
        thread1.start();
        thread2.start();
        System.out.println("============");
        Thread.sleep(5000);
        
        Thread thread3 = new Thread(new SyncThread(), "SyncThread1");
        Thread thread4 = new Thread(new SyncThread(), "SyncThread2");
        thread3.start();
        thread4.start();
        System.out.println("============");
        Thread.sleep(5000);
        // <=这两种方式等同=>
        SyncThread syncThread1 = new SyncThread();
        SyncThread syncThread2 = new SyncThread();
        Thread thread5 = new Thread(syncThread1, "SyncThread1");
        Thread thread6 = new Thread(syncThread2, "SyncThread2");
        thread5.start();
        thread6.start();
        System.out.println("============");
    }
}
