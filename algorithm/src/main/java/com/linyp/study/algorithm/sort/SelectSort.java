package com.linyp.study.algorithm.sort;

import java.util.Arrays;

/**
 * Created by Lin on 2016/5/9.
 *
 * 选择排序
 */
public class SelectSort {
    public static void main(String[] args) {
        int[] array = new int[]{49, 38, 65, 97, 26, 13, 27, 49, 55, 4};
        selectSort(array);
        System.out.println(Arrays.toString(array));
    }

    /**
     * 选择排序
     */
    public static void selectSort(int[] array) {
        int minIndex,temp;
        // 从起始位置不断递增
        for (int i = 0; i < array.length; i++) {
            // 找出从起始位置到最后一个元素的最小值，并返回它的位置
            minIndex = selectMinElem(array, i);
            if (minIndex != i) {
                temp = array[i];
                array[i] = array[minIndex];
                array[minIndex] = temp;
            }
        }
    }

    /**
     * 查找从下标为index开始的数组最小值，并返回它的下标
     * @param array
     * @param index
     */
    private static int selectMinElem(int[] array, int index) {
        int min = array[index];
        int minIndex = index;
        for (int i = index + 1; i < array.length; i++) {
            if (min >= array[i]) {
                min = array[i];
                minIndex = i;
            }
        }
        return minIndex;
    }
}
