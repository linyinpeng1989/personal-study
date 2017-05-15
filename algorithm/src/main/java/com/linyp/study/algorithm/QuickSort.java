package com.linyp.study.algorithm;

import java.util.Arrays;

/**
 * Created by Lin on 2016/5/9.
 *
 * 快速排序
 */
public class QuickSort {
    public static void main(String[] args) {
        int[] array = new int[]{49, 38, 65, 97, 26, 13, 27, 49, 55, 4};
        quickSort(array, 0, array.length - 1);
        System.out.println(Arrays.toString(array));
    }

    /**
     * 快速排序---取第一个数作为基准数
     * @param array 无序数组
     * @param l 左侧指针
     * @param r 右侧指针
     */
    public static void quickSort(int[] array, int l, int r) {
        if (l < r) {
            //Swap(s[l], s[(l + r) / 2]); // 如果取中间的这个数作为基准数，可先将它和第一个数交换，之后再进行下面的操作
            int i = l, j = r;
            int x = array[l];   // 挖坑，取第一个数作为基准数
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
            quickSort(array, l, i-1);   // 递归调用，对左侧再次进行快速排序
            quickSort(array, i+1, r);   // 递归调用，对右侧再次进行快速排序
        }
    }
}
