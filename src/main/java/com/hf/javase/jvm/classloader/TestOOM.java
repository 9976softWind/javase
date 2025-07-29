package com.hf.javase.jvm.classloader;

import java.util.Random;

public class TestOOM {
    public static void main(String[] args) {
        String str = "hello";
        for (;;){
            str += str + new Random().nextInt(88888888) + new Random().nextInt(88888888);
        }
    }
}
