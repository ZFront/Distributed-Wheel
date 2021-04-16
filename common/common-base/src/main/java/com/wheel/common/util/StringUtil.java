package com.wheel.common.util;

import java.util.UUID;

public class StringUtil {

    public static boolean isEmpty(CharSequence value) {
        return value == null || value.toString().trim().length() == 0;
    }

    public static boolean isNotEmpty(CharSequence value) {
        return !isEmpty(value);
    }

    public static boolean isLengthOver(String str, int maxLen) {
        if (str == null) {
            return false;
        } else {
            return str.length() > maxLen;
        }
    }

    public static boolean isLengthOk(String str, int minLen, int maxLen) {
        if (str == null) {
            return false;
        } else {
            return minLen <= str.length() && str.length() <= maxLen;
        }
    }

    public static String getUUIDStr() {
        return UUID.randomUUID().toString();
    }


    /**
     * 取字符串前n位，如果原字符串不足n位,则返回原字符串
     *
     * @param str .
     * @param n   .
     * @return
     */
    public static String subLeft(String str, int n) {
        if (str == null || str.length() <= n) {
            return str;
        } else {
            return str.substring(0, n);
        }
    }

    /**
     * 取字符串后n位，如果原字符串不足n位,则返回原字符串
     *
     * @param str .
     * @param n   .
     * @return
     */
    public static String subRight(String str, int n) {
        if (str == null || str.length() <= n) {
            return str;
        } else {
            return str.substring(str.length() - n);
        }
    }

}
