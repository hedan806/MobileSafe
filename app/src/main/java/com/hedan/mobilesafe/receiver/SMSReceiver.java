package com.hedan.mobilesafe.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.text.TextUtils;

import com.hedan.dao.BlackNumber;
import com.hedan.dao.BlackNumberDao;
import com.hedan.mobilesafe.R;
import com.hedan.mobilesafe.db.dao.BlackNumberDaoHelper;
import com.hedan.mobilesafe.engine.GPSInfoProvider;
import com.hedan.mobilesafe.ui.CallSmsSafeActivity;
import com.hedan.mobilesafe.util.LogUtil;

import de.greenrobot.dao.query.Query;

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
            BlackNumberDao dao = BlackNumberDaoHelper.getInstance(context).getDaoInstance();
            Query query = dao.queryBuilder().where(BlackNumberDao.Properties.Phone.eq(sender)).build();
            BlackNumber result = (BlackNumber) query.list().get(0);
            if(result != null){
                if(result.getSms_intercept()){
                    abortBroadcast();

                    NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    Intent notifyIntent = new Intent(context, CallSmsSafeActivity.class);
                    PendingIntent pi = PendingIntent.getActivity(context, 0, notifyIntent, 0);
                    Notification notify = new Notification.Builder(context)
                            .setAutoCancel(true)
                            .setSmallIcon(R.drawable.logo_wx)
                            .setTicker("拦截到黑名单号码短信")
                            .setContentTitle("拦截到黑名单号码：" + sender)
                            .setContentText("黑名单号码：" + sender + "短信，已经被拦截。")
                            .setWhen(System.currentTimeMillis())
                            .setContentIntent(pi)
                            .build();
                    manager.notify(1, notify);
                }
            }
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
