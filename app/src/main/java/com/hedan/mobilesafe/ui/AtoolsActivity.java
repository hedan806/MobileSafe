package com.hedan.mobilesafe.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.ParcelUuid;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.hedan.mobilesafe.R;
import com.hedan.mobilesafe.engine.DownLoadFileService;
import com.hedan.mobilesafe.service.AddressService;
import com.hedan.mobilesafe.util.HttpCallbackListener;
import com.hedan.mobilesafe.util.HttpUtil;
import com.hedan.mobilesafe.util.LogUtil;
import com.hedan.mobilesafe.util.SharedPreferencesUtils;

import java.io.File;

/**
 * Created by Administrator on 2015/11/4.
 */
public class AtoolsActivity extends ToolbarActivity implements View.OnClickListener {
    private static final String TAG = AtoolsActivity.class.getSimpleName();

    private TextView tv_query_number;
    private Switch phone_address;
    private ProgressDialog pd;
    private Intent serviceIntent;
    private TextView tv_address_style;
    private TextView tv_address_drag;

    private static final int SUCCESS = 10;
    private static final int ERROR = 11;
    private Message msg;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SUCCESS:
                    Toast.makeText(getApplicationContext(), "下载数据库成功", Toast.LENGTH_SHORT).show();
                    break;
                case ERROR:
                    Toast.makeText(getApplicationContext(), "下载数据库失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.atools_main);

        serviceIntent = new Intent(AtoolsActivity.this, AddressService.class);
        initView();
        tv_query_number.setOnClickListener(this);
        tv_address_style.setOnClickListener(this);
        tv_address_drag.setOnClickListener(this);
        phone_address.setChecked((boolean) SharedPreferencesUtils.getParam(AtoolsActivity.this, "show_number_address", false));
        phone_address.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    startService(serviceIntent);
                    LogUtil.i(TAG, "Switch Open!");
                    SharedPreferencesUtils.setParam(AtoolsActivity.this, "show_number_address", true);
                } else {
                    stopService(serviceIntent);
                    LogUtil.i(TAG, "Switch Close!");
                    SharedPreferencesUtils.setParam(AtoolsActivity.this, "show_number_address", false);
                }
            }
        });
    }

    /**
     * 初始化控件
     */
    private void initView() {
        tv_query_number = (TextView) findViewById(R.id.tv_query_number);
        phone_address = (Switch) findViewById(R.id.id_phone_address);
        tv_address_drag = (TextView) findViewById(R.id.address_drag);
        tv_address_style = (TextView) findViewById(R.id.address_style);
    }

    @Override
    public void onCreateCustomToolbar(Toolbar toolbar) {
        getSupportActionBar().setTitle("高级工具");
        super.onCreateCustomToolbar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_query_number:
                LogUtil.i(TAG, "进入手机归属地查询");
                //判断本地数据库是否存在
                if (isDBexist()) {
                    Intent queryIntent = new Intent(AtoolsActivity.this, QueryNumberActivity.class);
                    startActivity(queryIntent);
                } else {
                    //提示用户下载
                    pd = new ProgressDialog(this);
                    pd.setCancelable(false);
                    //下载文件
                    pd.show();
                    new Thread() {
                        @Override
                        public void run() {
                            String path = getResources().getString(R.string.addressdburl);
                            boolean sdCardIsRead = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
                            LogUtil.i(TAG, "sdcard is open : " + sdCardIsRead);
                            if (sdCardIsRead) {
                                String filepath = Environment.getExternalStorageDirectory().getPath() + "/address.db";//"/sdcard/address.db";
                                HttpUtil.sendHttpRequestFile(path, filepath, pd, new HttpCallbackListener() {
                                    @Override
                                    public void onFinish(Object response) throws Exception {
                                        LogUtil.i(TAG, "response str : " + response);
                                        handler.sendEmptyMessage(SUCCESS);
                                    }

                                    @Override
                                    public void onError(Exception e) {
                                        LogUtil.i(TAG, "error : " + e.getMessage());
                                        handler.sendEmptyMessage(ERROR);
                                    }
                                });
                            } else {
                                Toast.makeText(getApplicationContext(), "SD卡不可用，无法下载", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                    }.start();
                }
                break;
            case R.id.address_style://设置来电归属地样式
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("选择来电归属地样式");
                String[] items = new String[]{"蓝色", "橙色", "黑色"};
                builder.setSingleChoiceItems(items, (int) SharedPreferencesUtils.getParam(AtoolsActivity.this, "address_background", 0), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferencesUtils.setParam(AtoolsActivity.this, "address_background", which);
                    }
                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.create().show();
                break;

            case R.id.address_drag://设置来电归属地位置
                Intent dragIntent = new Intent(this, DragViewActivity.class);
                startActivity(dragIntent);
                break;
        }
    }

    private boolean isDBexist() {
        String root = Environment.getExternalStorageDirectory().getPath();
        LogUtil.i(TAG, "root path : " + root);
        File file = new File(root + "/address.db");
        return file.exists();
    }
}
