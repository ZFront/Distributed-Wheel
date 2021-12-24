package com.wheel.learn.algorithm.leetcode.string;

import java.util.Arrays;

/**
 * @desc 有效的字母异位词
 * {@link https://leetcode-cn.com/problems/valid-anagram/description/}
 * @author: zhouf
 */
public class ID242 {

    public static void main(String[] args) {
        System.out.println(isAnagram("anagram", "nagaram"));

        System.out.println(isAnagram("nagaram", "anagram"));
    }

    /**
     * 排序比较
     */
    public static boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        char[] str1 = s.toCharArray();
        char[] str2 = t.toCharArray();
        Arrays.sort(str1);
        Arrays.sort(str2);
        return Arrays.equals(str1, str2);
    }

    /**
     * hashMap
     * 通过记录频次，来进行处理。
     * 如果频次不一样，说明存在额外的字符
     *
     * @return
     */
    public static boolean isAnagram2(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        int[] table = new int[26];
        for (int i = 0; i < s.length(); i++) {
            table[s.charAt(i) - 'a']++;
        }
        for (int i = 0; i < t.length(); i++) {
            table[t.charAt(i) - 'a']--;
            if (table[t.charAt(i) - 'a'] < 0) {
                return false;
            }
        }
        return true;
    }
}
