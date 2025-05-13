package com.hf.javase.net.socket.tcp;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * @author tdw
 * @date 2025.5.13
 */
@Slf4j
public class Server {

    /**
     * 使用Socket进行网络编程时，本质上就是两个进程之间的网络通信。其中一个进程必须充当服务器端，
     * 它会主动监听某个指定的端口，另一个进程必须充当客户端，它必须主动连接服务器的IP地址和指定端口，如果连接成功，服务器端和客户端就成功地建立了一个TCP连接，双方后续就可以随时发送和接收数据
     *
     */
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(19851);
            log.info("server is running...");
           for (;;){
               Socket client = serverSocket.accept();
               HandleClient handleClient = new HandleClient(client);
               handleClient.start();
           }
        } catch (IOException e) {
            log.info("server init error,{}",e);
        }
    }

}

@Slf4j
class HandleClient extends Thread {

    Socket socket;

    public HandleClient(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
       try(InputStream input = this.socket.getInputStream();){
            handle(input);
       } catch (Exception e) {
           try {
               this.socket.close();
           } catch (IOException ex) {
               ex.printStackTrace();
           }
       }
    }

    private void handle(InputStream in ) throws IOException {
        byte[] bytes = new byte[1024];
        int bytesRead;
        while ((bytesRead = in.read(bytes)) != -1) {
            String received = new String(bytes, 0, bytesRead, StandardCharsets.UTF_8);
            log.info("server Received: {}",received);
        }
    }
}
