package com.hf.javase.proxy.s;

/**
 * @author tdw
 * @date 2025.7.10
 */
public class StaticProxyDemo {

    public static void main(String[] args) {
        You you = new You();
        She she = new She();
        new WeddingCompany(you).Marry();
        new WeddingCompany(she).Marry();
    }
}
