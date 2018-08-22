package com.linyp.study.refresh;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.linyp.study.common.Base;
import com.linyp.study.common.Graph;

import java.util.concurrent.TimeUnit;

/**
 * @author: Yinpeng.Lin
 * @desc: 刷新（异步地为key加载新的value，并返回旧值）
 * @date: Created in 2018/8/21 18:05
 */
public class Refresh extends Base {

    public static void main(String[] args) {
        LoadingCache<String, Graph> cache = Caffeine.newBuilder()
                .maximumSize(1000)
                // 指定在创建缓存或者最近一次更新缓存后经过固定的时间间隔，刷新缓存
                .refreshAfterWrite(1, TimeUnit.MINUTES)
                .build(key -> createExpensiveGraph(key));
    }
}
