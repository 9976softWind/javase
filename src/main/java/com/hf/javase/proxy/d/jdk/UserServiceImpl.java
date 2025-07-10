package com.hf.javase.proxy.d.jdk;

/**
 * @author tdw
 * @date 2025.7.10
 * 实现目标类
 */
public class UserServiceImpl implements UserService{
    @Override
    public void addUser(String username) {
        System.out.println("添加用户：" + username);
    }

    @Override
    public String getUserId(int userId) {
        return "用户Id：" + userId;
    }
}
