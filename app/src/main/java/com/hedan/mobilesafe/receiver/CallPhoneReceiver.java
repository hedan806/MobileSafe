package com.hedan.mobilesafe.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.hedan.dao.BlackNumberDao;
import com.hedan.mobilesafe.R;
import com.hedan.mobilesafe.db.dao.BlackNumberDaoHelper;
import com.hedan.mobilesafe.ui.CallSmsSafeActivity;
import com.hedan.mobilesafe.ui.LostProtecteActivity;
import com.hedan.mobilesafe.util.LogUtil;
import com.android.internal.telephony.*;
import com.android.internal.telephony.ITelephony;

import java.lang.reflect.Method;
import java.util.List;

import de.greenrobot.dao.query.Query;

/**
 * Created by Administrator on 2015/10/22.
 */
public class CallPhoneReceiver extends BroadcastReceiver {
    private static final String TAG = "CallPhoneReceiver";
    private TelephonyManager tm;

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        //去电Action
        if (action.equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
            String number = getResultData();
            Log.i(TAG, "number : " + number);
            if ("101806".equals(number)) {
                Intent lostIntent = new Intent(context, LostProtecteActivity.class);
                lostIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //指定要激活的activity在自己的任务栈中
                context.startActivity(lostIntent);
                //终止电话
                //不能通过
                abortBroadcast();
                setResultData(null);
            }
        } else {
            tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            switch (tm.getCallState()) {
                case TelephonyManager.CALL_STATE_IDLE://正常状态

                    break;
                case TelephonyManager.CALL_STATE_RINGING://响铃状态
                    String number = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
                    LogUtil.i(TAG, "来电号码" + number + "，响铃状态");
                    BlackNumberDao dao = BlackNumberDaoHelper.getInstance(context).getDaoInstance();
                    Query query = dao.queryBuilder().where(BlackNumberDao.Properties.Phone.eq(number)).build();
                    List list = query.list();
                    for (Object phone : list) {
                        LogUtil.i(TAG, "phone : " + phone);
                    }
                    if (list.size() > 0) { //来电号码为拦截号码
                        LogUtil.i(TAG, "拦截黑名单号码：" + number);
                        endCall();
                        LogUtil.i(TAG, "显示通知");
                        Toast.makeText(context.getApplicationContext(),"拦截到黑名单号码：" + number,Toast.LENGTH_LONG).show();
                        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                        Intent notifyIntent = new Intent(context, CallSmsSafeActivity.class);
                        PendingIntent pi = PendingIntent.getActivity(context, 0, notifyIntent, 0);
                        Notification notify = new Notification.Builder(context)
                                .setAutoCancel(true)
                                .setSmallIcon(R.drawable.logo_wiki)
                                .setTicker("拦截到黑名单号码")
                                .setContentTitle("拦截到黑名单号码：" + number)
                                .setContentText("黑名单号码：" + number + "来电，已经被拦截。")
                                .setWhen(System.currentTimeMillis())
                                .setContentIntent(pi)
                                .build();
                        manager.notify(1, notify);
                    } else {
                        LogUtil.i(TAG, "没有拦截");
                    }
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK://接通状态

                    break;
            }
            //abortBroadcast();
        }

    }

    /**
     * 挂断电话
     */
    private void endCall() {
        Class<TelephonyManager> c = TelephonyManager.class;
        try {
            Method getITelephonyMethod = c.getDeclaredMethod("getITelephony", (Class[]) null);
            getITelephonyMethod.setAccessible(true);
            ITelephony iTelephony = null;
            LogUtil.e(TAG, "End call.");
            iTelephony = (ITelephony) getITelephonyMethod.invoke(tm, (Object[]) null);
            iTelephony.endCall();
        } catch (Exception e) {
            LogUtil.e(TAG, "Fail to answer ring call.", e);
        }
    }
}

