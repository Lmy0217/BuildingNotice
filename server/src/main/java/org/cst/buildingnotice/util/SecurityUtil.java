package org.cst.buildingnotice.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.cst.buildingnotice.config.Config;

public class SecurityUtil {
	
	public static String[] chars = new String[] {
		"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", 
		"k", "l", "m", "n", "o", "p", "q", "r", "s", "t", 
		"u", "v", "w", "x", "y", "z", 
		"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", 
		"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", 
		"K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
		"U", "V", "W", "X", "Y", "Z" 
	};
	
	public static String getShortUUID() {
		
		StringBuffer stringBuffer = new StringBuffer();
		String uuid = UUID.randomUUID().toString().replace("-", "");
		
		for (int i = 0; i < 8; i++) {
			String str = uuid.substring(i * 4, i * 4 + 4);
			int strInteger = Integer.parseInt(str, 16);
			stringBuffer.append(chars[strInteger % 0x3E]);
		}
		
		return stringBuffer.toString();
	}

	public static String saltGenerate() {

		Random ranGen = new SecureRandom();
		byte[] aesKey = new byte[16];
		ranGen.nextBytes(aesKey);

		return StringUtil.byte2String(aesKey);
	}

	public static String sha(String text) {

		if (text == null || text.length() == 0)
			return null;

		byte[] byteArray = null;
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("sha-256");
			messageDigest.update(text.getBytes());
			byteArray = messageDigest.digest();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}

		return StringUtil.byte2String(byteArray);
	}

	public static boolean slowEquals(byte[] a, byte[] b) {

		if (a == null || b == null)
			return false;

		int diff = a.length ^ b.length;
		for (int i = 0; i < a.length && i < b.length; i++)
			diff |= a[i] ^ b[i];

		return diff == 0;
	}

	public static String hash(File file) {

		if (file == null)
			return null;

		byte[] byteArray = null;
		try {
			InputStream fis = new FileInputStream(file);
			byte[] buffer = new byte[1024];
			MessageDigest complete = MessageDigest.getInstance("md5");
			for (int numRead = 0; (numRead = fis.read(buffer)) > 0;)
				complete.update(buffer, 0, numRead);
			fis.close();
			byteArray = complete.digest();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return StringUtil.byte2String(byteArray);
	}

	public static long random() {

		Random random = new Random();
		long result = random.nextInt(6);
		return result + 5;
	}

	public static String encrypt(String password, String salt) {

		return SecurityUtil.sha(SecurityUtil.sha(password) + salt);
	}

	public static boolean verify(String pwd, String hexSalt, String hexPwd) {

		return SecurityUtil.slowEquals(
				StringUtil.string2Byte(SecurityUtil.encrypt(pwd, StringUtil.hex2String(hexSalt))),
				StringUtil.hex2Byte(hexPwd));
	}
	
	public static List<String> pki(Integer id, String info, long time) {

		long timestamp = System.currentTimeMillis() + time;
		String key = id + ";" + sha(info + timestamp + saltGenerate());
		String keyInData = sha(key + timestamp) + ";" + timestamp;
		
		return new ArrayList<String>() {
			private static final long serialVersionUID = 1L;
			{
				add(key);
				add(keyInData);
			}
		};
	}
	
	public static boolean verifyPKI(String key, String keyInData) {
		
		int sepIndex = keyInData.lastIndexOf(";");
		long timestamp = Long.parseLong(keyInData.substring(sepIndex + 1));
		if (timestamp < System.currentTimeMillis()) return false;
		
		String tokenInput = sha(key + timestamp) + ";" + timestamp;
		return slowEquals(StringUtil.string2Byte(tokenInput), StringUtil.string2Byte(keyInData));
	}
	
	public static Integer getIdInKey(String key) {
		
		Integer id = -1;
		try {
			int sepIndex = key.indexOf(";");
			id = Integer.parseInt(key.substring(0, sepIndex));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}

	public static List<String> token(Integer id, String salt) {

		return pki(id, salt, Config.GAP_TOKEN_FALSE);
	}
	
	public static boolean verifyToken(String token, String tokenInData) {
		
		return verifyPKI(token, tokenInData);
	}
	
	public static Integer getIdInToken(String token) {
		
		return getIdInKey(token);
	}
	
	public static List<String> codeEmail(Integer id, String email) {

		return pki(id, email, Config.GAP_EMAIL_VERIFY_FALSE);
	}
	
	public static boolean verifyCodeEmail(String code, String codeInData) {
		
		return verifyPKI(code, codeInData);
	}
	
	public static Integer getIdInCodeEmail(String code) {
		
		return getIdInKey(code);
	}
	
	public static List<String> codeReset(Integer id, String pwd) {

		return pki(id, pwd, Config.GAP_RESET_VERIFY_FALSE);
	}
	
	public static boolean verifyCodeReset(String code, String codeInData) {
		
		return verifyPKI(code, codeInData);
	}
	
	public static Integer getIdInCodeReset(String code) {
		
		return getIdInKey(code);
	}
}
