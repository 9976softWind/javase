package com.hf.javase;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.nio.charset.StandardCharsets;

@SpringBootTest
class JavaseApplicationTests {

    @Test// 字节输入流
    void testFileInputStream() {
        FileInputStream fis = null;
        File file = new File("C:\\Users\\Admin\\Desktop\\测试脚本.txt");
        try  {
            fis = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) != -1) {
                System.out.println(new String(buffer, 0, length));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(fis != null){
                try {
                    fis.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }
    @Test// 字符输入流
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

    @Test
    void testFileOutputStream(){
        FileOutputStream fos = null;
        File file = new File("C:\\Users\\Admin\\Desktop\\测试脚本.txt");
        try {
            //  这种方式以追加的方式在文件末尾写入。不会清空原文件内容，在后面添加一个true
            fos = new FileOutputStream(file,true);
            String appendContent = "文件写入";
            byte[] bytes = appendContent.getBytes(StandardCharsets.UTF_8);
            fos.write(bytes);

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

}
