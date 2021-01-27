package com.yangf.pub_libs.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * @name： 杨帆
 * @Time： 2021年 01月 14日 18时 43分
 * @Data： 加密工具类
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
public class CryptUtil {
    /**
     * MD5加密
     *
     * @param value
     * @return
     */
    public static String encryptMD5(String value) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(value.getBytes(StandardCharsets.UTF_8));
            byte[] md = md5.digest();
            return binToHex(md);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String encryptMD5(byte[] value) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(value);
            byte[] md = md5.digest();
            return binToHex(md);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String binToHex(byte[] md) {
        StringBuilder sb = new StringBuilder();
        for (int b : md) {
            if (b < 0) {
                b += 256;
            }
            if (b < 16) {
                sb.append("0");
            }
            sb.append(Integer.toHexString(b));
        }
        return sb.toString();
    }
}
