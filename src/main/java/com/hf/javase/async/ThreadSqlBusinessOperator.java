package com.hf.javase.async;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author tdw
 * @date 2025.5.13
 */
@Slf4j
public class ThreadSqlBusinessOperator implements SqlBusinessOperator{

    private static  final int threads = 5;

    private static  final ExecutorService fixedThreadPool = Executors.newFixedThreadPool(threads);

    // 每次都创建新线程，频繁的创建，销毁，上下文切换回浪费系统资源，下面做一个小优化，采用线程池
    @Override
    public void insert() {
        AtomicBoolean isSuccess = new AtomicBoolean(false);
        fixedThreadPool.submit(new Thread(() -> {
            try {
                log.info("异步线程处理开始");
                isSuccess.set(insertSQL());
                boolean result = isSuccess.get();
                log.info("异步线程处理结果:{}",result);
                if(!result){
                    // 异步操作失败，执行后续业务逻辑
                    log.info("异步操作失败，执行后续业务逻辑");
                }
                log.info("异步线程处理完毕");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));
    }
    public static boolean insertSQL() throws InterruptedException {
        Thread.sleep(2000);
        return true;
    }
//    @Override
//    public void insert() {
//        // 创建新线程完成异步操作
//        new Thread(() -> {
//            try {
//                log.info("异步线程处理开始");
//                Thread.sleep(2000);
//                log.info("异步线程处理完毕");
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }).start();
//    }

}
