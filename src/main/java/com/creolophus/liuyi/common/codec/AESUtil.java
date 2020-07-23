package com.creolophus.liuyi.common.codec;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * 对称加密 AES
 * @author magicnana
 * @date 2019/5/20 下午6:28
 */
public class AESUtil {

    private static final boolean base64Key = false;

    public static final String key = "e1e1cadc1c92e72f1d2d1df04015b113";


    private static final char[] HEX_CHAR = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    private static SecretKey initKey(String key) {
        if(base64Key) {
            key = new String(Base64.decodeBase64(key));
        }
        byte[] keyStr = toBytes(key);
        return new SecretKeySpec(keyStr, "AES");
    }

    private static SecretKey getKey(String key) {
        SecretKey secretKey = initKey(key);
        return secretKey;
    }

    /**
     * 加密
     *
     * @return
     * @throws Exception
     */
    public static String encrypt(String message, String key) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, getKey(key));
            return toHexString(cipher.doFinal(message.getBytes()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String toHexString(byte[] b) {
        StringBuilder sb = new StringBuilder(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            sb.append(HEX_CHAR[(b[i] & 0xf0) >>> 4]);
            sb.append(HEX_CHAR[b[i] & 0x0f]);
        }
        return sb.toString();
    }

    /**
     * 解密
     *
     * @param msg
     * @return
     * @throws Exception
     */
    public static String decrypt(String msg, String key) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, getKey(key));
            byte[] decByte = cipher.doFinal(toBytes(msg));
            return new String(decByte, "utf-8");
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private static final byte[] toBytes(String s) {
        byte[] bytes;
        bytes = new byte[s.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(s.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }


    public static void main(String[] args) {

        String hello = "你好世界";
        String encode = AESUtil.encrypt(hello, key);
        String decode = AESUtil.decrypt(encode, key);

        System.out.println("key=" + key);
        System.out.println("encode=" + encode);
        System.out.println("decode=" + decode);

    }


}
