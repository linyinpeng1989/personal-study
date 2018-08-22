package com.linyp.study.statistics;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.stats.CacheStats;
import com.linyp.study.common.Base;
import com.linyp.study.common.Graph;

import java.util.concurrent.TimeUnit;

/**
 * @author: Yinpeng.Lin
 * @desc:
 * @date: Created in 2018/8/22 10:45
 */
public class Statistics extends Base {

    public static void main(String[] args) {
        Cache<String, Graph> cache = Caffeine.newBuilder()
                .maximumSize(2)
                .expireAfterWrite(1, TimeUnit.MINUTES)
                // 统计缓存信息
                .recordStats()
                .build();

        cache.get("A", k -> createExpensiveGraph(k));
        cache.get("B", k -> createExpensiveGraph(k));
        cache.get("C", k -> createExpensiveGraph(k));

        for (int index = 0; index < 1000; index++) {
            int mod = index % 5;
            switch (mod) {
                case 0 :
                    cache.getIfPresent("A");
                    break;
                case 1 :
                    cache.getIfPresent("B");
                    break;
                case 2 :
                    cache.getIfPresent("C");
                    break;
                case 3 :
                    cache.getIfPresent("D");
                    break;
                case 4 :
                    cache.getIfPresent("E");
                    break;
                default:
                    cache.getIfPresent("A");
            }
        }
        CacheStats stats = cache.stats();
        // 返回命中与请求的比率
        System.out.println(stats.hitRate());
        // 返回命中缓存的总数
        System.out.println(stats.hitCount());
        // 缓存逐出的数量
        System.out.println(stats.evictionCount());
        // 加载新值所花费的平均时间(纳秒)
        System.out.println(stats.averageLoadPenalty());
        // 加载总数
        System.out.println(stats.loadCount());
        // 加载成功总数
        System.out.println(stats.loadSuccessCount());
        // 加载失败总数
        System.out.println(stats.loadFailureCount());
        // 加载失败比例
        System.out.println(stats.loadFailureRate());
    }
}
