package com.example.test.mobilesafe.engine;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
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
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
