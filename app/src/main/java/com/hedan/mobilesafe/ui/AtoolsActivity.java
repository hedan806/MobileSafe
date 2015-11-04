package com.hedan.mobilesafe.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hedan.mobilesafe.R;
import com.hedan.mobilesafe.engine.DownLoadFileService;
import com.hedan.mobilesafe.util.LogUtil;

import java.io.File;

/**
 * Created by Administrator on 2015/11/4.
 */
public class AtoolsActivity extends Activity implements View.OnClickListener {
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
        tv_query_number = (TextView) findViewById(R.id.tv_query_number);
        tv_query_number.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_query_number:
                LogUtil.i(TAG,"进入手机归属地查询");

                //判断本地数据库是否存在
                if(isDBexist()){
                    Intent queryIntetn = new Intent(AtoolsActivity.this,QueryNumberActivity.class);
                    startActivity(queryIntetn);
                }else{
                    //提示用户下载
                    pd = new ProgressDialog(this);
                    pd.setMessage("正在下载...");
                    pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    //下载文件
                    pd.show();
                    new Thread(){
                        @Override
                        public void run() {

                            String path = getResources().getString(R.string.addressdburl);
                            String filepath = "/sdcard/address.db";
                            msg = new Message();
                            try {
                                LogUtil.i(TAG, "start download");
                                DownLoadFileService service = new DownLoadFileService();
                                File file = service.getFile(path, filepath, pd);
                                LogUtil.i(TAG,"file name : " + file.getName());
                                pd.dismiss();
                                LogUtil.i(TAG, "end download");
                                msg.what = SUCCESS;
                                handler.handleMessage(msg);
                            } catch (Exception e) {
                                pd.dismiss();
                                LogUtil.i(TAG,"error begin");
                                e.printStackTrace();
                                msg.what = ERROR;
                                handler.handleMessage(msg);
                            }

                        }
                    }.start();
                }
                break;
        }
    }

    private boolean isDBexist() {
        File file = new File("/sdcard/address.db");
        return file.exists();
    }
}
