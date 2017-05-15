package com.linyp.study.algorithm;

import java.util.Arrays;

/**
 * Created by Lin on 2016/5/9.
 *
 * 冒泡排序
 */
public class BubbleSort {
    public static void main(String[] args) {
        int[] array = new int[]{49, 38, 65, 97, 26, 13, 27, 49, 55, 4};
        bubbleSort(array);
        System.out.println(Arrays.toString(array));
    }

    /**
     * 冒泡排序
     * @param array
     */
    public static void bubbleSort(int[] array) {
        int temp;
        // 比较的轮数
        for (int i = 0; i < array.length; i++) {
            // 对剩余部分进行排序，将最大的元素放到最后
            for (int j = 0; j < array.length - i - 1; j++) {
                if (array[j] > array[j+1]) {
                    temp = array[j+1];
                    array[j+1] = array[j];
                    array[j] = temp;
                }
            }
        }
    }
}
