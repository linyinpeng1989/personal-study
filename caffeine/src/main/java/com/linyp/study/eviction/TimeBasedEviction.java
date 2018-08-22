package com.linyp.study.eviction;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Expiry;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.linyp.study.common.Base;
import com.linyp.study.common.Graph;

import java.util.concurrent.TimeUnit;

/**
 * @author: Yinpeng.Lin
 * @desc: 基于时间的驱逐策略
 * @date: Created in 2018/8/21 16:22
 */
public class TimeBasedEviction extends Base {

    public static void main(String[] args) throws InterruptedException {
        // 在最后一次访问或者写入后开始计时，在指定的时间后过期(最后一次访问后1秒钟)
        LoadingCache<String, Graph> cache = Caffeine.newBuilder()
                .expireAfterAccess(1, TimeUnit.SECONDS)
                .build(key -> createExpensiveGraph(key));
        cache.get("A");
        Thread.sleep(1100);
        // 因为执行回收的方法是异步的，所以需要调用该方法，手动触发一次回收操作。
        cache.cleanUp();
        System.out.println(cache.estimatedSize());

        // 基于固定的到期时间策略进行驱逐（写入后1秒钟）
        cache = Caffeine.newBuilder()
                .expireAfterWrite(1, TimeUnit.SECONDS)
                .build(key -> createExpensiveGraph(key));
        cache.get("B");
        Thread.sleep(1100);
        // 因为执行回收的方法是异步的，所以需要调用该方法，手动触发一次回收操作。
        cache.cleanUp();
        System.out.println(cache.estimatedSize());

        // 自定义策略
        cache = Caffeine.newBuilder()
                .expireAfter(new Expiry<String, Graph>() {
                    /**
                     * 自定义创建后过期时间，currentTime是以配置的Ticker为准的数据，与系统时间没有关系
                     */
                    @Override
                    public long expireAfterCreate(String key, Graph value, long currentTime) {
                        // 创建后1秒钟过期，可以基于Graph中某个时间属性进行计算
                        return TimeUnit.SECONDS.toNanos(1);
                    }

                    /**
                     * 自定义更新后过期时间，currentTime、currentDuration是以配置的Ticker为准的数据，与系统时间没有关系
                     */
                    @Override
                    public long expireAfterUpdate(String key, Graph value, long currentTime, long currentDuration) {
                        // 更新后1秒钟过期
                        return TimeUnit.SECONDS.toNanos(1);
                    }

                    /**
                     * 自定义访问后过期时间，currentTime、currentDuration是以配置的Ticker为准的数据，与系统时间没有关系
                     */
                    @Override
                    public long expireAfterRead(String key, Graph value, long currentTime, long currentDuration) {
                        // 访问后1秒钟过期
                        return TimeUnit.SECONDS.toNanos(1);
                    }
                })
                .build(key -> createExpensiveGraph(key));
        cache.get("C");
        Thread.sleep(1100);
        // 因为执行回收的方法是异步的，所以需要调用该方法，手动触发一次回收操作。
        cache.cleanUp();
        System.out.println(cache.estimatedSize());
    }
}
