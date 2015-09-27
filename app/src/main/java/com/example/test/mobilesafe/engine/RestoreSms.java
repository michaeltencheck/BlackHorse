package com.example.test.mobilesafe.engine;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
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


    public void getRestore(String path, ProgressDialog pd) throws Exception{
        File file = new File(path);
//        ContentResolver resolver = context.getContentResolver();
        ContentValues values = null;
//        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
            xmlPullParserFactory.setNamespaceAware(true);
            XmlPullParser xmlPullParser = xmlPullParserFactory.newPullParser();
            xmlPullParser.setInput(fileInputStream, "utf-8");
            int type = xmlPullParser.getEventType();
        int currentCount = 0;
            while (type != XmlPullParser.END_DOCUMENT) {
                switch (type) {
                    case XmlPullParser.START_TAG:
                        if ("count".equals(xmlPullParser.getName())) {
                            pd.setMax(Integer.parseInt(xmlPullParser.nextText()));
                        }
                        else if ("sms".equals(xmlPullParser.getName())) {
                            values = new ContentValues();
                        }else if ("address".equals(xmlPullParser.getName())) {
                            String address = xmlPullParser.nextText();
                            Log.i("aaaa", address);
                            values.put("address", address);
                        } else if ("date".equals(xmlPullParser.getName())) {
                            String date = xmlPullParser.nextText();
                            values.put("date", date);
                        } else if ("type".equals(xmlPullParser.getName())) {
                            String type1 = xmlPullParser.nextText();
                            values.put("type", type1);
                        } else if ("body".equals(xmlPullParser.getName())) {
                            String body = xmlPullParser.nextText();
                            values.put("body", body);
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if ("sms".equals(xmlPullParser.getName())) {
                            ContentResolver resolver = context.getContentResolver();
                            resolver.insert(Uri.parse("content://sms/"), values);
                            values = null;
                            Log.i("aaaa", "bbbb");
                            currentCount++;
                            Thread.sleep(300);
                            pd.setProgress(currentCount);
                        }
                        break;
                    default:
                        break;
                }
                type = xmlPullParser.next();
            }
        /*} catch (Exception e) {
            e.printStackTrace();
        }*/
        /*ContentValues values = null;
        *//*ProgressDialog pd = new ProgressDialog(context);
        pd.setCancelable(false);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);*//*
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/smsbackup.xml";
        try {
            FileInputStream fileInputStream = new FileInputStream(path);
            XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
            xmlPullParserFactory.setNamespaceAware(true);
            XmlPullParser xmlPullParser = xmlPullParserFactory.newPullParser();
            xmlPullParser.setInput(fileInputStream,"utf-8");
            ContentResolver resolver = context.getContentResolver();
            int currentCount = 0;
            while (xmlPullParser.getEventType() != XmlPullParser.END_DOCUMENT) {
                if ("count".equals(xmlPullParser.getName())) {
                    String count = xmlPullParser.nextText();
//                    pd.setMax(Integer.parseInt(count));
                } else if ("sms".equals(xmlPullParser.getName())) {
                    if ("address".equals(xmlPullParser.getName())) {
                        String address = xmlPullParser.nextText();
                        values.put("address", address);
                    } else if ("date".equals(xmlPullParser.getName())) {
                        String date = xmlPullParser.nextText();
                        values.put("date", date);
                    } else if ("type".equals(xmlPullParser.getName())) {
                        String type = xmlPullParser.nextText();
                        values.put("type", type);
                    } else if ("body".equals(xmlPullParser.getName())) {
                        String body = xmlPullParser.nextText();
                        values.put("body", body);
                    }
                }
                resolver.insert(Uri.parse("content://sms/"), values);
                values = null;
                currentCount++;
//                pd.setProgress(currentCount);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/

    }
}
