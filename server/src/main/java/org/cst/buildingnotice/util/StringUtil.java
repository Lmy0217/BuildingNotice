package org.cst.buildingnotice.util;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;

public class StringUtil {
	
	private static final String HEX = "0123456789abcdef";

	public static String byte2Hex(byte[] byteArray) {
		
		if(byteArray == null || byteArray.length == 0) return null;
		
		StringBuilder sb = new StringBuilder(byteArray.length * 2);
		
		for(byte b : byteArray) {
			sb.append(HEX.charAt((b >> 4) & 0x0f));
			sb.append(HEX.charAt(b & 0x0f));
		}
		
		return sb.toString();
	}
	
	public static byte[] hex2Byte(String hexString) {
		
		if(hexString == null || hexString.length() == 0 
				|| hexString.length() % 2 == 1) return null;
		
		byte[] result = new byte[hexString.length() / 2];
		
		try {
			int b;
			for(int i = 0; i < result.length; i++) {
				b = HEX.indexOf(hexString.charAt(2 * i)) * 16;
				b += HEX.indexOf(hexString.charAt(2 * i + 1));
				result[i] = (byte) (b & 0xff);
			}
		} catch (IndexOutOfBoundsException e) {
			result = null;
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static String byte2String(byte[] byteArray) {
		
		if(byteArray == null || byteArray.length == 0) return null;
		
		String result = null;
		try {
			result = new String(byteArray, "ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static String hex2String(String hexString) {
		
		return byte2String(hex2Byte(hexString));
	}
	
	public static String string2Hex(String string) {
		
		if(string == null || string.length() == 0) return null;
		
		String result = null;
		try {
			result = StringUtil.byte2Hex(string.getBytes("ISO-8859-1"));
		} catch (UnsupportedEncodingException e) {
			result = null;
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static byte[] string2Byte(String string) {
		
		return hex2Byte(string2Hex(string));
	}
	
	public static String long2FormatString(long number) {
		
		if(number < 0) return null;
		
		DecimalFormat df = new DecimalFormat("000000000");
		return df.format(number);
	}
}
