package com.eshoppers.security;

import com.eshoppers.annotation.Sha256;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Sha256
public class Sha256PasswordEncryption implements PasswordEncryption {
    @Override
    public String encrypt(String password) {
        try {
            var digest = MessageDigest.getInstance("SHA-256");
            var bytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));

            return bytesToHex(bytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Unable to encrypt password", e);
        }
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
