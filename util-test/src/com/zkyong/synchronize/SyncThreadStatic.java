package com.zkyong.synchronize;

/**
 * 同步线程
 */
class SyncThreadStatic implements Runnable {
   private static int count;

   public SyncThreadStatic() {
      count = 0;
   }

   public synchronized static void method() {
      for (int i = 0; i < 5; i ++) {
         try {
            System.out.println(Thread.currentThread().getName() + ":" + (count++));
            Thread.sleep(100);
         } catch (InterruptedException e) {
            e.printStackTrace();
         }
      }
   }

   public synchronized void run() {
      method();
   }
}