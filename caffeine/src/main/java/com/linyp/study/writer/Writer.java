package com.linyp.study.writer;

import com.github.benmanes.caffeine.cache.CacheWriter;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.github.benmanes.caffeine.cache.RemovalCause;
import com.linyp.study.common.Base;
import com.linyp.study.common.Graph;

/**
 * @author: Yinpeng.Lin
 * @desc:
 * @date: Created in 2018/8/22 10:10
 */
public class Writer extends Base {

    public static void main(String[] args) {
        LoadingCache<String, Graph> cache = Caffeine.newBuilder()
                .writer(new CacheWriter<String, Graph>() {
                    @Override
                    public void write(String key, Graph value) {
                        // write to storage or secondary cache
                    }

                    @Override
                    public void delete(String key, Graph value, RemovalCause cause) {
                        // delete from storage or secondary cache
                    }
                })
                .build(key -> createExpensiveGraph(key));
    }
}
