/*
 * Copyright 2013-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hf.javase.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@RestController
@RequestMapping("/api/v1/file")
public class FileController {


    // http://127.0.0.1:8080/api/v1/file/{fileId}
    @GetMapping("/{fileId}")
    public void downLoadFile(@PathVariable String fileId, HttpServletResponse response) throws IOException {


        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition, Content-Type");
        response.setContentType("text/plain");
        // 指示内容的显示方式    attachment --> 下载文件
        response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + "test");
        response.setHeader("Content-Length", "1025");
        response.getWriter().write("test11");


//        FileReader fir = null;
//        File file = new File("C:\\Users\\Admin\\Desktop\\测试脚本.txt");
//        try  {
//            fir = new FileReader(file);
//            char[] buffer = new char[1024];
//            int length;
//            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition, Content-Type");
//            response.setContentType("text/plain");
//            // 指示内容的显示方式    attachment --> 下载文件
//            response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + file.getName());
//            response.setHeader("Content-Length", String.valueOf(file.getTotalSpace()));
//            while ((length = fir.read(buffer)) != -1) {
//                response.getWriter().write(buffer);
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }catch (IOException e){
//            e.printStackTrace();
//        }finally {
//            if(fir != null){
//                try {
//                    fir.close();
//                }catch (IOException e){
//                    e.printStackTrace();
//                }
//            }
//        }
    }

}
