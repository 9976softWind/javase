package com.hf.javase.juctest.test1;

import java.util.concurrent.atomic.AtomicInteger;

public class TestB {
    private static AtomicInteger count = new AtomicInteger(0);
    private static Object lock = new Object();
    private static volatile boolean flag = true;

    public static void main(String[] args) {
        new Thread(()->{
            while (count.get() <= 200){
                synchronized (lock){
                    lock.notify();
                    try {
                        if(flag){
                            System.out.println(Thread.currentThread().getName() + "-->" + count.getAndIncrement());
                            flag = false;
                            lock.wait();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            if(count.get() > 200){
                synchronized (lock){
                    lock.notifyAll();
                }
            }
        },"A").start();
        new Thread(()->{
            while (count.get() <= 200){
                synchronized (lock){
                    lock.notify();
                    try {
                        if(!flag){
                            System.out.println(Thread.currentThread().getName() + "-->" + count.getAndIncrement());
                            flag = true;
                            lock.wait();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            if(count.get() > 200){
                synchronized (lock){
                    lock.notifyAll();
                }
            }
        },"B").start();
    }
}
