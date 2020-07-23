package com.creolophus.liuyi.common.codec;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;

/**
 * 签名，无法解密
 * @author magicnana
 * @date 2019/5/20 下午5:49
 */
public class MD5Util {

    private  static final String salt = "3ce952e46063a08b";

    public static byte[] md5(byte[] data) {
        return DigestUtils.md5(data);
    }

    public static byte[] md5(String data) {
        return md5(data.getBytes());
    }

    public static String md5Hex(byte[] data) {
        return DigestUtils.md5Hex(data);
    }

    public static String md5Hex(String data) {
        return DigestUtils.md5Hex(data);
    }

    public static String md5Hex(String data,String salt) {
        return md5Hex(data +salt);
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        String hello  = "hello";
        System.out.println(new String(md5(hello.getBytes("UTF-8"))));

        System.out.println(new String(md5Hex(hello.getBytes("UTF-8"))));

        System.out.println(new String(md5Hex(hello,salt)));


    }
}
