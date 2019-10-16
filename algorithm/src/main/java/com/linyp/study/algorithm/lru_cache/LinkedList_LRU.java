package com.linyp.study.algorithm.lru_cache;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.util.LinkedList;
import java.util.Objects;

/**
 * @author: Yinpeng.Lin
 * @desc: 使用单链表实现LRU缓存算法
 * @date: Created in 2019/10/11 14:23
 * @since 1.0
 */
@Data
public class LinkedList_LRU {

    /**
     * 缓存单链表
     */
    private LinkedList<String> cacheList;

    /**
     * 缓存大小限制
     */
    private Integer limit;

    public LinkedList_LRU(LinkedList<String> cacheList, Integer limit) {
        this.cacheList = cacheList;
        this.limit = limit;
    }

    public String get(String str) {
        // 遍历缓存链表，如果节点已经存在，则将该节点删除，并插入到链表头部
        for (int index = 0; index < cacheList.size(); index++) {
            String node = cacheList.get(index);
            if (Objects.equals(node, str)) {
                // 删除该节点
                cacheList.remove(index);
                // 插入到链表头部
                cacheList.addFirst(str);
                return str;
            }
        }

        // 如果缓存链表已满，删除链表尾部节点
        if (cacheList.size() >= limit) {
            cacheList.removeLast();
        }
        // 插入链表头部
        cacheList.addFirst(str);
        return str;
    }

    public static void main(String[] args) {
        LinkedList_LRU lruCache = new LinkedList_LRU(new LinkedList<>(), 5);

        // 链表未满情况
        lruCache.get("000");
        lruCache.get("111");
        lruCache.get("222");
        lruCache.get("333");
        lruCache.get("444");
        System.out.println(JSON.toJSONString(lruCache.getCacheList()));

        // 链表已满，查找旧数据，旧数据节点位置调整到表头
        lruCache.get("000");
        System.out.println(JSON.toJSONString(lruCache.getCacheList()));

        // 链表已满，查找新数据，删除表尾节点
        lruCache.get("555");
        System.out.println(JSON.toJSONString(lruCache.getCacheList()));
    }

}
