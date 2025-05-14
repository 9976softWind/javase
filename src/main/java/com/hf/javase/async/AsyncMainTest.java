package com.hf.javase.async;

import com.hf.javase.async.impl.CompletableFutureSqlBusinessOperator;
import com.hf.javase.async.impl.FutureSqlBusinessOperator;
import com.hf.javase.async.impl.ThreadSqlBusinessOperator;
import lombok.extern.slf4j.Slf4j;

/**
 * @author tdw
 * @date 2025.5.13
 */
@Slf4j
public class AsyncMainTest {

    private static final SqlBusinessOperator sqlBusinessOperator = new ThreadSqlBusinessOperator();

    private static final SqlBusinessOperator futureSqlBusinessOperator = new FutureSqlBusinessOperator();

    private static final SqlBusinessOperator completableFutureSqlBusinessOperator = new CompletableFutureSqlBusinessOperator();


    public static void main(String[] args) {
        log.info("主线程处理开始");
//        sqlBusinessOperator.insert();
//        futureSqlBusinessOperator.insert();
        completableFutureSqlBusinessOperator.insert();
        log.info("主线程处理完毕");
    }

}
