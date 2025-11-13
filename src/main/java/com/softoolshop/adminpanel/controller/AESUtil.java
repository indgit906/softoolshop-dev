package com.softoolshop.adminpanel.controller;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESUtil {
	private static final String ALGO = "AES/GCM/NoPadding";
    private static final int GCM_TAG_LENGTH = 128;

    public static String decrypt(String ciphertext, String ivBase64, String key) throws Exception {
        byte[] decodedKey = key.getBytes("UTF-8");
        SecretKeySpec secretKey = new SecretKeySpec(decodedKey, 0, 32, "AES");

        Cipher cipher = Cipher.getInstance(ALGO);
        byte[] iv = Base64.getDecoder().decode(ivBase64);
        GCMParameterSpec spec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);

        cipher.init(Cipher.DECRYPT_MODE, secretKey, spec);
        byte[] decodedCipher = Base64.getDecoder().decode(ciphertext);

        return new String(cipher.doFinal(decodedCipher), "UTF-8");
    }
}
