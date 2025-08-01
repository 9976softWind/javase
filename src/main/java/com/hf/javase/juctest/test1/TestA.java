package com.hf.javase.juctest.test1;


import java.util.concurrent.atomic.AtomicInteger;

public class TestA {

    private static AtomicInteger count = new AtomicInteger(0);
    private static volatile boolean flag = true;

    public static void main(String[] args) throws InterruptedException {
        new Thread(()->{
            while (count.get() <= 200){
                if(flag){
                    System.out.println(Thread.currentThread().getName() + "-->" + count.getAndIncrement());
                    flag = false;
                }
            }
        },"A").start();
        new Thread(()->{
            while (count.get()<=200){
                if(!flag){
                    System.out.println(Thread.currentThread().getName() + "-->" + count.getAndIncrement());
                    flag = true;
                }

            }
        },"B").start();

    }
}
