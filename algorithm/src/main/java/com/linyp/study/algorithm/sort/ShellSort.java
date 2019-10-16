    package com.linyp.study.algorithm.sort;

import java.util.Arrays;

    /**
     * Created by Lin on 2016/5/9.
     *
     * 希尔排序
     */
    public class ShellSort {
        public static void main(String[] args) {
            int[] array = new int[]{49, 38, 65, 97, 26, 13, 27, 49, 55, 4};
            shellSort(array,array.length);
            System.out.println(Arrays.toString(array));
        }

        /**
         * 希尔排序
         * @param array
         * @param length
         */
        public static void shellSort(int[] array, int length) {
            int i,j,gap;    // gap为步长
            for(gap = length/2; gap > 0; gap /= 2) {
                for(i = gap; i < length; i++) {
                    for(j = i-gap; j >= 0 && array[j] > array[j+gap]; j -= gap)
                        swap(array,j,j+gap);
                }
            }
        }

        /**
         * 交换位置
         * @param array
         * @param index
         * @param index1
         */
        private static void swap(int[] array, int index, int index1) {
            int temp = array[index];
            array[index] = array[index1];
            array[index1] = temp;
        }


        /**
         * 希尔排序
         * @param array
         * @param length
         */
        /*public static void shellSort(int[] array,int length) {
            int i,j,gap;    //gap为步长
            for(gap = length/2; gap > 0; gap /= 2) {
                for(i = 0; i < gap; i++) {
                    for(j = gap+i; j < length; j++) {
                        if(array[j] < array[j-gap]) {
                            int temp=array[j];
                            int k=j-gap;
                            while( k >= 0 && array[k] > temp) {
                                array[k+gap] = array[k];
                                k -= gap;
                            }
                            array[k+gap] = temp;
                        }
                    }
                }
            }
        }*/
    }
