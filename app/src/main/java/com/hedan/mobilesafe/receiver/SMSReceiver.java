package com.hedan.mobilesafe.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.text.TextUtils;

import com.hedan.mobilesafe.R;
import com.hedan.mobilesafe.engine.GPSInfoProvider;
import com.hedan.mobilesafe.util.LogUtil;

/**
 * Created by Administrator on 2015/11/3.
 */
public class SMSReceiver extends BroadcastReceiver {
    private static final String TAG = "SMSReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        //获取短信内容
        Object[] pdus = (Object[]) intent.getExtras().get("pdus");
        for(Object pdu : pdus){
            SmsMessage sms = SmsMessage.createFromPdu((byte[]) pdu);
            String content = sms.getMessageBody();
            String sender = sms.getOriginatingAddress();
            LogUtil.i(TAG,"短信内容：" + content);
            //获取GPS位置
            if("#*location*#".equals(content)){
                abortBroadcast();//终止广播
                GPSInfoProvider provider = GPSInfoProvider.getInstance(context);
                String location = provider.getLocation();
                SmsManager smsManager = SmsManager.getDefault();
                if(!TextUtils.isEmpty(location)){
                    LogUtil.i(TAG,"发送GPS位置信息到用户："  +sender+ " : " + location);
                    smsManager.sendTextMessage(sender, null, location, null, null);
                }
            }else if("#*alarm*#".equals(content)){
                LogUtil.i(TAG,"播放报警音乐");
                MediaPlayer player = MediaPlayer.create(context, R.raw.alarm);
                player.setVolume(1.0f,1.0f);
                player.start();
                abortBroadcast();
            }
        }
    }
}
