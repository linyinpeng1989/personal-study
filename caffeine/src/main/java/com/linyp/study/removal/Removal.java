package com.linyp.study.removal;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.RemovalCause;
import com.linyp.study.common.Graph;

/**
 * @author: Yinpeng.Lin
 * @desc: 移除监听器（当元素移除时执行某些操作）
 * @date: Created in 2018/8/21 17:43
 */
public class Removal {

    public static void main(String[] args) throws InterruptedException {
        Cache<String, Graph> cache = Caffeine.newBuilder()
                .removalListener((String key, Graph graph, RemovalCause cause) -> {
                    System.out.println("Do Something");
                    System.out.println("Key " + key + " was removed. Cause was " + cause.toString());
                })
                .build();
        Graph graph = new Graph("A", "A_123");
        cache.put(graph.getKey(), graph);
        System.out.println(cache.estimatedSize());

        cache.invalidate("A");
        // 移除监听器使用Executor异步执行，默认使用ForkJoinPool
        Thread.sleep(1);
        System.out.println(cache.estimatedSize());

    }
}
