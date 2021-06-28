package com.wheel.basis.leetcode.binary;

/**
 * @desc {@link https://leetcode-cn.com/problems/find-smallest-letter-greater-than-target/description/}
 * @author: zhouf
 */
public class ID744 {

    public static void main(String[] args) {
        System.out.println(nextGreatestLetter("aflmz".toCharArray(), 'e'));
    }

    /**
     * @param letters 排序后的字母
     * @param target
     * @return
     */
    public static char nextGreatestLetter(char[] letters, char target) {
        int n = letters.length;
        int l = 0, h = n - 1;
        while (l <= h) {
            int m = l + (h - l) / 2;
            if (letters[m] <= target) {
                l = m + 1;
            } else {
                h = m - 1;
            }
        }
        return l < n ? letters[l] : letters[0];
    }
}
