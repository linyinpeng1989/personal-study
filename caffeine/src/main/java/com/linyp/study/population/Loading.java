package com.linyp.study.population;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.linyp.study.common.Base;
import com.linyp.study.common.Graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author: Yinpeng.Lin
 * @desc: 同步加载
 * @date: Created in 2018/8/21 10:39
 */
public class Loading extends Base {

    public static void main(String[] args) {
        // LoadingCache是使用CacheLoader来构建的缓存的值
        LoadingCache<String, Graph> cache = Caffeine.newBuilder()
                .maximumSize(10_000)
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .build(key -> createExpensiveGraph(key));

        String key = "小李子";
        // 采用同步方式去获取一个缓存和上面的手动方式是一个原理。在build Cache的时候会提供一个createExpensiveGraph函数。
        // 查询并在缺失的情况下使用同步的方式来构建一个缓存
        Graph graph = cache.get(key);

        // 获取组key的值返回一个Map
        List<String> keys = new ArrayList<>();
        keys.add(key);
        Map<String, Graph> graphs = cache.getAll(keys);
    }
}
