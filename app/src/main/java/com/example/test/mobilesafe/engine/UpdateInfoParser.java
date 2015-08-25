package com.example.test.mobilesafe.engine;

import android.util.Xml;

import com.example.test.mobilesafe.domain.UpdateInfo;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;

/**
 * Created by test on 8/24/2015.
 */
public class UpdateInfoParser {

    public static UpdateInfo parseUpdateInfo(InputStream inputStream) throws Exception{
        UpdateInfo updateInfo = new UpdateInfo();
        XmlPullParser xmlPullParser = Xml.newPullParser();
        xmlPullParser.setInput(inputStream, "utf-8");
        int eventType = xmlPullParser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    if ("version".equals(xmlPullParser.getName())) {
                        String version = xmlPullParser.nextText();
                        updateInfo.setVersion(version);
                    }else if ("description".equals(xmlPullParser.getName())) {
                        String description = xmlPullParser.nextText();
                        updateInfo.setDesciption(description);
                    }else if ("apkurl".equals(xmlPullParser.getName())) {
                        String apkurl = xmlPullParser.nextText();
                        updateInfo.setApkurl(apkurl);
                    }
                    break;
                default:
                    break;
            }
            eventType = xmlPullParser.next();
        }
        return updateInfo;
    }
}
