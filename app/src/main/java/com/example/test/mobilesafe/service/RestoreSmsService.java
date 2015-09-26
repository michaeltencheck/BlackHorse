package com.example.test.mobilesafe.service;

import android.app.ProgressDialog;
import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.os.Looper;
import android.widget.Toast;

import com.example.test.mobilesafe.engine.RestoreSms;

public class RestoreSmsService extends Service {
    public RestoreSmsService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(){
            @Override
            public void run() {
                RestoreSms restoreSms = new RestoreSms(getApplicationContext());
                String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/smsbackup.xml";
                try {
                    restoreSms.getRestore(path, new ProgressDialog(getApplicationContext()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Looper.prepare();
                Toast.makeText(getApplicationContext(),"还原完成",Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }.start();
    }
}
