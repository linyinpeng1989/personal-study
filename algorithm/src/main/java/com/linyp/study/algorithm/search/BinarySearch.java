package com.linyp.study.algorithm.search;

import java.math.BigDecimal;

/**
 * @author: Yinpeng.Lin
 * @desc: 二分查找
 * <p>
 * 针对有序的数据集合，每次都通过跟区间的中间元素对比，将待查找的区间缩小为之前的一半，
 * 直到找到要查找的元素，或者区间被缩小为 0。
 * @date: Created in 2019/10/16 13:41
 * @since 1.0
 */
public class BinarySearch {

    public static void main(String[] args) {
        int[] array = new int[]{1, 3, 4, 5, 6, 8, 8, 8, 11, 18};
        System.out.println(binarySearch1(array, 8));
        System.out.println(binarySearch2(array, 0, array.length - 1, 8));
        System.out.println(binarySearchFirstValue1(array, 8));
        System.out.println(binarySearchFirstValue2(array, 8));
        System.out.println(binarySearchLastValue1(array, 8));
        System.out.println(binarySearchLastValue2(array, 8));
        System.out.println(binarySearchFirstGEValue1(array, 4));
        System.out.println(binarySearchFirstGEValue2(array, 4));
        System.out.println(binarySearchLastGEValue1(array, 7));
        System.out.println(binarySearchLastGEValue2(array, 7));

        System.out.println(sqrt(1.45, 6));

    }


