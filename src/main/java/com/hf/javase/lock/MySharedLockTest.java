package com.hf.javase.lock;

import java.util.concurrent.TimeUnit;

public class MySharedLockTest {
    private static int count = 0;
    private static final MySharedLock lock = new MySharedLock(3); // 最多3个线程同时持有

    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[5];
        for (int i = 0; i < 5; i++) {
            threads[i] = new Thread(() -> {
                    lock.lock();
                    try {
                        System.out.println("count:" + count);
                        System.out.println(lock.getHoldCount());
                        TimeUnit.SECONDS.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        lock.unlock();
                    }
            });
            threads[i].start();
        }

        for (Thread t : threads) {
            t.join();
        }
        System.out.println("Final count: " + count); // 预期5000
    }
}
