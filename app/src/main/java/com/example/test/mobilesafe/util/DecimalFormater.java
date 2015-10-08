package com.example.test.mobilesafe.util;

import java.text.DecimalFormat;

/**
 * Created by test on 10/7/2015.
 */
public class DecimalFormater {
    public static String getNumber(long size) {
        DecimalFormat decimalFormat = new DecimalFormat("####.00");
        if (size < 1024) {
            return size + "Bytes";
        }else if (size < 1024l * 1024l) {
            float kNumber = size / 1024f;
            return decimalFormat.format(kNumber) + "KB";
        } else if (size < 1024l * 1024l * 1024l) {
            float mNumber = size / 1024f / 1024f;
            return decimalFormat.format(mNumber) + "MB";
        } else if (size < 1024l * 1024l * 1024l * 1024l) {
            float gNumber = size / 1024f / 1024f / 1024f;
            return decimalFormat.format(gNumber) + "GB";
        } else {
            return "Obtain memory error";
        }
//        return decimalFormat.format(size / 1024) + "aaa";
//        return decimalFormat.format(size / 1024f / 1024f / 1024f) + "GB";
    }

    public static String getKBNumber(long size) {
        DecimalFormat decimalFormat = new DecimalFormat("####.00");
        float kNumber = size / 1024f;
        return decimalFormat.format(kNumber) + "MB";
    }
}
