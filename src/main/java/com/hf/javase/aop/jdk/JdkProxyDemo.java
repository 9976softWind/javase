package com.hf.javase.aop.jdk;

import java.lang.reflect.Proxy;

/**
 * @author tdw
 * @date 2025.7.10
 */
public class JdkProxyDemo {
    public static void main(String[] args) {

        // 创建目标对象
        UserServiceImpl target = new UserServiceImpl();
        // 创建InvocationHandler
        LogInvocationHandler handler = new LogInvocationHandler(target);
        //动态生成代理对象
        UserService proxy = (UserService)Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), handler);
        proxy.addUser("张三");
        proxy.getUserId(1);
    }
}
