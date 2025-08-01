package com.hf.javase.juctest.test2;

import java.util.concurrent.atomic.AtomicInteger;

public class BankA {
    public static void main(String[] args) {
        Account account1 = new Account(new AtomicInteger(1000), "account1");
        Account account2 = new Account(new AtomicInteger(500), "account2");
        Thread a = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "：" + account1.getName() + "has " + account1.getMoney().get());
            boolean dec = account1.dec(500);
            if (dec) {
                System.out.println(Thread.currentThread().getName() + "：" + "转账成功");
                System.out.println(Thread.currentThread().getName() + "：" + account1.getName() + "has " + account1.getMoney().get());
            }
        }, "A");
        a.start();
        Thread b = new Thread(() -> {
            try {
                a.join();
                System.out.println(Thread.currentThread().getName() + "：" + account2.getName() + "has " + account2.getMoney().get());
                boolean inc = account2.inc(500);
                if (inc) {
                    System.out.println(Thread.currentThread().getName() + "：" + "入账成功");
                    System.out.println(Thread.currentThread().getName() + "：" + account2.getName() + "has " + account2.getMoney().get());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }, "B");
        b.start();
        Thread c = new Thread(() -> {
            try {
                b.join();
                System.out.println(Thread.currentThread().getName() + "：" + "转账成功");
                System.out.println(Thread.currentThread().getName() + "：" + account1.getName() + "has " + account1.getMoney().get());
                System.out.println(Thread.currentThread().getName() + "：" + account2.getName() + "has " + account2.getMoney().get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "C");
        c.start();
    }
}
