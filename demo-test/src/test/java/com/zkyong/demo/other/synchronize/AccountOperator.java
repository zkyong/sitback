package com.zkyong.demo.other.synchronize;

import java.util.Random;

/**
 * 账户操作类
 */
class AccountOperator implements Runnable {
    private Account account;

    public AccountOperator(Account account) {
        this.account = account;
    }

    public void run() {
        synchronized (account) {
            Random rd = new Random();
            int i = rd.nextInt(100);
            System.out.println(i);
            if (i > 50) {
                account.deposit(500);
                System.out.println("deposit, remain:" + account.getBalance());
            } else {
                account.withdraw(500);
                System.out.println("withdraw, remain:" + account.getBalance());
            }
            System.out.println(Thread.currentThread().getName() + ":" + account.getBalance());
        }
    }
}