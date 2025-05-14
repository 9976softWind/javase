package com.hf.javase.async;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @author tdw
 * @date 2025.5.13
 */
@Slf4j
public class FutureSqlBusinessOperator implements SqlBusinessOperator{

    private static  final int threads = 5;

    private static  final ExecutorService fixedThreadPool = Executors.newFixedThreadPool(threads);

    @Override
    public void insert() {
        Future<Boolean> future  = fixedThreadPool.submit(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                log.info("异步线程处理开始");
                boolean b = insertSQL();
                log.info("异步线程处理完毕");
                return b;
            }
        });
        try {
            // 获取异步结果，此处是阻塞型get()
            Boolean aBoolean = future.get();
            log.info("异步线程处理结果:{}",aBoolean);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public static boolean insertSQL() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }

}
