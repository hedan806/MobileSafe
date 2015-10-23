package com.hedan.mobilesafe.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hedan.mobilesafe.R;
import com.hedan.mobilesafe.domain.UpdateInfo;
import com.hedan.mobilesafe.engine.DownLoadFileService;
import com.hedan.mobilesafe.engine.UpdateInfoService;

import java.io.File;


public class SplashActivity extends Activity {

    private static final String TAG = "SplashActivity";

    private TextView tv_splash_version;
    private LinearLayout ll_splash_main;
    private String version;
    private UpdateInfo info;

    //private MyHandler myHandler;
    private Handler myHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            boolean result = data.getBoolean("result");
            Log.i(TAG, result + "");
            if (result) {
                showUpdateDialog();
            } else {
                Toast.makeText(SplashActivity.this, "获取更新信息失败", Toast.LENGTH_SHORT).show();
                loadMainUI();
            }
        }
    };

    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //隐藏标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        //全屏显示
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        tv_splash_version = (TextView) this.findViewById(R.id.tv_splash_version);
        version = getVersion();

        pd = new ProgressDialog(this);
        pd.setTitle("正在下载");
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

//        myHandler = new MyHandler();

        NetWordTask task = new NetWordTask(version);
        new Thread(task).start();

        tv_splash_version.setText(version);
        ll_splash_main = (LinearLayout) this.findViewById(R.id.ll_splash_main);
        AlphaAnimation aa = new AlphaAnimation(0.0f, 1.0f);
        aa.setDuration(2000);
        ll_splash_main.startAnimation(aa);

    }

    /*private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            Looper.prepare();
            super.handleMessage(msg);
            Bundle data = msg.getData();
            boolean result = data.getBoolean("result");
            Log.i(TAG, result + "");
            if (result) {
                showUpdateDialog();
            } else {
                loadMainUI();
            }
            Looper.loop();
        }
    }*/

    /**
     * 网络操作相关的子线程
     */
    private class NetWordTask implements Runnable {
        private String version;

        public NetWordTask(String version) {
            this.version = version;
        }

        @Override
        public void run() {
//            boolean isNeed = isNeedUpdate(version);
            boolean isNeed = false;
            Message msg = new Message();
            Bundle data = new Bundle();
            try {
                UpdateInfoService service = new UpdateInfoService(getBaseContext());
                info = service.getUpdateInfo(R.string.updateurl);
                String up_version = info.getVersion();
                if (up_version.equals(version)) {
                    Log.i(TAG, "版本相同，无需更新，进入主界面");
                    loadMainUI();
                    isNeed = false;
                } else {
                    Log.i(TAG, "版本不同，需要升级");
                    isNeed = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
//                Toast.makeText(SplashActivity.this, "获取更新信息失败", Toast.LENGTH_SHORT).show();
                Log.i(TAG, "获取更新信息异常，进入主界面");
//                loadMainUI();
                isNeed = false;
            }finally {
                data.putBoolean("result", isNeed);
                msg.setData(data);
                myHandler.sendMessage(msg);
            }
        }
    }

    /**
     * 下载文件子线程
     */
    private class DownFileTask implements Runnable {
        private String path;
        private String filepath;

        public DownFileTask(String path, String filepath) {
            this.path = path;
            this.filepath = filepath;
        }

        @Override
        public void run() {
            DownLoadFileService fileService = new DownLoadFileService();
            try {
                File file = fileService.getFile(path, filepath,pd);
                Log.i(TAG, "下载完成");
                install(file);
                pd.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "下载文件失败", Toast.LENGTH_SHORT).show();
                pd.dismiss();
                loadMainUI();
            }
        }
    }

    /**
     * 安装apk
     * @param file
     */
    private void install(File file){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");

        finish();
        startActivity(intent);
    }

    /**
     * 显示升级对话框
     */
    private void showUpdateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
        builder.setTitle("升级提醒");
        builder.setMessage(info.getDesc());
        builder.setCancelable(false);
        builder.setPositiveButton("确定", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i(TAG, "下载apk文件" + info.getApkurl());
                //是否有存储卡
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    DownFileTask fileTask = new DownFileTask(info.getApkurl(), "/sdcard/new.apk");
                    pd.show();
                    new Thread(fileTask).start();
                } else {
                    Toast.makeText(getApplicationContext(), "SD卡不可用", Toast.LENGTH_SHORT).show();
                    loadMainUI();
                }
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i(TAG, "用户取消，直接进入主界面");
                loadMainUI();
            }
        });

        builder.create().show();
    }

    /**
     * 主界面初始化
     */
    private void loadMainUI() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * 是否需要更新升级
     *
     * @param version
     * @return
     */
    private boolean isNeedUpdate(String version) {
        try {
            UpdateInfoService service = new UpdateInfoService(this);
            info = service.getUpdateInfo(R.string.updateurl);
            String up_version = info.getVersion();
            if (up_version.equals(version)) {
                Log.i(TAG, "版本相同，无需更新，进入主界面");
                loadMainUI();
                return false;
            } else {
                Log.i(TAG, "版本不同，需要升级");
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "获取更新信息失败", Toast.LENGTH_SHORT).show();
            Log.i(TAG, "获取更新信息异常，进入主界面");
            loadMainUI();
            return false;
        }
    }

    /**
     * 获取当前版本号
     *
     * @return
     */
    private String getVersion() {
        try {
            PackageManager manager = getPackageManager();
            PackageInfo info = manager.getPackageInfo(getPackageName(), 0);
            return info.versionName;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "版本号未知";
        }

    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
