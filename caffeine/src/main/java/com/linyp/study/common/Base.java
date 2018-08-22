package com.linyp.study.common;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

/**
 * @author: Yinpeng.Lin
 * @desc:
 * @date: Created in 2018/8/21 10:43
 */
public class Base {

    /**
     * 根据键key从数据源获取值(同步)
     *
     * @param key
     * @return
     */
    protected static Graph createExpensiveGraph(String key) {
        // query by key
        return createExpensiveGraph(key, 0);
    }

    /**
     * 创建Graph
     *
     * @param key
     * @return
     */
    protected static Graph createExpensiveGraph(String key, Integer weight) {
        // query by key
        return new Graph(key, key + "_123", weight);
    }

    /**
     * 根据键key从数据源获取值(异步)
     *
     * @param key
     * @param executor 线程池(默认为ForkJoinPool.commonPool()方法返回的ForkJoinPool)
     * @return
     */
    protected static CompletableFuture<Graph> createExpensiveGraphAsync(String key, Executor executor) {
        // query by key
        System.out.println(executor.getClass());
        CompletableFuture<Graph> completableFuture = CompletableFuture.supplyAsync(() -> createExpensiveGraph(key, 0), executor);
        return completableFuture;
    }

}


