package com.hf.javase.net.socket.udp;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

/**
 * @author tdw
 * @date 2025.5.13
 */
@Slf4j
public class Server {

    public static void main(String[] args) {
        try {
            DatagramSocket ds = new DatagramSocket(6666);
            for(;;){
                // 数据缓冲区:
                byte[] buffer = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                ds.receive(packet);
                String s = new String(packet.getData(), packet.getOffset(), packet.getLength(), StandardCharsets.UTF_8);
                log.info("udp packet has received：{}",s);
                packet.setData("ACK".getBytes(StandardCharsets.UTF_8));
                ds.send(packet);
            }
        } catch (SocketException e) {
            log.error("udp server error：{}",e);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
