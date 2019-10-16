package com.linyp.study.algorithm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: Yinpeng.Lin
 * @desc: 链表算法
 *  1、单链表反转
 *  2、链表环检测
 *  3、两个有序链表的合并
 *  4、删除链表倒数第n个结点
 *  5、求链表的中间结点
 *
 * @date: Created in 2019/10/15 9:46
 * @since 1.0
 */
public class LinkedListAlgo {





    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private class Node<T> {
        /** 链表结点元素 */
        private T element;
        /** 指向下一个结点的指针 */
        private Node<T> next;
    }

}
