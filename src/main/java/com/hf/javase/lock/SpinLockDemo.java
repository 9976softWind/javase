package com.hf.javase.lock;

import java.util.concurrent.atomic.AtomicReference;

//自旋锁，底层使用CAS
public class SpinLockDemo {

    AtomicReference<Thread> atomicReference = new AtomicReference();
    public void myLock(){
        System.out.println(Thread.currentThread().getName() +"=>" + "MyLock");
        while (!atomicReference.compareAndSet(null,Thread.currentThread())){

        }
    }

    public void myUnLock(){
        System.out.println(Thread.currentThread().getName() +"=>" + "MyUnLock");
        atomicReference.compareAndSet(Thread.currentThread(),null);
    }
}
