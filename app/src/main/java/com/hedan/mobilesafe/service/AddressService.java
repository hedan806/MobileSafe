package com.hedan.mobilesafe.service;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hedan.mobilesafe.R;
import com.hedan.mobilesafe.engine.NumberAddressService;
import com.hedan.mobilesafe.util.LogUtil;
import com.hedan.mobilesafe.util.SharedPreferencesUtils;

/**
 * Created by Administrator on 2015/11/8.
 */
public class AddressService extends Service {

    private static final String TAG = AddressService.class.getSimpleName();
    private TelephonyManager telephonyManager;
    private MyPhoneListener listener;
    private WindowManager manager;
    private View view;

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
        LogUtil.i(TAG, "AddressService Close!");
        telephonyManager.listen(listener, PhoneStateListener.LISTEN_NONE);
        listener = null;
    }

    private class MyPhoneListener extends PhoneStateListener {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);
            LogUtil.i(TAG, "来电号码： " + incomingNumber);
            LogUtil.i(TAG, "phone state : " + state);
            switch (state) {
                case TelephonyManager.CALL_STATE_IDLE://处于静止状态，没有呼叫
                    if(view != null){
                        manager.removeView(view);
                        view = null;
                    }

                    break;
                case TelephonyManager.CALL_STATE_RINGING://铃响状态
                    String address = NumberAddressService.getAddress(incomingNumber);
                    LogUtil.i(TAG, "来电归属地：" + address);
                    showAddress(address);
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:// 接通电话状态
                    if(view != null){
                        manager.removeView(view);
                        view = null;
                    }
                    break;
            }
        }
    }

    /**
     * 显示位置信息
     */
    private void showAddress(String address) {
        int lastX = (int)SharedPreferencesUtils.getParam(AddressService.this,"lastX",0);
        int lastY = (int)SharedPreferencesUtils.getParam(AddressService.this,"lastY",0);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
        params.format = PixelFormat.TRANSLUCENT;
        params.type = WindowManager.LayoutParams.TYPE_TOAST;
        params.gravity = Gravity.LEFT | Gravity.TOP;
        params.x = lastX;
        params.y = lastY;
        params.setTitle("Toast");
        view = View.inflate(getApplicationContext(), R.layout.show_address,null);
        LinearLayout ll = (LinearLayout) view.findViewById(R.id.id_layout_address);
        int backgroundID = (int) SharedPreferencesUtils.getParam(AddressService.this, "address_background", 0);
        switch (backgroundID){
            case 0:
                ll.setBackgroundResource(R.drawable.call_locate_orange);
                break;
            case 1:
                ll.setBackgroundResource(R.drawable.call_locate_gray);
                break;
            case 2:
                ll.setBackgroundResource(R.drawable.call_locate_white);
                break;
        }


        TextView tv = (TextView) view.findViewById(R.id.id_tv_address);
        tv.setTextSize(24);
        tv.setText(address);
        manager = (WindowManager) this.getSystemService(WINDOW_SERVICE);
        manager.addView(view, params);
    }
}
