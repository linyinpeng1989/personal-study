package com.linyp.study.eviction;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.linyp.study.common.Base;
import com.linyp.study.common.Graph;

/**
 * @author: Yinpeng.Lin
 * @desc: 基于引用的驱逐策略
 * @date: Created in 2018/8/21 17:29
 */
public class ReferenceBasedEviction extends Base {

    public static void main(String[] args) {
        // 当key和value都没有引用时驱逐缓存(弱引用在垃圾收集时会被回收)
        LoadingCache<String, Graph> cache = Caffeine.newBuilder()
                .weakKeys()
                .weakValues()
                .build(key -> createExpensiveGraph(key));

        // 当垃圾收集器需要释放内存时驱逐(软引用在内存不足时被垃圾收集器回收)
        cache = Caffeine.newBuilder()
                .softValues()
                .build(key -> createExpensiveGraph(key));
    }
}
