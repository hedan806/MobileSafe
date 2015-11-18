package com.hedan.mobilesafe.service;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by Administrator on 2015/11/18.
 */
public class SmsRestoreService extends IntentService{
    public SmsRestoreService() {
        super("SmsRestoreService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
