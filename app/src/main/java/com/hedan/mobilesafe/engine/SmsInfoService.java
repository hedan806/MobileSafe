package com.hedan.mobilesafe.engine;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Telephony;
import android.util.Xml;

import com.hedan.mobilesafe.domain.SmsInfo;
import com.hedan.mobilesafe.util.LogUtil;

import org.xmlpull.v1.XmlPullParser;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/18.
 */
public class SmsInfoService {
    private static final String TAG = SmsInfoService.class.getSimpleName();
    private Context context;

    public SmsInfoService(Context context) {
        this.context = context;
    }

    public List<SmsInfo> getSmsInfos() {
        List<SmsInfo> list = new ArrayList<SmsInfo>();
        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = resolver.query(Telephony.Sms.CONTENT_URI, new String[]{Telephony.Sms._ID, Telephony.Sms.ADDRESS, Telephony.Sms.DATE, Telephony.Sms.BODY, Telephony.Sms.TYPE, Telephony.Sms.READ}, null, null, null);
        SmsInfo info;
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(Telephony.Sms._ID));
            String address = cursor.getString(cursor.getColumnIndex(Telephony.Sms.ADDRESS));
            String date = cursor.getString(cursor.getColumnIndex(Telephony.Sms.DATE));
            String body = cursor.getString(cursor.getColumnIndex(Telephony.Sms.BODY));
            int type = cursor.getInt(cursor.getColumnIndex(Telephony.Sms.TYPE));
            int read = cursor.getInt(cursor.getColumnIndex(Telephony.Sms.READ));

            info = new SmsInfo(id, address, body, date, type, read);
            list.add(info);
            info = null;
        }
        return list;
    }

    public void restoreSms(String path, ProgressDialog pd) throws Exception {
        File file = new File(path);
        FileInputStream is = new FileInputStream(file);
        ContentValues values = null;
        XmlPullParser parser = Xml.newPullParser();
        parser.setInput(is, "utf-8");
        int type = parser.getEventType();
        int currCount = 0;
        while (type != XmlPullParser.END_DOCUMENT) {
            switch (type) {
                case XmlPullParser.START_TAG:
                    if ("count".equals(parser.getName())) {
                        pd.setMax(Integer.parseInt(parser.nextText()));
                    }
                    LogUtil.i(TAG, "parser name : " + parser.getName());
                    if ("sms".equals(parser.getName())) {
                        values = new ContentValues();
                    } else if ("address".equals(parser.getName())) {
                        values.put("address", parser.nextText());
                    } else if ("date".equals(parser.getName())) {
                        values.put("date", parser.nextText());
                    } else if ("read".equals(parser.getName())) {
                        values.put("read", parser.nextText());
                    } else if ("body".equals(parser.getName())) {
                        values.put("body", parser.nextText());
                    } else if ("type".equals(parser.getName())) {
                        values.put("type", parser.nextText());
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if ("sms".equals(parser.getName())) {
                        ContentResolver resolver = context.getContentResolver();
                        int size = values.size();
                        LogUtil.i(TAG, "values size : " + size + ">>>values string : " + values.toString());
                        Uri uri = resolver.insert(Telephony.Sms.CONTENT_URI, values);
                        LogUtil.i(TAG, "uri : " + uri);
                        values = null;
                        currCount++;
                        Thread.sleep(1000);
                        LogUtil.i(TAG, "insert finish !");
                        pd.setProgress(currCount);
                    }
                    break;
            }
            type = parser.next();
        }
    }

}
