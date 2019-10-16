package com.linyp.study.algorithm.sort;

import java.util.Arrays;

/**
 * Created by Lin on 2016/5/9.
 *
 * 快速排序
 */
public class QuickSort {
    public static void main(String[] args) {
        int[] array = new int[]{49, 38, 65, 97, 26, 13, 27, 49, 55, 4};
        // quickSort1(array, 0, array.length - 1);
        quickSort2(array, 0, array.length - 1);
        System.out.println(Arrays.toString(array));
    }

    /**
     * 快速排序---取第一个数作为基准数
     * @param array 无序数组
     * @param l 左侧指针
     * @param r 右侧指针
     */
    public static void quickSort1(int[] array, int l, int r) {
        if (l < r) {
            int i = partition1(array, l, r);
            quickSort1(array, l, i-1);   // 递归调用，对左侧再次进行快速排序
            quickSort1(array, i+1, r);   // 递归调用，对右侧再次进行快速排序
        }
    }

    /**
     * 分区
     * @param array
     * @param fromIndex
     * @param endIndex
     * @return
     */
    private static int partition1(int[] array, int fromIndex, int endIndex) {
        //Swap(s[l], s[(l + r) / 2]); // 如果取中间的这个数作为基准数，可先将它和第一个数交换，之后再进行下面的操作
        int i = fromIndex, j = endIndex;
        int x = array[fromIndex];   // 挖坑，取第一个数作为基准数
        while (i < j) {
            // 从右往左遍历，直到找到第一个小于基准数的值
            while (i < j && array[j] >= x)
                j--;
            // 挖坑：将找到的小于基准数的值挖出    补坑：填充到上一个坑中
            // 并将左侧指针右移一位
            if (i < j)
                array[i++] = array[j];
            // 从左往右遍历，直到找到第一个大于等于基准数的值
            while (i < j && array[i] < x)
                i++;
            // 挖坑：将找到的大于等于基准数的值挖出   补坑：填充到上一个坑中
            if (i < j)
                array[j--] = array[i];
        }
        // 当i==j时，此轮结束，并把基准数填充到坑中
        array[i] = x;
        return i;
    }

    /**
     * 数组快速排序
     *
     * @param array
     */
    private static void quickSort2(int[] array, int fromIndex, int endIndex) {
        if (fromIndex >= endIndex) {
            return;
        }
        // 进行分区（即进行一次快速排序，q为基准数的下标）
        int q = partition2(array, fromIndex, endIndex);
        // 左分区、右分区分别进行快速排序
        quickSort2(array, fromIndex, q - 1);
        quickSort2(array, q + 1, endIndex);
    }

    /**
     * 执行快速排序
     * @param array
     * @param fromIndex
     * @param endIndex
     * @return
     */
    private static int partition2(int[] array, int fromIndex, int endIndex) {
        // 获取基准数
        int pivot = array[endIndex];
        int i = fromIndex;
        for (int j = fromIndex; j < endIndex; j++) {
            // 只有i结点值小于等于基准数，才进行交换，且i++
            if (array[j] <= pivot) {
                swap(array, i, j);
                i++;
            }
        }
        swap(array, i, endIndex);
        return i;
    }

    /**
     * 交换数组位置
     * @param array
     * @param i
     * @param j
     */
    private static void swap(int[] array, int i, int j){
        if (i == j) {
            return;
        }
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
