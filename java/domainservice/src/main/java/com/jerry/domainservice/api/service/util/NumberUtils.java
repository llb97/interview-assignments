package com.jerry.domainservice.api.service.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import lombok.Getter;

/**
 * Provides the utility function for number.
 * @author jerry
 *
 */
public final class NumberUtils {
	
	private NumberUtils() {}
	
	private static char[] array = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
	
	@Getter
	private static SecureRandom random = null;
	
	static {
		try {
			random = SecureRandom.getInstanceStrong(); // 获取高强度安全随机数生成器
        } catch (NoSuchAlgorithmException e) {
        	random = new SecureRandom(); // 获取普通的安全随机数生成器
        }	
	}
	
	/**
	 * 字符长度
	 */
	public static int RADIX = array.length;	
	
	/**
	 * 短域名的长度
	 */
	public static final int SHORT_DOMAIN_LENGTH = 8;
	
	static class InvalidRadixException extends RuntimeException{

		/**
		 * 
		 */
		private static final long serialVersionUID = -3450431798764995166L;
		
	}

	
	/**
	 * Returns a string representation of the first argument in the
     * radix specified by the second argument.
	 * @param size
	 * @param radix
	 * @return
	 */
	public static String convert(int size) {
		StringBuilder result = new StringBuilder();
		while (size > 0) {
			result.append(array[random.nextInt(RADIX)]);
			size--;
		}
		
		return result.toString();
	}
	
	
}
