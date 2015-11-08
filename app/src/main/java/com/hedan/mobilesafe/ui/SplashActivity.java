package com.hedan.mobilesafe.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hedan.mobilesafe.R;
import com.hedan.mobilesafe.domain.UpdateInfo;
import com.hedan.mobilesafe.engine.UpdateInfoParser;
import com.hedan.mobilesafe.util.HttpCallbackListener;
import com.hedan.mobilesafe.util.HttpUtil;
import com.hedan.mobilesafe.util.LogUtil;

import java.lang.ref.WeakReference;

public class SplashActivity extends Activity {

    private static final String TAG = "SplashActivity";
    private TextView tv_version;
    private LinearLayout ll;

    private android.support.v7.app.ActionBar actionBar ;

    private static final int SHOW_RESPONSE = 0;

    private Handler handler = new MyHandler(this);
    private UpdateInfo info;
    private String version;

    static class MyHandler extends Handler{
        WeakReference<SplashActivity> mActivity;
        MyHandler(SplashActivity activity){
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            SplashActivity theActivity = mActivity.get();
            switch (msg.what){
                case SHOW_RESPONSE:
                    String response  = (String) msg.obj;
                    theActivity.tv_version.setText(response);
                    break;

            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        version = getVersion();

        tv_version = (TextView) this.findViewById(R.id.tv_splash_version);
        tv_version.setText(version);

        ll = (LinearLayout) this.findViewById(R.id.ll_splash_main);
        AlphaAnimation aa = new AlphaAnimation(0.0f,1.0f);
        aa.setDuration(2000);
        ll.startAnimation(aa);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    isNeedUpdate(version);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();



    }

    /**
     * 是否需要升级
     * @param version
     * @return
     */
    private void isNeedUpdate(final String version){
        String path = getResources().getString(R.string.updateurl);
        HttpUtil.sendHttpRequestStr(path, new HttpCallbackListener() {
            @Override
            public void onFinish(Object response) throws Exception {
                info = UpdateInfoParser.getUpdateInfo(String.valueOf(response));
                String up_version = info.getVersion();
                if (up_version.equalsIgnoreCase(version)) {
                    LogUtil.i(TAG, "版本相同，无需升级");
                    loadMainUI();
                } else {
                    LogUtil.i(TAG, "版本不同，需要升级");

                }
            }

            @Override
            public void onError(Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LogUtil.i(TAG,"获取更新信息异常");
                        Toast.makeText(getApplicationContext(), "获取更新信息失败", Toast.LENGTH_SHORT).show();
                        loadMainUI();
                    }
                });
            }
        });
    }

    /**
     * 加载主界面
     */
    private void loadMainUI() {
        Intent intent = new Intent(this,MainContentActivity.class);
        finish();
        startActivity(intent);
        overridePendingTransition(R.anim.left_in,R.anim.left_out);
    }


    /**
     * 获取系统版本号
     * @return
     */
    private String getVersion() {
        PackageManager manager = getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(getPackageName(), 0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "版本号未知";
        }
    }
}
