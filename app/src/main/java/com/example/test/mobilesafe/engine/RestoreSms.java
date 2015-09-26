package com.example.test.mobilesafe.engine;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by test on 9/26/2015.
 */
public class RestoreSms {
    private Context context;

    public RestoreSms(Context context) {
        this.context = context;
    }

    public void getRestore() {
        ContentValues values = null;
        ProgressDialog pd = new ProgressDialog(context);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/smsbackup.xml";
        try {
            FileInputStream fileInputStream = new FileInputStream(path);
            XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
            xmlPullParserFactory.setNamespaceAware(true);
            XmlPullParser xmlPullParser = xmlPullParserFactory.newPullParser();
            xmlPullParser.setInput(fileInputStream,"utf-8");
            ContentResolver resolver = context.getContentResolver();
            while (xmlPullParser.getEventType() != XmlPullParser.END_DOCUMENT) {
                if ("sms".equals(xmlPullParser.getName())) {
                    if ("address".equals(xmlPullParser.getName())) {
                        String address = xmlPullParser.nextText();
                        values.put("address", address);
                    }else if ("date".equals(xmlPullParser.getName())) {
                        String address = xmlPullParser.nextText();
                        values.put("date", address);
                    }else if ("type".equals(xmlPullParser.getName())) {
                        String address = xmlPullParser.nextText();
                        values.put("type", address);
                    }else if ("body".equals(xmlPullParser.getName())) {
                        String address = xmlPullParser.nextText();
                        values.put("body", address);
                    }
                }
                resolver.insert(Uri.parse("content://sms/"), values);
                values = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
