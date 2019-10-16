package com.linyp.study.algorithm.sort;

import java.util.Arrays;

/**
 * Created by Lin on 2016/5/9.
 *
 * 冒泡排序
 */
public class BubbleSort {
    public static void main(String[] args) {
        int[] array = new int[]{49, 38, 65, 97, 26, 13, 27, 49, 55, 4};
        // bubbleSort(array);
        bubbleSort1(array);
        // bubbleSort2(array);
        System.out.println(Arrays.toString(array));
    }

    /**
     * 冒泡排序
     *
     * @param array
     */
    public static void bubbleSort(int[] array) {
        int temp;
        // 比较的轮数
        for (int i = 0; i < array.length; i++) {
            // 对无序部分进行排序，将最大的元素放到最后（每经过一轮排序，有序部分元素会增加一个）
            for (int j = 0; j < array.length - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    temp = array[j + 1];
                    array[j + 1] = array[j];
                    array[j] = temp;
                }
            }
        }
    }

    /**
     * 冒泡排序
     * 优化：
     * 1.如果数组有序则退出循环，减少循环轮数
     *
     * @param array
     */
    public static void bubbleSort1(int[] array) {
        int temp;
        // 比较的轮数
        for (int i = 0; i < array.length; i++) {
            // 对无序部分进行排序，将最大的元素放到最后（每经过一轮排序，有序部分元素会增加一个）
            boolean isSorted = true;
            for (int j = 0; j < array.length - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    temp = array[j + 1];
                    array[j + 1] = array[j];
                    array[j] = temp;
                    isSorted = false;
                }
            }
            // 如果位置没有变动，则表示数组有序，退出循环
            if (isSorted) {
                break;
            }
        }
    }

    /**
     * 冒泡排序（和bubbleSort2效果一样，sortBoundary和array.length - i - 1其实都是确定无序部分的边界）
     * 优化：
     * 1.如果数组有序则退出循环，减少循环轮数
     *
     * @param array
     */
    public static void bubbleSort2(int[] array) {
        int temp;

        int sortBoundary = array.length - 1;
        int lastExchangeIndex = 0;
        // 比较的轮数
        for (int i = 0; i < array.length; i++) {
            // 对无序部分进行排序，将最大的元素放到最后（每经过一轮排序，有序部分元素会增加一个）
            boolean isSorted = true;
            for (int j = 0; j < sortBoundary; j++) {
                if (array[j] > array[j + 1]) {
                    temp = array[j + 1];
                    array[j + 1] = array[j];
                    array[j] = temp;
                    isSorted = false;
                    lastExchangeIndex = j;
                }
            }
            sortBoundary = lastExchangeIndex;
            // 如果位置没有变动，则表示数组有序，退出循环
            if (isSorted) {
                break;
            }
        }
    }

    /**
     * 特殊的冒泡排序 -- 鸡尾酒排序
     *      循环轮数减半，但是每轮的元素比较和交换是双向的：先从左到右遍历比较，然后再从右到左遍历比较
     * @param array
     */
    public static void bubbleSort3(int[] array) {

    }

}
