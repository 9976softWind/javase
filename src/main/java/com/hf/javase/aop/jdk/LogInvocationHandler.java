package com.hf.javase.aop.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author tdw
 * @date 2025.7.10
 * 实现InvocationHandler
 */
public class LogInvocationHandler implements InvocationHandler {

    // 代理目标对象
    private final Object target;

    public LogInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        // 方法调用前增强
        System.out.println("[日志] 调用方法: " + method.getName() + ", 参数: " + Arrays.toString(args));

        // 反射调用目标方法
        Object result = method.invoke(target, args);

        // 方法调用后增强
        System.out.println("[日志] 返回结果: " + result);

        return result;
    }
}
