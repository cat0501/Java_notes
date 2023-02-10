package com.atguigu.admin.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5是一种加密算法
 * 用来将明文密码字符串加密为密文密码字符串
 */
public final class MD5 {

    public static String encrypt(String strSrc) {
        try {
            // 16进制的字符
            char[] hexChars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
            // 源字符串的字节数组
            byte[] bytes = strSrc.getBytes();
            // 获取MD5的加密实例
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(bytes);
            bytes = md.digest();
            int j = bytes.length;
            char[] chars = new char[j * 2];
            int k = 0;
            // 遍历字节数组，将字节数组中的每一个字节都转换成一个16进制的字符
            for (byte b : bytes) {
                chars[k++] = hexChars[b >>> 4 & 0xf];
                chars[k++] = hexChars[b & 0xf];
            }
            // 将字节数组转成字符串返回
            return new String(chars);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException("MD5加密出错！！+" + e);
        }
    }

    public static void main(String[] args) {
        // 96e79218965eb72c92a549dd5a330112
        String password = "111111";
        System.out.println(MD5.encrypt(password));
    }
}