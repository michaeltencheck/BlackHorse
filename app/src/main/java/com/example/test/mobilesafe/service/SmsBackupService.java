package com.example.test.mobilesafe.service;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.os.Looper;
import android.util.Xml;
import android.widget.Toast;

import com.example.test.mobilesafe.domain.SmsInfo;
import com.example.test.mobilesafe.engine.SmsInfoService;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.List;

public class SmsBackupService extends Service {
    private SmsInfoService smsInfoService;
    public SmsBackupService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        smsInfoService = new SmsInfoService(this);
        new Thread(){
            @Override
            public void run() {
                String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/smsbackup.xml";
                File file = new File(path);
                List<SmsInfo> smsInfos = smsInfoService.getSmsInfos();
                XmlSerializer serializer = Xml.newSerializer();
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    serializer.setOutput(fileOutputStream, "utf-8");
                    serializer.startDocument("utf-8", true);
                    serializer.startTag(null, "count");
                    serializer.text(smsInfos.size() + "");
                    serializer.endTag(null, "count");
                    serializer.startTag(null, "smss");
                    for (SmsInfo smsInfo : smsInfos) {
                        serializer.startTag(null, "sms");

                        serializer.startTag(null, "id");
                        serializer.text(smsInfo.getId());
                        serializer.endTag(null, "id");

                        serializer.startTag(null, "address");
                        serializer.text(smsInfo.getAddress());
                        serializer.endTag(null, "address");

                        serializer.startTag(null, "date");
                        serializer.text(smsInfo.getDate());
                        serializer.endTag(null, "date");

                        serializer.startTag(null, "type");
                        serializer.text(smsInfo.getType()+"");
                        serializer.endTag(null, "type");

                        serializer.startTag(null, "body");
                        serializer.text(smsInfo.getBody());
                        serializer.endTag(null, "body");

                        serializer.endTag(null, "sms");
                    }


                    serializer.endTag(null, "smss");
                    serializer.endDocument();

                    fileOutputStream.flush();
                    fileOutputStream.close();
                    Looper.prepare();
                    Toast.makeText(getApplicationContext(), "备份完成",Toast.LENGTH_LONG).show();
                    Looper.loop();
                } catch (Exception e) {
                    e.printStackTrace();
                    Looper.prepare();
                    Toast.makeText(getApplicationContext(), "备份失败",Toast.LENGTH_LONG).show();
                    Looper.loop();
                }

            }
        }.start();
    }
}
