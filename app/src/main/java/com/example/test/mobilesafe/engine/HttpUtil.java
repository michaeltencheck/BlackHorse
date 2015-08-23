package com.example.test.mobilesafe.engine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by test on 8/23/2015.
 */
public class HttpUtil {
    private static InputStream connectWebsite(URL url) {
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(8888);
            httpURLConnection.setReadTimeout(8888);
            InputStream inputStream = httpURLConnection.getInputStream();
            return inputStream;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
