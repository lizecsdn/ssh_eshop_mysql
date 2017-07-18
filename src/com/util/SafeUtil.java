package com.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.springframework.util.Base64Utils;

/**
 * 安全工具类
 */
public class SafeUtil {

	/**
	 * md5加密字符串
	 * 
	 * @param str
	 * @return
	 */
	public final static String md5(String str) {
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		messageDigest.update(str.getBytes());
		// return Base64Utils.encodeToString(messageDigest.digest());
		// return messageDigest.toString();
		// return messageDigest.digest().toString();
		// 转换并返回结果，也是字节数组，包含16个元素

		byte[] resultByteArray = messageDigest.digest();

		// 字符数组转换成字符串返回

		return byteArrayToHex(resultByteArray);

	}

	public static String byteArrayToHex(byte[] byteArray) {
		char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F' };

		// new一个字符数组，这个就是用来组成结果字符串的（解释一下：一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方））

		char[] resultCharArray = new char[byteArray.length * 2];

		// 遍历字节数组，通过位运算（位运算效率高），转换成字符放到字符数组中去

		int index = 0;

		for (byte b : byteArray) {

			resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];

			resultCharArray[index++] = hexDigits[b & 0xf];

		}

		// 字符数组组合成字符串返回

		return new String(resultCharArray);
	}

	/**
	 * sha1加密字符串
	 * 
	 * @param str
	 * @return
	 */
	/*
	 * public final static String sha1(String str){ MessageDigest messageDigest
	 * = null; try { messageDigest = MessageDigest.getInstance("SHA-1"); } catch
	 * (NoSuchAlgorithmException e) { e.printStackTrace(); }
	 * messageDigest.update(str.getBytes()); return
	 * Base64Utils.encodeToString(messageDigest.digest()); }
	 */

	/**
	 * 使用特定加密范式加密
	 * 
	 * @param str
	 * @return
	 */
	public final static String encode(String str) {
		// return md5(sha1(md5(str)));
		return md5(str);
	}

}
