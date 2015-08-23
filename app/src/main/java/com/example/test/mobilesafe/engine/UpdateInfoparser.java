package com.example.test.mobilesafe.engine;

import com.example.test.mobilesafe.domain.UpdateInfo;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;

/**
 * Created by test on 8/23/2015.
 */
public class UpdateInfoparser {
    private UpdateInfo updateInfo;
    public UpdateInfo parseUpdateInfo(InputStream inputStream) {
        try {
            XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
            xmlPullParserFactory.setNamespaceAware(true);
            XmlPullParser xmlPullParser = xmlPullParserFactory.newPullParser();
            xmlPullParser.setInput(inputStream, "utf-8");
            int eventType = xmlPullParser.getEventType();
            while (eventType != xmlPullParser.END_TAG) {
                if ("version".equals(eventType)) {
                    updateInfo.setVersion(xmlPullParser.getText());
                }else if ("description".equals(eventType)) {
                    updateInfo.setDesciption(xmlPullParser.getText());
                }else if ("apkurl".equals(eventType)) {
                    updateInfo.setApkurl(xmlPullParser.getText());
                }
            }
            return updateInfo;
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            return null;
        }

    }
}
