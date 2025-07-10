package com.hf.javase.proxy.d.jdk;

/**
 * @author tdw
 * @date 2025.7.10
 * 代理接口
 */
public interface UserService {

    void addUser(String username);
    String getUserId(int userId);
}
