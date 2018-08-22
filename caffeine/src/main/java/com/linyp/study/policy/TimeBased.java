package com.linyp.study.policy;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.linyp.study.common.Base;
import com.linyp.study.common.Graph;

import java.util.concurrent.TimeUnit;

/**
 * @author: Yinpeng.Lin
 * @desc:
 * @date: Created in 2018/8/22 11:14
 */
public class TimeBased extends Base {

    public static void main(String[] args) {
        // 在最后一次访问或者写入后开始计时，在指定的时间后过期(最后一次访问后1秒钟)
        LoadingCache<String, Graph> cache = Caffeine.newBuilder()
                .expireAfterAccess(1, TimeUnit.SECONDS)
                .build(key -> createExpensiveGraph(key));

        cache.policy().expireAfterAccess().ifPresent(expiration -> {
            long expiresAfter = expiration.getExpiresAfter(TimeUnit.MINUTES);
            expiration.setExpiresAfter(expiresAfter + 10, TimeUnit.MINUTES);
        });
        // cache.policy().expireAfterWrite().ifPresent(expiration -> ...);
        // cache.policy().expireVariably().ifPresent(expiration -> ...);
        // cache.policy().refreshAfterWrite().ifPresent(expiration -> ...);
    }
}
