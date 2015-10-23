package com.hedan.mobilesafe.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.hedan.mobilesafe.ui.LostProtecteActivity;

/**
 * Created by Administrator on 2015/10/22.
 */
public class CallPhoneReceiver extends BroadcastReceiver{
    private static final String TAG = "CallPhoneReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        String number = getResultData();
        Log.i(TAG,"number : " + number);
        if("101806".equals(number)){
            Intent lostIntent = new Intent(context, LostProtecteActivity.class);
            lostIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //指定要激活的activity在自己的任务栈中
            context.startActivity(lostIntent);
            //终止电话
            //不能通过
            abortBroadcast();
            setResultData(null);
        }
    }
}
