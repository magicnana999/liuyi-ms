package com.creolophus.lucky.common.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * 签名，无法解密
 *
 * @author magicnana
 * @date 2019/5/20 下午5:49
 */
public class MD5Util {

  private static final String salt = "3ce952e46063a08b";
  private final static String[] strDigits = {"0", "1", "2", "3", "4", "5",
      "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

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

  //////////////////////////////////////

  public static String md5Hex(String data, String salt) {
    return md5Hex(data + salt);
  }

  // return Hexadecimal
  private static String byteToArrayString(byte bByte) {
    int iRet = bByte;
    if (iRet < 0) {
      iRet += 256;
    }
    int iD1 = iRet / 16;
    int iD2 = iRet % 16;
    return strDigits[iD1] + strDigits[iD2];
  }

  // 转换字节数组为16进制字串
  private static String byteToString(byte[] bByte) {
    StringBuffer sBuffer = new StringBuffer();
    for (int i = 0; i < bByte.length; i++) {
      sBuffer.append(byteToArrayString(bByte[i]));
    }
    return sBuffer.toString().toUpperCase();
  }

  public static String GetMD5Code(String strObj) {
    String resultString = null;
    try {
      resultString = new String(strObj);
      MessageDigest md = MessageDigest.getInstance("MD5");
      // md.digest() 该函数返回值为存放哈希值结果的byte数组
      resultString = byteToString(md.digest(strObj.getBytes()));
    } catch (NoSuchAlgorithmException ex) {
      ex.printStackTrace();
    }
    return resultString;
  }


  public static void main(String[] args) throws UnsupportedEncodingException {
    String hello = "hello";
    System.out.println(new String(md5(hello.getBytes("UTF-8"))));

    System.out.println(new String(md5Hex(hello.getBytes("UTF-8"))));

    System.out.println(new String(md5Hex(hello, salt)));


  }
}
