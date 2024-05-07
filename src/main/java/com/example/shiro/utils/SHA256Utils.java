package com.example.shiro.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class SHA256Utils {
    public static String generateSalt() {
        byte[] saltBytes = new byte[16];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(saltBytes);
        return Base64.getEncoder().encodeToString(saltBytes);
    }

    public static String hashPassword(String password, String salt) {
        try {
            // 将密码和盐值拼接起来
            String saltedPassword = salt + password;

            // 创建 MessageDigest 实例并指定算法为 SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // 将明文密码和盐值转换为字节数组并进行加密计算
            byte[] hashBytes = digest.digest(saltedPassword.getBytes(StandardCharsets.UTF_8));

            // 将加密后的字节数组转换为十六进制字符串
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = String.format("%02x", b);
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void main(String[] args) {
        String password = "link2024";

        // 生成盐值
//        String salt = generateSalt();

        String salt = "hhQlWH4VM4xIE+uZAX3PLg==";

        // 对密码进行加盐和哈希加密
        String hashedPassword = hashPassword(password, salt);

        System.out.println("Password: " + password);
        System.out.println("Salt: " + salt);
        System.out.println("Hashed Password: " + hashedPassword);
    }
}