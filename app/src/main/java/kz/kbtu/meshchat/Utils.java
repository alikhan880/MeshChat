package kz.kbtu.meshchat;

import java.security.NoSuchAlgorithmException;

/**
 * Created by aqali on 7/5/17 for MeshChat.
 */

public class Utils {
	private static final String SALT = "123456salt";
	
	private static String md5(String s) {
		try {
			java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
			byte[] array = md.digest(s.getBytes());
			StringBuilder sb = new StringBuilder();
			for (byte anArray : array)
				sb.append(Integer.toHexString((anArray & 0xFF) | 0x100).substring(1, 3));
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String hash(String s) {
		return md5(s + SALT);
	}
}
