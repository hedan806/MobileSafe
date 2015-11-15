package com.hedan.mobilesafe.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.hedan.dao.BlackNumber;
import com.hedan.mobilesafe.R;
import com.hedan.mobilesafe.adapter.CommonAdapter;
import com.hedan.mobilesafe.adapter.ViewHolder;
import com.hedan.mobilesafe.db.dao.BlackNumberDaoHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/11/12.
 */
public class CallSmsSafeActivity extends ToolbarActivity implements View.OnClickListener {
    private ListView ll;
    private CommonAdapter mAdapter;
    private Button id_add_black;
    private List<BlackNumber> mDatas;

    private BlackNumberDaoHelper blackDaoHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.call_sms_safe_main);

        ll = (ListView) findViewById(R.id.id_black_list);
        id_add_black = (Button) findViewById(R.id.id_add_black_number);
        blackDaoHelper = BlackNumberDaoHelper.getInstance(this);

        id_add_black.setOnClickListener(this);

        mDatas = blackDaoHelper.getAllData();
        ll.setAdapter(mAdapter = new CommonAdapter<BlackNumber>(getApplicationContext(),mDatas,R.layout.call_sms_safe_list_item){
            @Override
            public void convert(ViewHolder helper, BlackNumber item) {
                helper.setText(R.id.id_safe_item,item.getPhone());
            }
        });

        ll.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final BlackNumber blackNumber = mDatas.get(position);
                new AlertDialog.Builder(CallSmsSafeActivity.this)
                        .setTitle("提示")
                        .setMessage("是否删除此黑名单号码")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                blackDaoHelper.deleteData(String.valueOf(blackNumber.getId()));
                                //TODO ListView刷新暂时解决方法
                                mDatas = blackDaoHelper.getAllData();
                                ll.setAdapter(mAdapter = new CommonAdapter<BlackNumber>(getApplicationContext(), mDatas, R.layout.call_sms_safe_list_item) {
                                    @Override
                                    public void convert(ViewHolder helper, BlackNumber item) {
                                        helper.setText(R.id.id_safe_item, item.getPhone());
                                    }
                                });
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .create()
                        .show();

                return true;
            }
        });

        getSupportActionBar().setTitle("通讯卫士");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.id_add_black_number:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("添加黑名单");
                final EditText et = new EditText(this);
                builder.setView(et);
                builder.setPositiveButton("添加", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String number = et.getText().toString().trim();
                        if(TextUtils.isEmpty(number)){
                            Toast.makeText(getApplicationContext(),"黑名单号码不能为空",Toast.LENGTH_SHORT).show();
                        }else{
                            blackDaoHelper.addData(new BlackNumber(null,number,new Date()));
                            // TODO 通知ListView更新，暂时解决方法
                            mDatas = blackDaoHelper.getAllData();
                            ll.setAdapter(mAdapter = new CommonAdapter<BlackNumber>(getApplicationContext(), mDatas, R.layout.call_sms_safe_list_item) {
                                @Override
                                public void convert(ViewHolder helper, BlackNumber item) {
                                    helper.setText(R.id.id_safe_item, item.getPhone());
                                }
                            });
                        }
                    }
                });

                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.create().show();
                break;
        }
    }
}
