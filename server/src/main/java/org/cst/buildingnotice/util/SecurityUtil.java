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

public class SecurityUtil {

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

	public static List<String> token(Integer id, String salt) {

		long timestamp = System.currentTimeMillis() + 3 * 24 * 60 * 60 * 1000;
		String token = id + ";" + sha(salt + timestamp + saltGenerate());
		String tokenInData = sha(token + timestamp) + ";" + timestamp;
		
		return new ArrayList<String>() {
			private static final long serialVersionUID = 1L;
			{
				add(token);
				add(tokenInData);
			}
		};
	}
	
	public static boolean verifyToken(String token, String tokenInData) {
		
		int sepIndex = tokenInData.lastIndexOf(";");
		long timestamp = Long.parseLong(tokenInData.substring(sepIndex + 1, tokenInData.length()));
		if (timestamp < System.currentTimeMillis()) return false;
		
		String tokenInput = sha(token + timestamp) + ";" + timestamp;
		return slowEquals(StringUtil.string2Byte(tokenInput), StringUtil.string2Byte(tokenInData));
	}
	
	public static Integer getIdInToken(String token) {
		
		Integer id = -1;
		try {
			int sepIndex = token.indexOf(";");
			id = Integer.parseInt(token.substring(0, sepIndex));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}
}