    /**
     * 二分查找（循环实现）
     *
     * @param array  有序数组
     * @param target 目标值
     * @return 目标值的下标
     */
    public static int binarySearch1(int[] array, int target) {
        int low = 0, high = array.length - 1;
        int mid = -1;
        while (low <= high) {
            // mid = (low + high) / 2;
            // 防止两个大整型相加，导致溢出
            mid = low + ((high - low) >> 1);
            if (array[mid] == target) {
                return mid;
            } else if (array[mid] > target) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return mid;
    }

    /**
     * 二分查找（递归实现）
     *
     * @param array  有序数组
     * @param low    低位
     * @param high   高位
     * @param target 目标值
     * @return 目标值的下标
     */
    public static int binarySearch2(int[] array, int low, int high, int target) {
        if (low > high) {
            return -1;
        }
        // int mid = (low + high) / 2;
        // 防止两个大整型相加，导致溢出
        int mid = low + ((high - low) >> 1);
        if (array[mid] == target) {
            return mid;
        } else if (array[mid] > target) {
            return binarySearch2(array, low, mid - 1, target);
        } else {
            return binarySearch2(array, mid + 1, high, target);
        }
    }

    /**
     * 查找第一个等于给定值的元素
     *
     * @param array
     * @param target
     * @return
     */
    public static int binarySearchFirstValue1(int[] array, int target) {
        int low = 0, high = array.length - 1;
        while (low <= high) {
            int mid = low + ((high - low) >> 1);
            // 中间值大于或等于目标值，high均更新为 mid - 1，继续对左子树进行二分查找
            if (array[mid] >= target) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        if (low < array.length && array[low] == target) {
            return low;
        }
        return -1;
    }

    /**
     * 查找第一个等于给定值的元素
     *
     * @param array
     * @param target
     * @return
     */
    public static int binarySearchFirstValue2(int[] array, int target) {
        int low = 0, high = array.length - 1;
        while (low <= high) {
            int mid = low + ((high - low) >> 1);
            // 中间值大于或等于目标值，high均更新为 mid - 1，继续对左子树进行二分查找
            if (array[mid] > target) {
                high = mid - 1;
            } else if (array[mid] < target){
                low = mid + 1;
            } else {
                if (mid == 0 || array[mid - 1] != target) {
                    return mid;
                }
                high = mid - 1;
            }
        }
        return -1;
    }

    /**
     * 查找最后一个等于给定值的元素
     *
     * @param array
     * @param target
     * @return
     */
    public static int binarySearchLastValue1(int[] array, int target) {
        int low = 0, high = array.length - 1;
        while (low <= high) {
            int mid = low + ((high - low) >> 1);
            // 中间值小于或等于目标值，low均更新为 mid + 1，继续对右子树进行二分查找
            if (array[mid] <= target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        if (high < array.length && array[high] == target) {
            return high;
        }
        return -1;
    }

    /**
     * 查找最后一个等于给定值的元素
     *
     * @param array
     * @param target
     * @return
     */
    public static int binarySearchLastValue2(int[] array, int target) {
        int low = 0, high = array.length - 1;
        while (low <= high) {
            int mid = low + ((high - low) >> 1);
            // 中间值小于或等于目标值，low均更新为 mid + 1，继续对右子树进行二分查找
            if (array[mid] < target) {
                low = mid + 1;
            } else if (array[mid] > target){
                high = mid - 1;
            } else {
                // 如果已经遍历完成，或者下一个元素不符合要求，则直接返回
                if (mid == array.length - 1 || array[mid + 1] != target) {
                    return mid;
                }
                low = mid + 1;
            }
        }
        return -1;
    }

    /**
     * 查找第一个大于等于给定值的元素
     *
     * @param array
     * @param target
     * @return
     */
    public static int binarySearchFirstGEValue1(int[] array, int target) {
        int low = 0, high = array.length - 1;
        while (low <= high) {
            int mid = low + ((high - low) >> 1);
            // 中间值大于或等于目标值，high均更新为 mid - 1，继续对左子树进行二分查找
            if (array[mid] >= target) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        if (low < array.length && array[low] >= target) {
            return low;
        }
        return -1;
    }

    /**
     * 查找第一个大于等于给定值的元素
     *
     * @param array
     * @param target
     * @return
     */
    public static int binarySearchFirstGEValue2(int[] array, int target) {
        int low = 0, high = array.length - 1;
        while (low <= high) {
            int mid = low + ((high - low) >> 1);
            // 中间值大于或等于目标值，high均更新为 mid - 1，继续对左子树进行二分查找
            if (array[mid] >= target) {
                if (mid == 0 || array[mid - 1] < target) {
                    return mid;
                }
                high = mid - 1;
            } else if (array[mid] < target){
                low = mid + 1;
            }
        }
        return -1;
    }

    /**
     * 查找最后一个小于等于给定值的元素
     *
     * @param array
     * @param target
     * @return
     */
    public static int binarySearchLastGEValue1(int[] array, int target) {
        int low = 0, high = array.length - 1;
        while (low <= high) {
            int mid = low + ((high - low) >> 1);
            // 中间值小于等于目标值，low更新为 mid + 1，继续对右子树进行二分查找
            if (array[mid] <= target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        if (high < array.length && array[high] <= target) {
            return high;
        }
        return -1;
    }

    /**
     * 查找最后一个小于等于给定值的元素
     *
     * @param array
     * @param target
     * @return
     */
    public static int binarySearchLastGEValue2(int[] array, int target) {
        int low = 0, high = array.length - 1;
        while (low <= high) {
            int mid = low + ((high - low) >> 1);
            // 中间值小于等于目标值，low更新为 mid + 1，继续对右子树进行二分查找
            if (array[mid] <= target) {
                if (mid == array.length - 1 || array[mid + 1] > target) {
                    return mid;
                }
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }

    /**
     * 求一个数的平方根
     *
     * @param originData 原始数值
     * @param precision  精度位数
     * @return
     */
    public static double sqrt(double originData, int precision) {
        if (originData <= 0) {
            return -1;
        }

        // 计算精度
        double precis = getPrecision(precision);

        double mid = -1;

        // 定义二分查找上下限
        double low = 0;
        double high = originData;
        while (high - low > precis) {
            mid = low + (high - low) / 2;
            double pro = mid * mid;
            if (pro > originData) {
                high = mid;
            } else if (pro < originData) {
                low = mid;
            } else if (Math.abs(pro - originData) < precis){
                return mid;
            }
        }
        System.out.println(mid);
        // 数据精度，BigDecimal使用字符串创建
        BigDecimal bigDecimal = new BigDecimal(String.valueOf(mid)).setScale(precision, BigDecimal.ROUND_HALF_UP);
        return bigDecimal.doubleValue();
    }

    /**
     * 计算精度
     *
     * @param precision
     * @return
     */
    private static double getPrecision(int precision) {
        if (precision < 1) {
            return 0;
        }
        double precis = 1;
        for (int i = 0; i < precision; i++) {
            precis *= 10;
        }
        return 1 / precis;
    }

}
