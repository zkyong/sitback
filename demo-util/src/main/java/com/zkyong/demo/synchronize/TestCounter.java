package com.zkyong.demo.synchronize;

public class TestCounter {
    public static void main(String[] args) {
//        Counter counter = new Counter();
//        Thread thread1 = new Thread(counter, "A");
//        Thread thread2 = new Thread(counter, "B");
//        thread1.start();
//        thread2.start();
        
        System.out.println(sum(50));
    }
    public static int sum(int i){
        int sum = 0;
        for (int j = 0; j < i; j++) {
            if(i==1){
                return sum + 1;
            }else{
                sum = sum + sum(i);
            }
        }
        return sum;
    }
}
