package com.hf.javase.bio;

import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author tdw
 * @date 2025.5.13
 */
//@Component
@Slf4j
public class BIOServer {

    /**
     * 如果BIO使用单线程接受连接，则会阻塞其他连接，效率较低。
     * 如果使用多线程虽然减弱了单线程带来的影响，但当有大并发进来时，会导致服务器线程太多，压力太大而崩溃。
     * 就算使用线程池，也只能同时允许有限个数的线程进行连接，如果并发量远大于线程池设置的数量，还是与单线程无异
     * 操作系统里read函数操作是阻塞操作，如果连接不做数据读写操作会导致线程阻塞，就是说只占用连接，不发送数据，则会浪费资源。比如线程池中500个连接，只有100个是频繁读写的连接，其他占着茅坑不拉屎，浪费资源！
     * 另外多线程也会有线程切换带来的消耗
     * 综上所述，BIO方式已经不适用于如下的大并发场景，仅适用于连接数目比较小且固定的架构。这种方式对服务器资源要求比较高，但BIO程序简单易理解。
     */

    private static int port = 9090;

    private static final int MAX_CLIENTS = 100; // 最大并发连接数

    private final ExecutorService threadPool = Executors.newFixedThreadPool(MAX_CLIENTS);

    private ServerSocket serverSocket;

    @PostConstruct
    public void init(){
        try {
            serverSocket = new ServerSocket(port);
            log.info("[BIOServer] Server started on port {}", port);

            while (!Thread.currentThread().isInterrupted()) {
                Socket client = serverSocket.accept();
                int clientId = new Random().nextInt(1000);
                log.info("[BIOServer] Client connected! clientId: {}", clientId);
                threadPool.execute(() -> {
                    try {
                        handleClientData(client, clientId);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        } catch (IOException e) {
            log.error("[BIOServer] Server error", e);
        } finally {
            closeServer();
        }
    }

    private void handleClientData(Socket client,int clientId) throws IOException {
        try (InputStream in = client.getInputStream();
             OutputStream out = client.getOutputStream()) {

            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = in.read(buffer)) != -1) {
                String received = new String(buffer, 0, bytesRead, StandardCharsets.UTF_8);
                log.info("[BIOServer-{}] Received: {}", clientId, received);

                out.write(("Server response: " + received).getBytes(StandardCharsets.UTF_8));
                out.flush();
            }

            log.info("[BIOServer] Client disconnected! clientId: {}", clientId);
        } catch (IOException e) {
            log.error("[BIOServer-{}] Client handling error", clientId, e);
        } finally {
            closeQuietly(client);
        }
    }
    private void closeServer() {
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
            threadPool.shutdown();
            log.info("[BIOServer] Server stopped");
        } catch (IOException e) {
            log.error("[BIOServer] Error closing server", e);
        }
    }

    private void closeQuietly(Socket resource) {
        try {
            if (resource != null) {
                resource.close();
            }
        } catch (IOException e) {
            log.error("Error closing resource", e);
        }
    }

    @PreDestroy
    public void destroy() {
        closeServer();
    }

}
