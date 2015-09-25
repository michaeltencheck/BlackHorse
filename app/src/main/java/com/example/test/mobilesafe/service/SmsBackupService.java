package com.example.test.mobilesafe.service;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.util.Xml;

import com.example.test.mobilesafe.engine.SmsInfoService;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Serializable;

public class SmsBackupService extends Service {
    private SmsInfoService smsInfoService;
    public SmsBackupService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        smsInfoService = new SmsInfoService(this);
        new Thread(){
            @Override
            public void run() {
                String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/smsbackup.xml";
                File file = new File(path);
                XmlSerializer serializer = Xml.newSerializer();
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    serializer.setOutput(fileOutputStream,"utf-8");
                    serializer.startDocument("utf-8", true);




                    serializer.endDocument();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }.start();


    }
}
