package com.example.test.mobilesafe.engine;

import android.content.Context;

import com.example.test.mobilesafe.domain.UpdateInfo;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by test on 8/24/2015.
 */
public class UpdateInfoService {
    private Context context;
    private UpdateInfo updateInfo;

    public UpdateInfoService(Context context) {
        this.context = context;
    }

    public UpdateInfo getUpdateInfo(final int urlId) throws Exception{
        new Thread(new Runnable() {
            @Override
            public void run() {
                String path = context.getResources().getString(urlId);
                try {
                    URL url = new URL(path);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setConnectTimeout(8888);
                    InputStream inputStream = httpURLConnection.getInputStream();
                    updateInfo = UpdateInfoParser.parseUpdateInfo(inputStream);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        return updateInfo;
        /*String path = context.getResources().getString(urlId);
        URL url = new URL(path);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setConnectTimeout(8888);
        httpURLConnection.setRequestMethod("GET");
        InputStream inputStream = httpURLConnection.getInputStream();
        updateInfo = UpdateInfoParser.parseUpdateInfo(inputStream);
        return updateInfo;*/
    }


}
