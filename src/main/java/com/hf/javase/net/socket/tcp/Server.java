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
