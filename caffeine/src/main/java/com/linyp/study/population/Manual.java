package com.linyp.study.population;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.linyp.study.common.Base;
import com.linyp.study.common.Graph;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

/**
 * @author: Yinpeng.Lin
 * @desc: 手动加载
 * @date: Created in 2018/8/21 10:22
 */
public class Manual extends Base {

    public static void main(String[] args) {
        Cache<String, Graph> cache = Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.MINUTES)
                // 10_000等价于10000
                .maximumSize(10_000)
                .build();

        String key = "小李子";
        // 根据key查询一个缓存，如果没有返回NULL
        Graph graph = cache.getIfPresent(key);

        // 根据Key查询一个缓存，如果没有则调用createExpensiveGraph方法，并将返回值保存到缓存。
        // 如果该方法返回Null则manualCache.get返回null，如果该方法抛出异常则manualCache.get抛出异常
        graph = cache.get(key, k -> createExpensiveGraph(k));

        // 将一个值放入缓存，如果以前有值就覆盖以前的值
        cache.put(key, graph);

        // 删除一个缓存
        cache.invalidate(key);

        // 转化为ConcurrentMap
        ConcurrentMap<String, Graph> map = cache.asMap();
    }

}
