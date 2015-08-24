package com.example.test.mobilesafe.engine;

import android.content.Context;

import com.example.test.mobilesafe.domain.UpdateInfo;

import java.io.InputStream;
import java.net.HttpURLConnection;
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

    public UpdateInfo getUpdateInfo(int urlId) throws Exception{
        String path = context.getResources().getString(urlId);
        URL url = new URL(path);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setConnectTimeout(8888);
        httpURLConnection.setRequestMethod("GET");
        InputStream inputStream = httpURLConnection.getInputStream();
        updateInfo = UpdateInfoParser.parseUpdateInfo(inputStream);
        return updateInfo;
    }


}
