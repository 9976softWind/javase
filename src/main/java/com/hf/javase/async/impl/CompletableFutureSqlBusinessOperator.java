package com.hf.javase.async.impl;

import com.hf.javase.async.SqlBusinessOperator;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @author tdw
 * @date 2025.5.14
 */
@Slf4j
public class CompletableFutureSqlBusinessOperator implements SqlBusinessOperator {

    private static  final int threads = 5;

    private static  final ExecutorService fixedThreadPool = Executors.newFixedThreadPool(threads);

    /**
     * CompletableFuture 是 Java 8 引入的一个强大的异步编程工具，它实现了 Future 和 CompletionStage 接口，提供了丰富的异步编程能力。
     * CompletableFuture 代表一个异步计算的结果，它提供了：
     *                                              异步执行任务
     *                                              任务结果处理
     *                                              任务组合与链式调用
     *                                              异常处理
     * 异步任务编排：
     *      使用 supplyAsync() 启动异步任务
     *      使用 thenApplyAsync() 进行串行异步操作
     *      使用 thenCombineAsync() 合并两个独立异步任务的结果
     *      使用 thenRunAsync() 执行无返回值的后续操作
     * 异常处理：
     *      使用 exceptionally() 处理整个链中的异常
     *      可以在任意步骤中抛出业务异常
     * 线程控制：
     *      使用自定义线程池而非默认的 ForkJoinPool
     *      所有异步操作都指定了相同的线程池
     * 业务逻辑分离：
     *      每个业务方法保持简单和独立
     *      异步编排逻辑清晰可见
     */
    @Override
    public void insert() {
        CompletableFuture<Boolean> future = CompletableFuture.supplyAsync(() -> {
            log.info("异步线程处理开始");
            boolean b = insertSQL();
            log.info("异步线程处理完毕");
            return b;
        },fixedThreadPool);
        try {
            Boolean aBoolean = future.get(3, TimeUnit.SECONDS);
            log.info("异步结果:{}",aBoolean);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
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
