package com.hf.javase.net.io.nio.test;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

/**
 * @author tdw
 * @date 2025.5.20
 */
@Slf4j
public class NIOServer {

    private static ServerSocketChannel serverChannel ;

    private final static int port = 6667;

    private static Selector selector;

    public static void main(String[] args) throws IOException {
        new NIOServer().init();
    }

    public void init(){
        try {
            // 选择器
            selector = Selector.open();
            serverChannel  = ServerSocketChannel.open();
            // 绑定端口
            serverChannel.socket().bind(new InetSocketAddress(port));
            // 设置非阻塞模式
            serverChannel.configureBlocking(false);
            // 将该 serverChannel  注册到 selector
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);
            log.info("服务端启动，监听端口：{}",port);
            // Server循环处理
            listen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void listen(){
        try {
            for(;;) {
                while (selector.select() > 0) { // selector有事件处理
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        // 监听到连接建立事件
                        if (key.isAcceptable()) {
                            handleAccept(key);
                        }
                        // 通道可读
                        if (key.isReadable()) {
                            handleRead(key);
                        }
                        //当前的 key 处理完必须移除，防止重复处理
                        iterator.remove();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void handleAccept(SelectionKey key) throws IOException {
        ServerSocketChannel channel = (ServerSocketChannel) key.channel();
        SocketChannel client = channel.accept();
        client.configureBlocking(false);
        client.register(selector,SelectionKey.OP_READ);
        log.info("客户端[{}] 上线",client.getRemoteAddress());
    }

    public static void handleRead(SelectionKey key){
        SocketChannel client = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);// 此时buffer为写模式
        try{
            int bytesRead;
            while ((bytesRead = client.read(buffer)) > 0) { // 循环读取完整数据
                buffer.flip(); // 此时buffer切换为读模式
                byte[] data = new byte[buffer.remaining()];
                buffer.get(data);
                String receivedMsg = new String(data, StandardCharsets.UTF_8);
                log.info("收到客户端[{}] 数据: {}", client.getRemoteAddress(), receivedMsg);
                // 服务器回复
                String replyMsg = "server: " + receivedMsg.toUpperCase();
                ByteBuffer replyBuffer = ByteBuffer.wrap(replyMsg.getBytes());
                while (replyBuffer.hasRemaining()) {
                    client.write(replyBuffer); // 非阻塞模式下可能部分写入
                }
                buffer.clear();// 此时buffer情况，并切换为写模式
            }
            if (bytesRead == -1) { // 客户端主动关闭信号
                log.info("客户端[{}] 断开连接", client.getRemoteAddress());
                client.close();
            }
        } catch (IOException e) {
            log.error("客户端异常断开：{}",e.getMessage());
            key.cancel();
            try {
                client.close();
            } catch (IOException ex) {
            }
        }
    }

}
