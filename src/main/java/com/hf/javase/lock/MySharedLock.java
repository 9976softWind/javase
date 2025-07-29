package com.hf.javase.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class MySharedLock implements Lock {
    // 同步器实现
    private final Sync sync;

    // 构造方法：指定最大共享数（例如最多3个线程同时持有锁）
    public MySharedLock(int maxSharedCount) {
        if (maxSharedCount <= 0) {
            throw new IllegalArgumentException("maxSharedCount must be positive");
        }
        this.sync = new Sync(maxSharedCount);
    }

    private static class Sync extends AbstractQueuedSynchronizer {
        private final int maxSharedCount; // 最大共享数

        public Sync(int maxSharedCount) {
            this.maxSharedCount = maxSharedCount;
            setState(0); // 初始状态为0（无线程持有）
        }

        // 共享式获取锁：返回值≥0表示成功，<0表示失败
        @Override
        protected int tryAcquireShared(int arg) {
            while (true) { // 循环处理CAS失败的情况
                int currentState = getState();
                int newState = currentState + arg;

                // 超过最大共享数，获取失败
                if (newState > maxSharedCount) {
                    return -1; // AQS规定：返回负数表示获取失败
                }

                // CAS更新状态成功，返回新状态（≥0表示成功）
                if (compareAndSetState(currentState, newState)) {
                    return newState;
                }
                // CAS失败则重试（处理并发竞争）
            }
        }

        // 共享式释放锁：返回true表示释放后可能需要唤醒其他线程
        @Override
        protected boolean tryReleaseShared(int arg) {
            while (true) { // 循环处理CAS失败的情况
                int currentState = getState();
                int newState = currentState - arg;

                // 状态为0时无需释放，避免负数
                if (currentState == 0) {
                    return false;
                }

                // CAS更新状态成功
                if (compareAndSetState(currentState, newState)) {
                    // 若释放后状态为0，说明所有线程都已释放，需要唤醒等待线程
                    return newState == 0;
                }
                // CAS失败则重试
            }
        }

        public int getHoldCount() {
            return getState();
        }
    }

    // 获取共享锁（会阻塞直到成功）
    @Override
    public void lock() {
        // 调用AQS的acquireShared，内部会循环调用tryAcquireShared
        sync.acquireShared(1);
    }

    // 可中断地获取共享锁
    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireSharedInterruptibly(1);
    }

    // 尝试获取共享锁（立即返回，不阻塞）
    @Override
    public boolean tryLock() {
        // tryAcquireShared返回≥0表示成功
        return sync.tryAcquireShared(1) >= 0;
    }

    // 超时尝试获取共享锁
    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireSharedNanos(1, unit.toNanos(time));
    }

    // 释放共享锁
    @Override
    public void unlock() {
        // 调用AQS的releaseShared，内部会调用tryReleaseShared并处理唤醒
        sync.releaseShared(1);
    }

    // 共享锁通常不依赖Condition，如需实现需重写AQS的isHeldExclusively等方法
    @Override
    public Condition newCondition() {
        throw new UnsupportedOperationException("Condition not supported for shared lock");
    }

    // 辅助方法：获取当前持有锁的线程数
    public int getHoldCount() {
        return sync.getHoldCount();
    }
}
    