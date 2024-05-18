package com.AbrishPhoenix.TheChat;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.Arrays;

public class SecHelper {

    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; // You can extend this for more characters
    private static final String KEY = "XPMGTDHLYONZBWEARKJUFSCIQV";
    private static final String SECRET_KEY = Helper.generateKey(); // 16-character key

    public static String encrypt(String plainText) {
        try {
            String plainKey = Helper.generateKey();
            String encrypted = encryptedStringKey(plainText, plainKey);
            encrypted += plainKey;
            
            return substitute(encrypted);
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    public static String decrypt(String _encryptedText) {
        try {
            String encryptedText = reverseSubstitute(_encryptedText);
            String extractedKey = encryptedText.substring(encryptedText.length() - SECRET_KEY.length());
            encryptedText = encryptedText.substring(0, encryptedText.length() - SECRET_KEY.length());
            
            return decryptedStringKey(encryptedText, extractedKey);
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    private static String substitute(String text) {
        StringBuilder cipherText = new StringBuilder();
        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                int index = ALPHABET.indexOf(c);
                cipherText.append(index != -1 ? KEY.charAt(index) : c);
            } else {
                cipherText.append(c); // Leave non-alphabetic characters unchanged
            }
        }
        return cipherText.toString();
    }

    private static String reverseSubstitute(String cipherText) {
        StringBuilder originalText = new StringBuilder();
        for (char c : cipherText.toCharArray()) {
            if (Character.isLetter(c)) {
                int index = KEY.indexOf(c);
                originalText.append(index != -1 ? ALPHABET.charAt(index) : c);
            } else {
                originalText.append(c); // Leave non-alphabetic characters unchanged
            }
        }
        return originalText.toString();
    }

    private static SecretKey generateKey(String key) throws Exception {
        byte[] keyValue = key.getBytes();
        MessageDigest sha = MessageDigest.getInstance("SHA-1");
        keyValue = sha.digest(keyValue);
        keyValue = Arrays.copyOf(keyValue, 16); // Use only first 128 bits for AES
        SecretKey secretKey = new SecretKeySpec(keyValue, "AES");
        return secretKey;
    }
    
    public static String decryptedStringKey(final String _string, final String _key) {
		try {
			
			javax.crypto.spec.SecretKeySpec key = (javax.crypto.spec.SecretKeySpec) generateKey(_key);
			
			javax.crypto.Cipher c = javax.crypto.Cipher.getInstance("AES");
			
			c.init(javax.crypto.Cipher.DECRYPT_MODE,key);
			
			byte[] decode = android.util.Base64.decode(_string,android.util.Base64.DEFAULT);
			
			byte[] decval = c.doFinal(decode);
			
            return new String(decval);
		} catch (Exception ex) {}
        return "";    
	}
	
	public static String encryptedStringKey(final String _string, final String _key) {
		try {
			javax.crypto.SecretKey key = generateKey(_key);
			
			javax.crypto.Cipher c = javax.crypto.Cipher.getInstance("AES");
			
			c.init(javax.crypto.Cipher.ENCRYPT_MODE,key);
			
			byte[] encVal = c.doFinal(_string.getBytes());
			
			String encrypted = android.util.Base64.encodeToString(encVal,android.util.Base64.DEFAULT);
            return encrypted;
		} catch (Exception ex) {}
        return "";    
	}
}