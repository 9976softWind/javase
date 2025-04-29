package com.hf.javase.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    //  Serializable这个标志接口是给java虚拟机参考的，java虚拟机看到这个接口之后，会为该类自动生成一个序列化版本号(不推荐)，建议显示声明版本号

    // 显式声明版本号
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

    private String phone;

}
