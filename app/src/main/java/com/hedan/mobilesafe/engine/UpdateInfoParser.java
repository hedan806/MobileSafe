package com.hedan.mobilesafe.engine;

import android.util.Log;
import android.util.Xml;

import com.hedan.mobilesafe.domain.UpdateInfo;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.io.StringReader;

/**
 * Created by Administrator on 2015/10/19.
 */
public class UpdateInfoParser {

    private static final String TAG = "UpdateInfoParser";

    public static UpdateInfo getUpdateInfo(String response) throws Exception{
        UpdateInfo info = new UpdateInfo();
        XmlPullParser parser = Xml.newPullParser();
        parser.setInput(new StringReader(response));
        int type = parser.getEventType();
        while (type != XmlPullParser.END_DOCUMENT){
            switch (type){
                case XmlPullParser.START_TAG:
                    String name = parser.getName();
                    Log.i(TAG,"name : " + name);
                    if("version".equals(parser.getName())){
                        String version = parser.nextText();
                        info.setVersion(version);
                    }else if("desc".equals(parser.getName())){
                        String desc = parser.nextText();
                        Log.i(TAG, "desc" + desc);
                        info.setDesc(desc);
                    }else if("apkurl".equals(parser.getName())){
                        String apkurl = parser.nextText();
                        Log.i(TAG,"apkUrl" + apkurl);
                        info.setApkurl(apkurl);
                    }
                    break;
            }
            type = parser.next();
        }
        return info;
    }
}
