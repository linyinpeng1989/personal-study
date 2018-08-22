package com.linyp.study.eviction;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.linyp.study.common.Base;
import com.linyp.study.common.Graph;

/**
 * @author: Yinpeng.Lin
 * @desc: 基于大小的驱逐策略(可以细分为基于缓存大小和基于权重两种)
 * @date: Created in 2018/8/21 15:40
 */
public class SizeBasedEviction extends Base {

    public static void main(String[] args) {
        // 基于缓存大小进行驱逐
        LoadingCache<String, Graph> cache = Caffeine.newBuilder()
                .maximumSize(1)
                .build(key -> createExpensiveGraph(key));

        cache.get("A");
        System.out.println(cache.estimatedSize());
        cache.get("B");
        // 因为执行回收的方法是异步的，所以需要调用该方法，手动触发一次回收操作。
        cache.cleanUp();
        System.out.println(cache.estimatedSize());


        // 基于权重来进行驱逐（权重只是用于确定缓存大小，不会用于决定该缓存是否被驱逐）
        cache = Caffeine.newBuilder()
                .maximumWeight(100)
                // 根据Graph对象字段计算每个Graph权重，结果为Integer类型
                .weigher((String key, Graph graph) -> graph.getWeight())
                .build(key -> createExpensiveGraph(key, 10));
        for (int index = 0; index < 100; index++) {
            cache.get("A" + index);
        }
        // 因为执行回收的方法是异步的，所以需要调用该方法，手动触发一次回收操作。
        cache.cleanUp();
        System.out.println(cache.estimatedSize());
    }
}
