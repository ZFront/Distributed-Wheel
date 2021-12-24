package com.wheel.learn.algorithm.leetcode.pointer;

/**
 * @desc 验证回文字符串 Ⅱ
 * {@link https://leetcode-cn.com/problems/valid-palindrome-ii/description/}
 * @author: zhouf
 */
public class ID680 {

    /**
     * 关键的难点是怎么处理删除字符，
     * 在使用双指针遍历字符串时，如果出现两个指针指向的字符不相等的情况，我们就试着删除一个字符，再判断删除完之后的字符串是否是回文字符串
     * 且在试着删除字符时，既可以删除左边的字符，也可以删除右边的字符
     * @param s
     * @return
     */
    public static boolean validPalindrome(String s) {
        int i = 0, j = s.length() - 1;
        while (i < j) {
            if (s.charAt(i) != s.charAt(j)) {
                // 尝试比较删除左边的字符，或者尝试比较删除右边的字符
                return isValid(s, i + 1, j) || isValid(s, i, j - 1);
            }
            i++;
            j--;
        }
        return true;
    }

    public static boolean isValid(String s, int i, int j) {
        while (i < j) {
            if (s.charAt(i) != s.charAt(j)) {
                return false;
            }
            i++;
            j--;
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(validPalindrome("aba"));
        System.out.println(validPalindrome("abca"));
    }

}
