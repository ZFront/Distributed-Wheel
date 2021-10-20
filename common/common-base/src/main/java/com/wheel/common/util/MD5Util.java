package com.wheel.common.util;

import org.apache.commons.codec.binary.Hex;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {

    public static String getMD5Hex(String str) {
        return String.valueOf(Hex.encodeHex(getMD5(str), true));
    }

    /**
     * @param str
     * @return
     */
    public static byte[] getMD5(String str) {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            if (StringUtil.isNotEmpty(str)) {
                messageDigest.update(str.getBytes("UTF-8"));
            }
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            throw new RuntimeException("生成MD5信息时异常", e);
        }
        return messageDigest.digest();
    }
}
