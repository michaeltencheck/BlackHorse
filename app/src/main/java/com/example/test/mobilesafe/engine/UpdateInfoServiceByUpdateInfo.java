package com.example.test.mobilesafe.engine;

import android.content.Context;

import com.example.test.mobilesafe.domain.UpdateInfo;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by test on 8/26/2015.
 */
public class UpdateInfoServiceByUpdateInfo {
    private Context context;
    private UpdateInfo updateInfo;

    public UpdateInfoServiceByUpdateInfo(Context context) {
        this.context = context;
    }

    public UpdateInfo getUpdateInfo(int urlID) throws Exception{
        String path = context.getResources().getString(urlID);
        URL url = new URL(path);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setConnectTimeout(8888);
        InputStream inputStream = httpURLConnection.getInputStream();
        UpdateInfo updateInfo = UpdateInfoParser.parseUpdateInfo(inputStream);
        return updateInfo;
    }
}
