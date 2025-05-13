package com.hf.javase.net.socket.tcp;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * @author tdw
 * @date 2025.5.13
 */
@Slf4j
public class Client {

    public static void main(String[] args) {
        try {
            Socket client = new Socket("127.0.0.1",19851);
            log.info("client has connected to server");
            InputStream inputStream = client.getInputStream();
            OutputStream outputStream = client.getOutputStream();
            outputStream.write("你好".getBytes(StandardCharsets.UTF_8));
            /**
             * 如果不调用flush()，我们很可能会发现，客户端和服务器都收不到数据，
             * 这并不是Java标准库的设计问题，以流的形式写入数据的时候，并不是一写入就立刻发送到网络，而是先写入内存缓冲区，直到缓冲区满了以后，才会一次性真正发送到网络
             * 这样设计的目的是为了提高传输效率。如果缓冲区的数据很少，而我们又想强制把这些数据发送到网络，就必须调用flush()强制把缓冲区数据发送出去。
             */
            outputStream.flush();
            client.close();
            log.info("client has disconnected");
        } catch (IOException e) {
            log.info("server init error,{}",e);
        }
    }

}
