package com.hf.javase.net.socket.udp;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;

/**
 * @author tdw
 * @date 2025.5.13
 */
public class Client {

    public static void main(String[] args) {
        try(DatagramSocket ds = new DatagramSocket()) {
            // 连接指定服务器和端口
            ds.connect(InetAddress.getByName("127.0.0.1"),6666);
            for(int i=0; i<2;i++){
                DatagramPacket packet = new DatagramPacket(new byte[1024], 0, 1024);
                packet.setData(("hello" + i).getBytes(StandardCharsets.UTF_8));
                //发
                ds.send(packet);
                //收
                ds.receive(packet);
                System.out.println(new String(packet.getData(),packet.getOffset(),packet.getLength()));
            }
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
