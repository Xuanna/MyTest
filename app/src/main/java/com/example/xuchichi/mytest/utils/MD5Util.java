package com.example.xuchichi.mytest.utils;

import java.security.MessageDigest;

/**
 * Created by xuchichi on 2018/3/20.
 */

public class MD5Util {
    /***
     * MD5加密 生成32位md5码
     */
    public static String string2MD5(String inStr) {
        return bytes2MD5(inStr.getBytes());
    }


    /**
     * 将字节码数组转化成md5字符串
     * @param in
     * @return
     */
    public static String bytes2MD5(byte[] in) {
        if (in == null) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            return "";
        }
        byte[] md5Bytes = md5.digest(in);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < md5Bytes.length; i++) {
            /**
             * %02X生成md5为大写 %02x生成md5为小写
             */
            sb.append(String.format("%02x", (md5Bytes[i] & 0xFF)));
        }
        return sb.toString();
    }


    /**
     * 将字符串转化成md5字节码数组
     * @param inStr
     * @return
     */
    public static byte[] string2MD5Bytes(String inStr) {
        return bytes2MD5Bytes(inStr.getBytes());
    }


    /**
     * 普通字节码数组转化成md5字节码数组
     * @param in
     * @return
     */
    public static byte[] bytes2MD5Bytes(byte[] in) {
        if (in == null) {
            return null;
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            return null;
        }
        return md5.digest(in);
    }


    /**
     * 将md5字节码数组转化成字符串
     * @param md5bytes
     * @return
     */
    public static String MD5bytes2String(byte[] md5bytes) {
        if (md5bytes == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < md5bytes.length; i++) {
            sb.append(String.format("%02x", (md5bytes[i] & 0xFF)));
        }
        return sb.toString();
    }
}
