package com.hedan.mobilesafe.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.hedan.mobilesafe.util.LogUtil;


/**
 * Created by Administrator on 2015/10/28.
 */
public class BootCompleteReceiver extends BroadcastReceiver {

    private static final String TAG = "BootCompleteReceiver";
    private SharedPreferences sp ;

    @Override
    public void onReceive(Context context, Intent intent) {
        sp = context.getSharedPreferences("config",Context.MODE_APPEND);
        //判断手机是否开启了保护
        if(sp.getBoolean("open_lost",false)){
            TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String currSim = manager.getSimSerialNumber();
            String safeSim = sp.getString("sim","");
            if(!TextUtils.isEmpty(safeSim)){
                if(!safeSim.equals(currSim)){ //串号不同，发送警报
                    SmsManager sms = SmsManager.getDefault();
                    String safe_number = sp.getString("safe_number","");
                    if(!TextUtils.isEmpty(safe_number)){
                        LogUtil.i(TAG,"sim卡发生改变，手机可能被盗");
                        sms.sendTextMessage(safe_number,null,"sim卡发生改变，手机可能被盗",null,null);
                    }
                }
            }
        }
    }
}
