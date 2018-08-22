package com.linyp.study.population;

import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.linyp.study.common.Base;
import com.linyp.study.common.Graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author: Yinpeng.Lin
 * @desc: 异步加载
 * @date: Created in 2018/8/21 10:48
 */
public class AsyncLoading extends Base {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // AsyncLoadingCache是使用AsyncCacheLoader来构建的缓存的值
        AsyncLoadingCache<String, Graph> cache = Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.MINUTES)
                // 以同步方式构建并包装成异步结果
                .buildAsync(key -> createExpensiveGraph(key));
                // 以异步方式构建并返回异步结果
                // .buildAsync((key, executor) -> createExpensiveGraphAsync(key, executor));

        String key = "小李子";
        // 查询并在缺失的情况下使用异步的方式来构建缓存
        CompletableFuture<Graph> graphCompletableFuture = cache.get(key);
        Graph graph = graphCompletableFuture.get();

        // 查询一组缓存并在缺失的情况下使用异步的方式来构建缓存
        List<String> keys = new ArrayList<>();
        keys.add(key);
        CompletableFuture<Map<String, Graph>> graphsCompletableFuture = cache.getAll(keys);
        Map<String, Graph> stringGraphMap = graphsCompletableFuture.get();

        // 异步转同步
        LoadingCache<String, Graph>  loadingCache = cache.synchronous();
    }

}
