package com.hf.javase.cas;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

public class CASDemo {

    public static void main(String[] args) {
        AtomicStampedReference<Integer> atomicInteger = new AtomicStampedReference(1000,1);
        new Thread(()->{
            int stamp = atomicInteger.getStamp();
            System.out.println("A1->" + stamp);
            System.out.println(atomicInteger.compareAndSet(1000, 2000, atomicInteger.getStamp(), atomicInteger.getStamp() + 1));
            stamp = atomicInteger.getStamp();
            System.out.println("A2->" + stamp);
            System.out.println(atomicInteger.compareAndSet(2000, 1000, atomicInteger.getStamp(), atomicInteger.getStamp() + 1));
            stamp = atomicInteger.getStamp();
            System.out.println("A3->" + stamp);
        },"A").start();
    }
}
