package com.hf.javase.jvm.classloader;

public class Demo02 {
    public static void main(String[] args) {
        // JVM试图使用的最大内存
        System.out.println("max:" + cal(Runtime.getRuntime().maxMemory()) + "MB");
        // JVM初始化总内存内存
        System.out.println("total:" +cal(Runtime.getRuntime().totalMemory()) + "MB");
    }
    public static String cal(long num){
        return num / (double) 1024/1024 + "";
    }
}
