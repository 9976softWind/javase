package com.hf.javase.net.io.nio.test;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @author tdw
 * @date 2025.5.20
 */
@Slf4j
public class NIOClient {

    private SocketChannel socketChannel;

    private Selector selector;

    private String userName;


    public NIOClient(String userName) throws IOException {
        this.userName = userName;
        selector = Selector.open();
        socketChannel = SocketChannel.open(new InetSocketAddress("localhost",6667));
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);
        // 启动接收消息线程
        new Thread(this::receiveMessage).start();

        // 启动发送消息线程
        new Thread(this::sendMessage).start();
    }
    public static void main(String[] args) throws IOException {
        new NIOClient("User1");
    }
    private void receiveMessage() {
        try {
            while (true) {
                if (selector.select(1000) == 0) continue;
                Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
                while (keys.hasNext()) {
                    SelectionKey key = keys.next();
                    keys.remove();
                    if (key.isReadable()) {
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        SocketChannel channel = (SocketChannel) key.channel();
                        channel.read(buffer);
                        buffer.flip();
                        String msg = new String(buffer.array(), 0, buffer.limit());
                        System.out.println(msg);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage() {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                String msg = scanner.nextLine();
                if ("exit".equals(msg)) break;
                msg = this.userName + ": " + msg;
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes(StandardCharsets.UTF_8));
                socketChannel.write(buffer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
