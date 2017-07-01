package com.linyp.study.algorithm;

import java.util.Arrays;

/**
 * Created by linyp on 2017/6/30.
 * 归并排序
 */
public class MergeSort {
    /**
     * 递归版归并排序
     * @param arr
     * @param result
     * @param start
     * @param end
     */
    static void merge_sort_recursive(int[] arr, int[] result, int start, int end) {
        if (start >= end)
            return;
        int len = end - start, mid = (len >> 1) + start;
        int start1 = start, end1 = mid;
        int start2 = mid + 1, end2 = end;
        merge_sort_recursive(arr, result, start1, end1);
        merge_sort_recursive(arr, result, start2, end2);
        int k = start;
        while (start1 <= end1 && start2 <= end2)
            result[k++] = arr[start1] < arr[start2] ? arr[start1++] : arr[start2++];
        while (start1 <= end1)
            result[k++] = arr[start1++];
        while (start2 <= end2)
            result[k++] = arr[start2++];
        for (k = start; k <= end; k++)
            arr[k] = result[k];
    }

    /**
     * 迭代版归并排序
     * @param arr
     */
    public static void merge_sort(int[] arr, int[] result) {
        int len = arr.length;
        int block, start;

        // 原版代码的迭代次数少了一次，没有考虑到奇数列数组的情况
        for(block = 1; block < len*2; block *= 2) {
            for(start = 0; start <len; start += 2 * block) {
                int low = start;
                int mid = (start + block) < len ? (start + block) : len;
                int high = (start + 2 * block) < len ? (start + 2 * block) : len;
                //两个块的起始下标及结束下标
                int start1 = low, end1 = mid;
                int start2 = mid, end2 = high;
                //开始对两个block进行归并排序
                while (start1 < end1 && start2 < end2) {
                    result[low++] = arr[start1] < arr[start2] ? arr[start1++] : arr[start2++];
                }
                while(start1 < end1) {
                    result[low++] = arr[start1++];
                }
                while(start2 < end2) {
                    result[low++] = arr[start2++];
                }
            }
            int[] temp = arr;
            arr = result;
            result = temp;
        }
        result = arr;
    }

    public static void main(String[] args) {
        int[] noSortArr = {34, 21, 5, 18, 55, 1, 64, 32, 19};
        int len = noSortArr.length;
        int[] result = new int[len];
        //merge_sort_recursive(noSortArr, result, 0, len - 1);
        merge_sort(noSortArr, result);
        System.out.println(Arrays.toString(result));
    }
}
