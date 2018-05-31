package com.ubc.pojo;


import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
//import javax.persistence.EntityManager;
//import javax.persistence.Query;

import org.apache.commons.codec.binary.Base64;




public class Util {
	public static boolean ENC_ENABLED = true;
	
	/**
	 * Encryption Decryption Password
	 */
	static final String ENC_PASSWORD = "Infimonk@123";

	
	
	public static String encrypt(String input) {
		byte[] crypted = null;
		String key = ENC_PASSWORD;
		try {
			SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, skey);
			crypted = cipher.doFinal(input.getBytes());
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return new String(Base64.encodeBase64(crypted));
	}

	
	public static String decrypt(String input) {
		if(!ENC_ENABLED) {
			return input;
		}
		byte[] output = null;
		String key = ENC_PASSWORD;
		try {
			SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, skey);
			output = cipher.doFinal(Base64.decodeBase64(input));
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return new String(output);
	}

	
}
