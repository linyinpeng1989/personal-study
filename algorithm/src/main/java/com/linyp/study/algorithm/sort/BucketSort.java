package com.linyp.study.algorithm.sort;

/**
 * @author: Yinpeng.Lin
 * @desc: 桶排序
 * @date: Created in 2019/10/16 10:15
 * @since 1.0
 */
public class BucketSort {

    /**
     * 桶排序
     *
     * @param array      待排序数组
     * @param bucketSize 桶大小
     */
    public static void bucketSort(int[] array, int bucketSize) {
        if (array == null || array.length < 2) {
            return;
        }

        // 计算数组最大值和最小值
        int minValue = array[0];
        int maxValue = array[1];
        for (int i = 0; i < array.length; i++) {
            if (array[i] < minValue) {
                minValue = array[i];
            } else if (array[i] > maxValue) {
                maxValue = array[i];
            }
        }

        // 确定桶数量
        int bucketCount = (maxValue - minValue)/bucketSize + 1;
        // 初始化桶
        int[][] buckets = new int[bucketCount][bucketSize];
        // 维护桶顺序以及每个桶中的元素个数
        int[] indexArr = new int[bucketCount];

        // 将数组中的值分配到各个桶中
        for (int i = 0; i < array.length; i++) {
            int bucketIndex = (array[i] - minValue) / bucketSize;
            // 如果该桶已满，进行扩容
            if (indexArr[bucketIndex] == buckets[bucketIndex].length) {
                ensureCapacity(buckets, bucketIndex);
            }
            buckets[bucketIndex][indexArr[bucketIndex]++] = array[i];
        }

        // 对每个桶进行排序，这里使用快速排序
        int k = 0;
        for (int i = 0; i < buckets.length; i++) {
            // 跳过空桶
            if (indexArr[i] == 0) {
                continue;
            }
            // 使用快速排序对桶中数据进行排序
            quickSortC(buckets[i], 0, indexArr[i] - 1);
        }
    }

    /**
     * 数组快速排序
     *
     * @param array
     */
    private static void quickSortC(int[] array, int fromIndex, int endIndex) {
        if (fromIndex >= endIndex) {
            return;
        }
        // 进行分区（即进行一次快速排序，q为基准数的下标）
        int q = partition(array, fromIndex, endIndex);
        // 左分区、右分区分别进行快速排序
        quickSortC(array, fromIndex, q - 1);
        quickSortC(array, q + 1, endIndex);
    }

    /**
     * 执行快速排序
     * @param array
     * @param fromIndex
     * @param endIndex
     * @return
     */
    private static int partition(int[] array, int fromIndex, int endIndex) {
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
    public static void swap(int[] array, int i, int j){
        if (i == j) {
            return;
        }
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    /**
     * 数组扩容
     * @param buckets
     * @param bucketIndex
     */
    private static void ensureCapacity(int[][] buckets, int bucketIndex) {
        int[] tempArr = buckets[bucketIndex];
        int[] newArr = new int[tempArr.length * 2];
        // 数据拷贝到新数组
        System.arraycopy(tempArr, 0, newArr, 0, tempArr.length);
        buckets[bucketIndex] = newArr;
    }

}
