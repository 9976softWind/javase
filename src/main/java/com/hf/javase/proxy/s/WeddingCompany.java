package com.hf.javase.proxy.s;

/**
 * @author tdw
 * @date 2025.7.10
 */
public class WeddingCompany implements Marry{

    private Marry target;

    public WeddingCompany(Marry target) {
        this.target = target;
    }

    @Override
    public void Marry() {
        System.out.println("大家伙看过来!宣布个事");
        target.Marry();
        System.out.println("大家鼓掌！");
    }
}
