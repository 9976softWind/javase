package com.hf.javase.lock;

public class MyExclusiveLockTest {

    private static int count = 0;
    private static final MyExclusiveLock myLock = new MyExclusiveLock();

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    myLock.lock();
                    try {
                        count++;
                    } finally {
                        myLock.unlock();
                    }
                }
            }, String.valueOf(i));
            thread.start();
            thread.join();
            System.out.println(" count: " + count);
        }

        System.out.println("Final count: " + count);

    }
}
