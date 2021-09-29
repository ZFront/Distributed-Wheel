package com.wheel.algorithm.leetcode.sort;

import com.wheel.common.util.JsonUtil;

/**
 * @desc 快速排序 -- 分冶算法 + 递归算法的典型实现。
 * <p>
 * 思想：
 * 在待排序的数列中，我们首先要找一个数字作为基准数（这只是个专用名词）。
 * 为了方便，我们一般选择第 1 个数字作为基准数（其实选择第几个并没有关系）。
 * 接下来我们需要把这个待排序的数列中小于基准数的元素移动到待排序的数列的左边，把大于基准数的元素移动到待排序的数列的右边。
 * 这时，左右两个分区的元素就相对有序了；
 * 接着把两个分区的元素分别按照上面两种方法继续对每个分区找出基准数，然后移动，直到各个分区只有一个数时为止。
 * <p>
 * {@link https://wiki.jikexueyuan.com/project/easy-learn-algorithm/fast-sort.html}
 * @author: zhouf
 */
public class FastSort {

    public static void main(String[] args) {
        int n[] = {6, 5, 2, 7, 3, 9, 8, 4, 10, 1};
        fastSort(n);
        System.out.println(JsonUtil.toString(n));
    }

    public static void fastSort(int n[]) {
        sort(n, 0, n.length - 1);
    }

    public static void sort(int n[], int l, int r) {
        if (l < r) {
            // 一趟快拍，返回交换后基数的下标
            int index = patition(n, l, r);
            // 递归排序基数左边的数组
            sort(n, l, index - 1);
            // 递归排序基数右边的数组
            sort(n, index + 1, r);
        }
    }

    public static int patition(int n[], int l, int r) {
        // p为基数，即待排序数组的第一个数
        int p = n[l];
        int i = l;
        int j = r;
        while (i < j) {
            // 从右往左找第一个小于基数的数
            while (n[j] >= p && i < j) {
                j--;
            }
            // 从左往右找第一个大于基数的数
            while (n[i] <= p && i < j) {
                i++;
            }
            // 找到后交换两个数
            swap(n, i, j);
        }
        // 使划分好的数分布在基数两侧
        swap(n, l, i);
        return i;
    }

    private static void swap(int n[], int i, int j) {
        int temp = n[i];
        n[i] = n[j];
        n[j] = temp;
    }
}
