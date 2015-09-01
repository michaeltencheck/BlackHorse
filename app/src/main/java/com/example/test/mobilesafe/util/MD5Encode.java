package com.example.test.mobilesafe.util;

import java.security.MessageDigest;

/**
 * Created by test on 9/2/2015.
 */
public class MD5Encode {
    public static String MD5Encoding(String s) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] bytes = messageDigest.digest(s.getBytes());
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                String pwdMD5 = Integer.toHexString(0xff&bytes[i]);
                if (pwdMD5.length() == 1) {
                    stringBuilder.append("0" + pwdMD5);
                } else {
                    stringBuilder.append(pwdMD5);
                }

            }
            return stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
