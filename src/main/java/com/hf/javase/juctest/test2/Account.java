package com.hf.javase.juctest.test2;

import lombok.Getter;

import java.util.concurrent.atomic.AtomicInteger;

@Getter
public class Account {


    private AtomicInteger money;
    private String name;

    public Account(AtomicInteger money,String name) {
        this.money = money;
        this.name = name;
    }

    //支出
    public boolean dec(int money){
        AtomicInteger expect = getMoney();
        int totalMoney = expect.get();
        return expect.compareAndSet(totalMoney,totalMoney - money);
    }
    //收入
    public boolean inc(int money){
        AtomicInteger expect = getMoney();
        int totalMoney = expect.get();
        return expect.compareAndSet(totalMoney,totalMoney + money);
    }

}
