package com.linyp.study.policy;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.linyp.study.common.Base;
import com.linyp.study.common.Graph;

import java.util.Map;

/**
 * @author: Yinpeng.Lin
 * @desc:
 * @date: Created in 2018/8/22 11:09
 */
public class SizeBased extends Base {

    public static void main(String[] args) {
        // 基于缓存大小进行驱逐
        LoadingCache<String, Graph> cache = Caffeine.newBuilder()
                .maximumSize(1)
                .build(key -> createExpensiveGraph(key));
        cache.policy().eviction().ifPresent(eviction -> {
            // getMaximum()用于获取缓存的最大值(maximum)或最大权重(weight)
            eviction.setMaximum(2 * eviction.getMaximum());

            // 最有可能命中的10条数据和最有可能驱逐的10条数据
            Map<String, Graph> hottest = eviction.hottest(10);
            Map<String, Graph> coldest = eviction.coldest(10);
        });
    }
}
