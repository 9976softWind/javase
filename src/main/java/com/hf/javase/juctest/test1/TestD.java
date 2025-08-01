package com.hf.javase.juctest.test1;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class TestD {
    // 共享计数器
    private static final AtomicInteger count = new AtomicInteger(0);
    // 共享锁
    private static final ReentrantLock lock = new ReentrantLock();
    // 条件变量：用于线程间等待/唤醒
    private static final Condition condition = lock.newCondition();
    // 交替标志
    private static volatile Boolean flag = true;

    public static void main(String[] args) {
        SharedData shareData = new SharedData(count, lock, condition, true); // 用共享对象封装所有变量

        new Thread(new A(shareData),"A").start();
        new Thread(new B(shareData),"B").start();
    }
}
class SharedData{
    private final AtomicInteger count;
    private final ReentrantLock lock;
    private final Condition condition;
    private volatile boolean flag;

    public SharedData(AtomicInteger count, ReentrantLock lock, Condition condition, boolean flag) {
        this.count = count;
        this.lock = lock;
        this.condition = condition;
        this.flag = flag;
    }

    // getter和setter（提供对共享变量的访问）
    public AtomicInteger getCount() { return count; }
    public ReentrantLock getLock() { return lock; }
    public Condition getCondition() { return condition; }
    public boolean isFlag() { return flag; }
    public void setFlag(boolean flag) { this.flag = flag; }
}
class A implements Runnable{
    private final SharedData shareData; // 持有共享资源对象（而非零散变量）

    public A(SharedData shareData) {
        this.shareData = shareData;
    }

    @Override
    public void run() {
        while (shareData.getCount().get() < 200){
            shareData.getLock().lock();
            try{
                shareData.getCondition().signal();
                if(shareData.isFlag()) {
                    System.out.println(Thread.currentThread().getName() + "-->" + shareData.getCount().getAndIncrement());
                    shareData.setFlag(false);
                }
                shareData.getCondition().await();
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                shareData.getLock().unlock();
            }
        }
    }
}
class B implements Runnable{
    private final SharedData shareData;
    public B(SharedData shareData) {
        this.shareData = shareData;
    }

    @Override
    public void run() {
        while (shareData.getCount().get() < 200){
            shareData.getLock().lock();
            try{
                shareData.getCondition().signal();
                if(!shareData.isFlag()){
                    System.out.println(Thread.currentThread().getName() + "-->" + shareData.getCount().getAndIncrement());
                    shareData.setFlag(true);
                }
                shareData.getCondition().await();
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                shareData.getLock().unlock();
            }
        }
    }
}