package com.hf.javase.juctest.test2;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class BankC {
    public static void main(String[] args) {
        Account account1 = new Account(new AtomicInteger(1000), "account1");
        Account account2 = new Account(new AtomicInteger(500), "account2");
        Semaphore semaphore1 = new Semaphore(1);
        Semaphore semaphore2 = new Semaphore(0);
        Semaphore semaphore3 = new Semaphore(0);
        new Thread(() -> {
            try {
                semaphore1.acquire(1);
                System.out.println(Thread.currentThread().getName() + "：" + account1.getName() + "has " + account1.getMoney().get());
                boolean dec = account1.dec(500);
                if (dec) {
                    System.out.println(Thread.currentThread().getName() + "：" + "转账成功");
                    System.out.println(Thread.currentThread().getName() + "：" + account1.getName() + "has " + account1.getMoney().get());
                }
                semaphore2.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "A").start();
        new Thread(() -> {
            try {
                semaphore2.acquire(1);
                System.out.println(Thread.currentThread().getName() + "：" + account2.getName() + "has " + account2.getMoney().get());
                boolean inc = account2.inc(500);
                if (inc) {
                    System.out.println(Thread.currentThread().getName() + "：" + "入账成功");
                    System.out.println(Thread.currentThread().getName() + "：" + account2.getName() + "has " + account2.getMoney().get());
                    semaphore3.release(1);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "B").start();
         new Thread(() -> {
             try {
                 semaphore3.acquire(1);
                 System.out.println(Thread.currentThread().getName() + "：" + "转账成功");
                 System.out.println(Thread.currentThread().getName() + "：" + account1.getName() + "has " + account1.getMoney().get());
                 System.out.println(Thread.currentThread().getName() + "：" + account2.getName() + "has " + account2.getMoney().get());
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }
        }, "C").start();
    }
}
