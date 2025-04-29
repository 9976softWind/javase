package com.hf.javase;

import com.hf.javase.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.nio.charset.StandardCharsets;

@SpringBootTest
class IOTests {

    /**
     * 字节流
     * 底层传输的始终为二进制数据
     */
    @Test// 字节输入流,任何类型的文件都可以采用这个流来读
    void testFileInputStream() {
        File file = new File("C:\\Users\\Admin\\Desktop\\测试脚本.txt");
        // 此处为try-resource-catch结构，是JDK7引入的异常处理机制，用于自动管理资源（例如文件流，数据库连接等），确保资源在使用后能被正确关闭，即使发生异常也如此
        try(FileInputStream fis = new FileInputStream(file)){
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) != -1) {
                System.out.println(new String(buffer, 0, length));
            }
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Test// 字节输出流，写的时候不要忘记刷新 刷新的作用是防止通道里面有遗漏的数据
    void testFileOutputStream(){
        FileOutputStream fos = null;
        File file = new File("C:\\Users\\Admin\\Desktop\\测试脚本.txt");
        try {
            //  这种方式以追加的方式在文件末尾写入。不会清空原文件内容，在后面添加一个true
            fos = new FileOutputStream(file,true);
            String appendContent = "文件写入";
            byte[] bytes = appendContent.getBytes(StandardCharsets.UTF_8);
            fos.write(bytes);
            //写的时候不要忘记刷新 刷新的作用是防止通道里面有遗漏的数据
            fos.flush();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 字符流
     * 当使用字节流读取文本文件时，可能会有一个小问题。就是遇到中文字符时，可能不会显示完整的字符，那是因为一个中文字符可能占用多个字节存储。
     * 所以Java提供一些字符流类，以字符为单位读写数据，专门用于处理文本文件
     */
    @Test// 字符输入流，只能读取普通文本
    void testFileReader() {
        FileReader fir = null;
        File file = new File("C:\\Users\\Admin\\Desktop\\测试脚本.txt");
        try  {
            fir = new FileReader(file);
            char[] buffer = new char[1024];
            int length;
            while ((length = fir.read(buffer)) != -1) {
                System.out.println(new String(buffer, 0, length));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(fir != null){
                try {
                    fir.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    @Test //字符输出流。只能输出普通文本
    void testFileWrite(){
        FileWriter fiw = null;
        File file = new File("C:\\Users\\Admin\\Desktop\\测试脚本.txt");
        try {
            fiw = new FileWriter(file,true);
            char[] c = new char[]{'一','二','三','四','五'};
            fiw.write(c);

            fiw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fiw == null){
                try {
                    fiw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 缓冲流
     * 缓冲流的基本原理，是在创建流对象时，会创建一个内置的默认大小的缓冲区数组，通过缓冲区读写，减少系统IO次数，从而提高读写的效率。
     */
    @Test //带有缓冲区的字节输入流
    void testBufferInputStream(){
        FileInputStream fis = null;
        File file = new File("C:\\Users\\Admin\\Desktop\\测试脚本.txt");
        try  {
            fis = new FileInputStream(file);
            BufferedInputStream bi = new BufferedInputStream(fis);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = bi.read(buffer)) != -1) {
                System.out.println(new String(buffer, 0, length, StandardCharsets.UTF_8));
            }
            bi.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Test //带有缓冲区的字节输出流
    void testBufferOutputStream(){
        File file = new File("C:\\Users\\Admin\\Desktop\\测试脚本.txt");
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file,true);
            BufferedOutputStream bo = new BufferedOutputStream(fileOutputStream);

            bo.write(new String("1123测试").getBytes(StandardCharsets.UTF_8));

            bo.flush();

            bo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test //带有缓冲区的字符输入流
    void testBufferReader() {
        /**
         *  特性                  BufferedReader                         普通Reader(如FileReader)
         *  缓冲机制            内置8KB字符缓冲区（默认）                      无缓冲，直接操作底层I/O
         *  读取单位            按缓冲区大小批量读取                           单字符或指定长度读取
         *  物理IO次数          大幅减少（缓冲命中时无IO操作）                  每次read()都触发物理IO
         */
        File file = new File("C:\\Users\\Admin\\Desktop\\测试脚本.txt");
        FileReader fileReader = null;  // 当一个流被当做参数传递到另一个流时，这个流称作：节点流；
        try {
            fileReader = new FileReader(file);
            BufferedReader br = new BufferedReader(fileReader);//该流称之为：包装流/处理流
            String s = null;
            while ((s = br.readLine()) != null){
                System.out.println(s);
            }
            //关闭流 只需要关闭最外面的流 里面的流不需要关闭 因为外面的流close会调用里面流的clos
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test //带有缓冲区的字符输出流
    void testBufferWrite() {

        File file = new File("C:\\Users\\Admin\\Desktop\\测试脚本.txt");
        FileWriter fileWriter = null;  // 当一个流被当做参数传递到另一个流时，这个流称作：节点流；
        try {
            fileWriter = new FileWriter(file,true);
            BufferedWriter bw = new BufferedWriter(fileWriter);//该流称之为：包装流/处理流

            bw.write("cs1是1");

            bw.flush();

            bw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *  流的转换
     */
    @Test
    void changeStream(){
        /**
         *
         * OutputStreamWriter类
         *                  方法名	                                                            说明
         *                  OutputStreamWriter(OutputStream in)	                        创建一个使用默认字符集的字符输出流。
         *                  OutputStreamWriter(OutputStream in, String charsetName)	    创建一个指定字符集的字符输出流。
         *
         */

        /**
         * InputStreamReader类
         *                  方法名	                                                        说明
         *                  InputStreamReader(InputStream in)	                    创建一个使用默认字符集的字符流。
         *                  InputStreamReader(InputStream in, String charsetName)	创建一个指定字符集的字符流。
         */
    }

    /**
     * 对象序列化流
     * 功能：将Java对象的原始数据类型写出到文件,实现对象的持久存储
     * 核心目的是实现跨网络、跨进程、跨时间的数据持久化和传输
     * 典型应用场景
     * 1. 分布式系统通信
     *          RPC框架（如Dubbo、gRPC）：序列化是远程方法调用的基础，将Java对象转为字节流传输
     *          消息队列（Kafka/RabbitMQ）：消息生产者和消费者之间传递序列化的对象
     *          示例：订单系统将Order对象序列化后通过MQ发送给物流系统
     * 2. 缓存持久化
     *          Redis/Memcached缓存：将Java对象序列化后存储到缓存服务
     *          本地缓存快照：如Ehcache将缓存数据序列化到磁盘防止服务重启丢失
     *          示例：用户会话信息UserSession序列化后存入Redis
     * 3. 配置文件存储
     *          存储复杂配置对象（如规则引擎配置、工作流定义）
     *          替代方案：相比JSON/YAML，序列化更省空间但可读性差
     * 4. 游戏开发
     *          保存玩家存档（角色状态、装备数据等）
     *          示例：PlayerData对象序列化到本地文件
     * 5. 大数据处理
     *          Hadoop/Spark中跨节点传输数据对象
     *          Flink检查点（Checkpoint）的状态持久化
     */

    @Test  //对象序列化输出
    void testObjectOutputStream(){
        User user = new User(1, "张三", "15298564112");
        File file = new File("C:\\Users\\Admin\\Desktop\\User.ser");
        ObjectOutputStream objectOutputStream = null;
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(user);
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test  //对象序列化输入
    void testObjectInputStream() {
        File file = new File("C:\\Users\\Admin\\Desktop\\User.ser");
        // 1. 使用try-with-resources自动关闭资源
        try (FileInputStream fileInputStream = new FileInputStream(file);
             ObjectInputStream inputStream = new ObjectInputStream(fileInputStream)) {
            // 2. 添加文件存在性检查
            if (!file.exists() || file.length() == 0) {
                throw new IllegalStateException("文件不存在或为空");
            }
            // 3. 类型安全转换
            Object obj = inputStream.readObject();
            if (obj instanceof User) {
                User user = (User) obj;
                System.out.println(user.toString());
            } else {
                throw new ClassCastException("文件内容不是User类型");
            }
        } catch (InvalidClassException e) {
            System.err.println("序列化版本不匹配: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("未找到User类: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("IO错误: " + e.getMessage());
        }
    }

}
