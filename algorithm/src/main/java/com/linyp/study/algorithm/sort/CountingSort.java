package com.linyp.study.algorithm.sort;

/**
 * @author: Yinpeng.Lin
 * @desc: 计数排序
 * @date: Created in 2019/10/15 19:21
 * @since 1.0
 */
public class CountingSort {

    public static void countingSort(int[] array) {
        // 如果数组为空，或者数组只有一个元素
        if (array == null || array.length <= 1) {
            return;
        }

        // 获取数组中最大的值
        int max = getMaxOfArray(array);

        // 申请一个计数数组c，下标范围[0, max]
        int[] c = new int[max + 1];

        // 遍历数组并计算每个元素的个数，放入c
        for (int index = 0; index < array.length; index++) {
            c[array[index]]++;
        }

        // 数组c依次求和
        for (int index = 1; index < c.length; index++) {
            c[index] = c[index] + c[index -1];
        }

        // 申请临时数组r，用于临时存储排好序后的数组
        int[] r = new int[array.length];
        // 倒序遍历数组array，计算各个元素在有序数组中的位置
        for (int i = array.length - 1; i >= 0; i--) {
            int index = c[array[i]] - 1;
            r[index] = array[i];
            c[array[i]]--;
        }

        // 将排好序的临时数组r复制到array
        System.arraycopy(r, 0, array, 0, r.length);
    }

    /**
     * 获取数组中最大值
     * @param array
     * @return
     */
    private static int getMaxOfArray(int[] array) {
        int max = array[0];
        for (int index = 1; index < array.length; index++) {
            if (max < array[index]) {
                max = array[index];
            }
        }
        return max;
    }

}
