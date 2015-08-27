package com.example.test.mobilesafe.engine;

import android.app.ProgressDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by test on 8/26/2015.
 */
public class DownloadFileTask {
    public static File getFile(String downloadPath, String filePath, ProgressDialog progressDialog)
            throws Exception {
        URL url = new URL(downloadPath);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setConnectTimeout(8888);
        httpURLConnection.setReadTimeout(8888);
        if (httpURLConnection.getResponseCode() == 200) {
            int total = httpURLConnection.getContentLength();
            progressDialog.setMax(total);
            InputStream inputStream = httpURLConnection.getInputStream();
            File file = new File(filePath);
            FileOutputStream fos = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int len = 0;
            int progress = 0;
            while ((len = inputStream.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
                progress += len;
                progressDialog.setProgress(progress);
            }
            fos.flush();
            fos.close();
            inputStream.close();
            return file;
        }
        return null;
    }
}
