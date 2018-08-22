package com.linyp.study.test;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.google.common.testing.FakeTicker;
import com.linyp.study.common.Graph;

import java.util.concurrent.TimeUnit;

/**
 * @author: Yinpeng.Lin
 * @desc:
 * @date: Created in 2018/8/22 11:53
 */
public class TickerTest {

    public static void main(String[] args) {
        FakeTicker ticker = new FakeTicker();
        Cache<String, Graph> cache = Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .executor(Runnable::run)
                // 读取ticker时间
                .ticker(ticker::read)
                .maximumSize(10)
                .build();

        cache.put("A", new Graph("A", "A_123"));
        // 设置ticker为当前时间往后30分钟，此时缓存过期
        ticker.advance(30, TimeUnit.MINUTES);
        Graph graph = cache.getIfPresent("A");
        System.out.println(graph);
    }
}
