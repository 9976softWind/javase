package com.hf.javase.jvm.classloader;

import java.util.ArrayList;

public class Demo03 {

    byte[] array = new byte[1 * 1024 * 1024];


    public static void main(String[] args) {
        ArrayList<Demo03> demo03s = new ArrayList<>();
        int count = 0;
       try{
           for(;;){
               demo03s.add(new Demo03());
               count++;
           }
       }catch (Exception e){
           System.out.println("count:" + count);
       }

    }
}
