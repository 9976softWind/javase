package com.hf.javase.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

//基于AQS实现的自定义独占锁
public class MyExclusiveLock implements Lock {
    // 同步类，实现AQS的具体逻辑
    private final Sync sync = new Sync();
    // 同步器实现
    private static class Sync extends AbstractQueuedSynchronizer{

        @Override
        /**
         * 尝试获取独占锁
         */
        protected boolean tryAcquire(int arg) {
            Thread current = Thread.currentThread();
            int state = getState();
            // 状态为0，表示锁未被持有
            if(state == 0){
                //CAS操作，尝试更新锁状态为arg
                if(compareAndSetState(0,arg)){
                    // 设置当前持有锁的线程
                    setExclusiveOwnerThread(current);
                    return true;
                }
            }else if(current == getExclusiveOwnerThread()){
                //当前线程已持有锁，支持可重入
                int nextc = state + arg;
                if(nextc < 0){
                    throw new Error("Maximum lock count exceeded");
                }
                setState(nextc);
                return true;
            }
            // 锁已被其他线程持有，获取失败
            return false;
        }
        @Override
        /**
         * 尝试释放独占锁
         */
        protected boolean tryRelease(int arg) {
            if(Thread.currentThread() != getExclusiveOwnerThread()){
                throw new IllegalMonitorStateException();
            }
            int c = getState() - arg;
            boolean free = false;
            if(c == 0){
                free = true;
                setExclusiveOwnerThread(null);
            }
            setState(c);
            return free;
        }
        @Override
        /**
         * 判断当前线程是否持有锁
         */
        protected boolean isHeldExclusively() {
            return getState() != 0 && Thread.currentThread() == getExclusiveOwnerThread();
        }
        protected int getHoleCount(){
            return getState();
        }
    }
    @Override
    public void lock() {
        sync.acquire(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1,unit.toNanos(time));
    }

    @Override
    public void unlock() {
        sync.release(1);
    }

    @Override
    public Condition newCondition() {
        throw new UnsupportedOperationException("Condition not implemented");
    }
    public int getHoldCount(){
        return sync.getHoleCount();
    }
    public boolean isHeldByCurrentThread(){
        return sync.isHeldExclusively();
    }

}
