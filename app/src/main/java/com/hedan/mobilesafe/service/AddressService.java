package com.hedan.mobilesafe.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.hedan.mobilesafe.engine.NumberAddressService;
import com.hedan.mobilesafe.util.LogUtil;

/**
 * Created by Administrator on 2015/11/8.
 */
public class AddressService extends Service {

    private static final String TAG = AddressService.class.getSimpleName();
    private TelephonyManager telephonyManager;
    private MyPhoneListener listener;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.i(TAG, "AddressService Open!");
        listener = new MyPhoneListener();
        telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        telephonyManager.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.i(TAG,"AddressService Close!");
        telephonyManager.listen(listener,PhoneStateListener.LISTEN_NONE);
        listener = null;
    }

    private class MyPhoneListener extends PhoneStateListener{
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);
            LogUtil.i(TAG, "来电号码： " + incomingNumber);
            LogUtil.i(TAG,"phone state : " + state);
            switch (state){
                case TelephonyManager.CALL_STATE_IDLE://处于静止状态，没有呼叫

                    break;
                case TelephonyManager.CALL_STATE_RINGING://铃响状态
                    String address = NumberAddressService.getAddress(incomingNumber);
                    LogUtil.i(TAG,"来电归属地：" + address);
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:// 接通电话状态

                    break;
            }
        }
    }
}
