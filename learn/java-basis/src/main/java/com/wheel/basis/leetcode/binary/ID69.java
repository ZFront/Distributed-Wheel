package com.wheel.basis.leetcode.binary;

/**
 * @desc
 * @author: zhouf
 */
public class ID69 {

    public static void main(String[] args) {
        System.out.println(mySqrt(8));
        System.out.println(mySqrt(17));
    }

    /**
     * 上界限，不一定非得是x，只是做一个粗略的估算
     * 在二分查找的每一步，只要比较中间元素mid的平方跟X的关系，并通过比较的结果调整上下界的范围
     *
     * @param x
     * @return
     */
    public static int mySqrt(int x) {
        if (x <= 1) {
            return x;
        }
        int l = 1, h = x;
        while (l <= h) {
            int mid = l + (h - l) / 2;
            int sqrt = x / mid;
            if (sqrt == mid) {
                return mid;
            } else if (mid > sqrt) {
                h = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        return h;
    }
}
