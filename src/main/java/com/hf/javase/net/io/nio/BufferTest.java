package com.hf.javase.net.io.nio;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * @author tdw
 * @date 2025.5.20
 */
public class BufferTest {

    /**
     * 创建Buffer方式
     * 堆内存分配 allocate()
     * 直接内存分配 allocateDirect()
     * 数组包装 wrap
     */

    public static void main(String[] args) {
        ByteBuffer allocate = ByteBuffer.allocate(1024);
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
        ByteBuffer wrap = ByteBuffer.wrap("hello".getBytes(StandardCharsets.UTF_8));
    }



}
