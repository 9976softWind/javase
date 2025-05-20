package com.hf.javase.net.io.nio;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @author tdw
 * @date 2025.5.20
 */
public class FileChannelTest {
    /**
     * 创建Channel方式
     *  ① 文件通道（FileChannel）
     *      使用传统IO类的getChannel()方法;
     *      JDK7+ 静态工厂方法 FileChannel.open();
     *  ② 网络通道
     *      SocketChannel.open()（TCP客户端）
     *      ServerSocketChannel.open()（TCP服务端）
     *      DatagramChannel.open()（UDP通道）
     *  ③ 内存映射通道（MappedByteBuffer）
     *      FileChannel.map()创建内存映射缓冲区，实现零拷贝文件操作：
     *          FileChannel channel = FileChannel.open(Paths.get("file.txt"), StandardOpenOption.READ);
     *          MappedByteBuffer mappedBuffer = channel.map(FileChannel.MapMode.READ_ONLY,0,,channel.size());
     *  ④ 工具类 Files.newByteChannel()
     */

    private final static File  file = new File("C:\\Users\\Admin\\Desktop\\file.txt");

    public static void main(String[] args) {
//        write("hello 1".getBytes(StandardCharsets.UTF_8));
//        read();
//        copyFile(new File("C:\\Users\\Admin\\Desktop\\file.txt"),new File("C:\\Users\\Admin\\Desktop\\copyfile.txt"));
        copyFileWithTransferFrom(new File("C:\\Users\\Admin\\Desktop\\file.txt"),new File("C:\\Users\\Admin\\Desktop\\copyfile.txt"));
    }
    // ByteBuffer和FileChannel通道，写数据
    public static void write(byte[] bytes){
        // 也可以用FileChannel.open()直接操作，FileChannel channel = FileChannel.open(Paths.get("example.txt"),StandardOpenOption.READ)
        try(FileOutputStream fot = new FileOutputStream(file);
            java.nio.channels.FileChannel channel = fot.getChannel()){
            ByteBuffer buffer = ByteBuffer.wrap(bytes);
            channel.write(buffer);
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    // ByteBuffer和FileChannel通道，读数据
    public static void read(){
        try(FileInputStream fis = new FileInputStream(file);
            java.nio.channels.FileChannel channel = fis.getChannel()){
            // 创建缓冲区
            ByteBuffer dst = ByteBuffer.allocate((int)file.length());
            // 将通道中数据读到缓冲区
            channel.read(dst);
            System.out.println(new String(dst.array(), StandardCharsets.UTF_8));
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    // 通过ByteBuffer和FileChannel进行文件拷贝
    public static void copyFile(File originFile,File targetFile){
        try(FileChannel readFileChannel = FileChannel.open(Paths.get(originFile.toURI()), StandardOpenOption.READ);
            FileChannel writeFileChannel = FileChannel.open(Paths.get(targetFile.toURI()), StandardOpenOption.WRITE,StandardOpenOption.CREATE,StandardOpenOption.APPEND)) {

            ByteBuffer byteBuffer = ByteBuffer.allocate((int) originFile.length());
            readFileChannel.read(byteBuffer);
            byteBuffer.flip();
            writeFileChannel.write(byteBuffer);

        }catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // 通过FileChannel的transferFrom进行文件拷贝
    public static void copyFileWithTransferFrom(File originFile,File targetFile){
        try(FileChannel readFileChannel = FileChannel.open(Paths.get(originFile.toURI()), StandardOpenOption.READ);
            FileChannel writeFileChannel = FileChannel.open(Paths.get(targetFile.toURI()), StandardOpenOption.WRITE,StandardOpenOption.CREATE,StandardOpenOption.APPEND)) {
            writeFileChannel.transferFrom(readFileChannel,0,readFileChannel.size());
        }catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
