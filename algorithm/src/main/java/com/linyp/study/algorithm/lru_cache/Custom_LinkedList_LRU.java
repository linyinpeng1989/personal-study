package com.linyp.study.algorithm.lru_cache;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.Scanner;

/**
 * @author: Yinpeng.Lin
 * @desc: 自定义链表实现LRU缓存
 * @date: Created in 2019/10/15 14:01
 * @since 1.0
 */
@Data
public class Custom_LinkedList_LRU<T> {

    /**
     * 链表头结点
     */
    private Node<T> headNode;

    /**
     * 链表容量
     */
    private Integer capacity;

    /**
     * 链表当前长度
     */
    private Integer size;

    public Custom_LinkedList_LRU() {
        this(10);
    }

    public Custom_LinkedList_LRU(Integer capacity) {
        this.headNode = new Node();
        this.capacity = capacity;
        this.size = 0;
    }

    /**
     * 插入数据(LRU算法实现)
     *
     * @param data
     */
    public void add(T data) {
        // 查询链表中是否存在结点，其数据为data
        Node preNode = findPreNode(data);
        if (Objects.nonNull(preNode)) {
            // 删除当前结点
            deleteNext(preNode);
            insertAtBegin(data);
        } else {
            // 如果容量满了，删除链表尾部节点
            if (size >= capacity) {
                deleteAtEnd();
            }
            insertAtBegin(data);
        }
    }

    /**
     * 查找链表中值为 data 的结点的上一个结点
     *
     * @param data
     * @return
     */
    private Node findPreNode(T data) {
        // 头结点不包含数据，相当于哨兵结点
        Node node = headNode;
        // 从头结点开始遍历链表，找到相等的结点，则返回该节点的上一个结点
        while (node.getNext() != null) {
            if (Objects.equals(data, node.getNext().getElement())) {
                return node;
            }
            node = node.getNext();
        }
        return null;
    }

    /**
     * 删除当前结点的下一个结点
     *
     * @param preNode
     */
    private void deleteNext(Node preNode) {
        preNode.setNext(preNode.getNext().getNext());
        size--;
    }

    /**
     * 删除链表的尾结点
     */
    private void deleteAtEnd() {
        Node preNode = headNode;
        // 空链表直接返回
        if (Objects.isNull(preNode.getNext())) {
            return;
        }

        // 查找链表倒数第二个结点
        while (Objects.nonNull(preNode.getNext().getNext())) {
            preNode = preNode.getNext();
        }
        preNode.setNext(null);
        size--;
    }

    /**
     * 在链表表头插入结点
     *
     * @param data
     */
    private void insertAtBegin(T data) {
        Node begin = headNode.getNext();
        Node newBegin = new Node(data, begin);
        headNode.setNext(newBegin);
        size++;
    }

    /**
     * 打印链表
     */
    private void printAll() {
        Node node = headNode.getNext();
        while (node != null) {
            System.out.print(node.getElement() + ",");
            node = node.getNext();
        }
        System.out.println();
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private class Node<E> {
        /**
         * 链表结点元素
         */
        private E element;
        /**
         * 指向下一个结点的指针
         */
        private Node<E> next;
    }

    public static void main(String[] args) {
        Custom_LinkedList_LRU linkedListLru = new Custom_LinkedList_LRU();
        Scanner sc = new Scanner(System.in);
        while (true) {
            linkedListLru.add(sc.nextInt());
            linkedListLru.printAll();
        }
    }

}
