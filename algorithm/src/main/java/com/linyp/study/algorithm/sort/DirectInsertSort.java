package com.linyp.study.algorithm.sort;

import java.util.Arrays;

/**
 * Created by Lin on 2016/5/9.
 *
 * 直接插入排序
 */
public class DirectInsertSort {
    public static void main(String[] args) {
        int[] array = new int[]{65,27,59,64,58};
        //insertSort(array);
        binaryInsertSort(array);
        System.out.println(Arrays.toString(array));
    }

    /**
     * 直接插入排序算法(升序)
     * @param array 无序数组
     */
    public static void insertSort(int[] array) {
        int temp;
        // 从第2个数据开始插入
        for (int i = 1; i < array.length; i++) {
            int j = i-1;
            // 记录要插入的数据
            temp = array[i];
            // 从后往前判断,while循环结束后，下表从0 - j的值顺序不变，j+1 - i的值都往后移动一位
            while (j >= 0 && array[j] > temp) {
                array[j + 1] = array[j];
                j--;
            }
            // 将需要插入的值插入下标j + 1的位置
            if (j != i-1) {
                array[j + 1] = temp;
            }
        }
    }

    /**
     * 通过二分查找进行直接插入排序(升序)
     * @param array 无序数组
     */
    public static void binaryInsertSort(int[] array) {
        for (int i = 1; i < array.length; i++) { // 从第2个数据开始插入
            int index = findInsertIndex(array, i, array[i]);    // 二分寻找插入的位置
            if (i != index) {    // 插入位置不为i的时候，移动并插入
                int j = i,temp = array[i];
                while (j > index) {
                    array[j] = array[j - 1];
                    j--;
                }
                array[j] = temp;
            }
        }
    }
    /**
     * 查找数值iData在长度为iLen的array数组中的插入位置(二分查找)
     * @param array
     * @param iLen
     * @param iData
     * @return int  插入的位置
     */
    public static int findInsertIndex(int[] array, int iLen, int iData) {
        int ibegin = 0, iend = iLen-1, index = 0;
        while (ibegin < iend) {
            index = (ibegin + iend) / 2;
            if (array[index] > iData) iend = index - 1;
            else ibegin = index + 1;
        }
        if (array[index] <= iData) index ++;
        return index;
    }
}
