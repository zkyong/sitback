package com.zkyong.synchronize;

public class TestAccountOperator {
    public static void main(String[] args) {
        Account account = new Account("zhang san");
        AccountOperator accountOperator = new AccountOperator(account);
        final int THREAD_NUM = 5;
        Thread threads[] = new Thread[THREAD_NUM];
        //因为AccountOperator类对account对象进行加锁,使用同一个account对象时都会有同步锁
        for (int i = 0; i < 5; i++) {
            threads[i] = new Thread(accountOperator, "Thread" + i);
            threads[i].start();
        }
        
        //因为AccountOperator类对account对象进行加锁,使用同一个account对象时都会有同步锁
//        for (int i = 0; i < 5; i++) {
//            threads[i] = new Thread(new AccountOperator(account), "Thread" + i);
//            threads[i].start();
//        }
        
        //因为AccountOperator类对account对象进行加锁,使用不同的account对象时不会有同步锁
//        for (int i = 0; i < 5; i++) {
//            threads[i] = new Thread(new AccountOperator(new Account("zhang san")), "Thread" + i);
//            threads[i].start();
//        }
    }
}
