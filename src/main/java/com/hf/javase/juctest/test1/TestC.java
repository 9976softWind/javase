package com.hf.javase.juctest.test1;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class TestC {
    // 共享计数器
    private static final AtomicInteger count = new AtomicInteger(0);
    private static final ReentrantLock lock = new ReentrantLock();
    private static volatile boolean flag = true;

    public static void main(String[] args) {
        new Thread(()->{
            while (count.get() < 200){
                lock.lock();
                try{
                    if(flag){
                        System.out.println(Thread.currentThread().getName() + "-->" + count.getAndIncrement());
                        flag = false;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    lock.unlock();
                }
            }
        },"A").start();
        new Thread(()->{
            while (count.get() < 200){
                lock.lock();
                try{
                    if(!flag){
                        System.out.println(Thread.currentThread().getName() + "-->" + count.getAndIncrement());
                        flag = true;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    lock.unlock();
                }
            }
        },"B").start();
    }
}
