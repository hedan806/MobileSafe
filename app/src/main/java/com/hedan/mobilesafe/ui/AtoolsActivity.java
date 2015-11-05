package com.hedan.mobilesafe.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hedan.mobilesafe.R;
import com.hedan.mobilesafe.engine.DownLoadFileService;
import com.hedan.mobilesafe.util.HttpCallbackListener;
import com.hedan.mobilesafe.util.HttpUtil;
import com.hedan.mobilesafe.util.LogUtil;

import java.io.File;

/**
 * Created by Administrator on 2015/11/4.
 */
public class AtoolsActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = AtoolsActivity.class.getSimpleName();

    private TextView tv_query_number;
    private ProgressDialog pd;

    private static final int SUCCESS = 10;
    private static final int ERROR = 11;
    private Message msg;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case SUCCESS :
                    Toast.makeText(getApplicationContext(),"下载数据库成功",Toast.LENGTH_SHORT).show();
                    break;
                case ERROR:
                    Toast.makeText(getApplicationContext(),"下载数据库失败",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.atools_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.id_base_toolbar);
        toolbar.setTitle("高级工具");
        setSupportActionBar(toolbar);

        tv_query_number = (TextView) findViewById(R.id.tv_query_number);
        tv_query_number.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        LogUtil.i(TAG,"click item id : " + item.getItemId());
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.left_iner,R.anim.left_outer);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_query_number:
                LogUtil.i(TAG,"进入手机归属地查询");

                //判断本地数据库是否存在
                if(isDBexist()){
                    Intent queryIntent = new Intent(AtoolsActivity.this,QueryNumberActivity.class);
                    startActivity(queryIntent);
                }else{
                    //提示用户下载
                    pd = new ProgressDialog(this);
                    pd.setMessage("正在下载...");
                    pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    pd.setCancelable(false);
                    //下载文件
                    pd.show();
                    new Thread(){
                        @Override
                        public void run() {
                            String path = getResources().getString(R.string.addressdburl);
                            boolean sdCardIsRead = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
                            LogUtil.i(TAG,"sdcard is open : " + sdCardIsRead);
                            if(sdCardIsRead){
                                String filepath = Environment.getExternalStorageDirectory().getPath() + "address.db";//"/sdcard/address.db";
                                HttpUtil.sendHttpRequest(path, new HttpCallbackListener() {
                                    @Override
                                    public void onFinish(String response) throws Exception {
                                        pd.dismiss();
                                        LogUtil.i(TAG,"response str : " + response);
                                        handler.sendEmptyMessage(SUCCESS);
                                    }

                                    @Override
                                    public void onError(Exception e) {
                                        pd.dismiss();
                                        LogUtil.i(TAG, "error : " + e.getMessage());
                                        handler.sendEmptyMessage(ERROR);
                                    }
                                });
                                /*try {
                                    LogUtil.i(TAG, "start download");

                                    DownLoadFileService service = new DownLoadFileService();
                                    File file = service.getFile(path, filepath, pd);
                                    LogUtil.i(TAG, "file name : " + file.getName());
                                    pd.dismiss();
                                    LogUtil.i(TAG, "end download");
                                    Message msg = new Message();
                                    msg.what = SUCCESS;
                                    handler.handleMessage(msg);
                                } catch (Exception e) {
                                    pd.dismiss();
                                    LogUtil.i(TAG,"error begin");
                                    Message msg = new Message();
                                    msg.what = ERROR;
                                    handler.handleMessage(msg);
                                    e.printStackTrace();
                                }*/
                            }else{
                                Toast.makeText(getApplicationContext(),"SD卡不可用，无法下载",Toast.LENGTH_SHORT).show();
                                return ;
                            }
                        }
                    }.start();
                }
                break;
        }
    }

    private boolean isDBexist() {
        File file = new File(Environment.getExternalStorageDirectory().getPath() + "address.db");
        return file.exists();
    }
}
