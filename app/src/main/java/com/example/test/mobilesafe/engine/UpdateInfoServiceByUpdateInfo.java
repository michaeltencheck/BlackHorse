package com.example.test.mobilesafe.engine;

import android.content.Context;
import android.util.Log;

import com.example.test.mobilesafe.domain.UpdateInfo;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by test on 8/26/2015.
 */
public class UpdateInfoServiceByUpdateInfo {
    private Context context;
//    private UpdateInfo updateInfo;
//    private int urlID;

    public UpdateInfoServiceByUpdateInfo(Context context) {
        this.context = context;
//        this.urlID = urlID;
    }

    public UpdateInfo getUpdateInfo(int urlID) throws Exception{
        String path = context.getResources().getString(urlID);
        URL url = new URL(path);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setConnectTimeout(8888);
        InputStream inputStream = httpURLConnection.getInputStream();
        return UpdateInfoParser.parseUpdateInfo(inputStream);
    }

    /*@Override
    public void run() {
        String path = context.getResources().getString(urlID);
        try {
            URL url = new URL(path);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(8888);
            InputStream inputStream = httpURLConnection.getInputStream();
            updateInfo = UpdateInfoParser.parseUpdateInfo(inputStream);
            Log.i("cccc", updateInfo.getVersion());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public UpdateInfo getUpdateInfo() {
        return updateInfo;
    }*/
}
