package com.hedan.mobilesafe.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Environment;
import android.util.Xml;

import com.hedan.mobilesafe.domain.SmsInfo;
import com.hedan.mobilesafe.engine.SmsInfoService;
import com.hedan.mobilesafe.util.LogUtil;

import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

/**
 * Created by Administrator on 2015/11/18.
 */
public class SmsBackupService extends IntentService {

    private static final String TAG = SmsBackupService.class.getSimpleName();

    public SmsBackupService() {
        super("SmsBackUpService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        LogUtil.i(TAG,"短信备份服务开启，开始备份。Thread ID : " + Thread.currentThread().getId());
        SmsInfoService service = new SmsInfoService(this);
        File file;
        file = new File(Environment.getExternalStorageDirectory(), "sms_backup.xml");
        LogUtil.i(TAG,"file path : " + file.getPath());
        try {
            FileOutputStream os = new FileOutputStream(file);
            List<SmsInfo> infos = service.getSmsInfos();

            XmlSerializer serializer = Xml.newSerializer();
            serializer.setOutput(os, "utf-8");
            serializer.startDocument("utf-8", true);
            serializer.startTag(null, "smss");

            serializer.startTag(null, "count");
            serializer.text(infos.size() + "");
            serializer.endTag(null, "count");
            for (SmsInfo info : infos) {
                serializer.startTag(null, "sms");

                serializer.startTag(null, "id");
                serializer.text(info.getId() + "");
                serializer.endTag(null, "id");

                serializer.startTag(null, "address");
                serializer.text(info.getAddress());
                serializer.endTag(null, "address");

                serializer.startTag(null, "date");
                serializer.text(info.getDate() + "");
                serializer.endTag(null, "date");

                serializer.startTag(null, "body");
                serializer.text(info.getBody() + "");
                serializer.endTag(null, "body");

                serializer.startTag(null, "type");
                serializer.text(info.getType() + "");
                serializer.endTag(null, "type");

                serializer.startTag(null, "read");
                serializer.text(info.getRead() + "");
                serializer.endTag(null, "read");

                serializer.endTag(null, "sms");
            }

            serializer.endTag(null, "smss");
            serializer.endDocument();

            os.flush();
            os.close();
        } catch (Exception e) {
            LogUtil.w("ExternalStorage", "Error writing " + file, e);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.i(TAG,"短信备份完成，服务关闭");
    }
}
