package com.example;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Test {
    public static String main(String[] arg0) throws NoSuchAlgorithmException {
        String pwd = "123456";
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        byte[] bytes = messageDigest.digest(pwd.getBytes());
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i <= bytes.length; i++) {
            String pwdMD5 = Integer.toHexString(0xff&bytes[i]);
            if (pwdMD5.length() == 1) {
                stringBuilder.append("0" + pwdMD5);
                System.out.println(stringBuilder.toString());
            } else {
                stringBuilder.append(pwdMD5);
                System.out.println(stringBuilder.toString());
            }

        }
        return stringBuilder.toString();
    }
}
