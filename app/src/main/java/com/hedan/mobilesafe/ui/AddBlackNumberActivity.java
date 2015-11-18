package com.hedan.mobilesafe.ui;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.hedan.dao.BlackNumber;
import com.hedan.mobilesafe.R;
import com.hedan.mobilesafe.db.dao.BlackNumberDaoHelper;

import java.util.Date;

/**
 * Created by Administrator on 2015/11/17.
 */
public class AddBlackNumberActivity extends ToolbarActivity implements View.OnClickListener {
    private Button add_black;
    private Button add_from_contants;
    private EditText et_number;
    private EditText et_name;
    private CheckBox cb_call;
    private CheckBox cb_sms;

    private BlackNumberDaoHelper blackHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add_black_number);

        add_black = (Button) findViewById(R.id.add_black);
        add_from_contants = (Button) findViewById(R.id.add_from_contacts);
        et_number = (EditText) findViewById(R.id.et_add_black_number);
        et_name = (EditText) findViewById(R.id.et_add_black_name);
        cb_call = (CheckBox) findViewById(R.id.cb_call_intercept);
        cb_sms = (CheckBox) findViewById(R.id.cb_sms_intercept);

        blackHelper = BlackNumberDaoHelper.getInstance(this);

        add_black.setOnClickListener(this);
        add_from_contants.setOnClickListener(this);

        getSupportActionBar().setTitle("添加黑名单");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_black:
                String number = et_number.getText().toString().trim();
                String name = et_name.getText().toString().trim();

                if(TextUtils.isEmpty(number)){
                    Toast.makeText(getApplicationContext(),"号码不能为空",Toast.LENGTH_SHORT).show();
                    return ;
                }
                if(TextUtils.isEmpty(name)){
                    Toast.makeText(getApplicationContext(),"名称不能为空",Toast.LENGTH_SHORT).show();
                    return ;
                }

                if(!cb_call.isChecked() && !cb_sms.isChecked()){
                    Toast.makeText(getApplicationContext(),"请选择拦截方式",Toast.LENGTH_SHORT).show();
                    return;
                }

                blackHelper.addData(new BlackNumber(null,number,name,cb_call.isChecked(),cb_sms.isChecked(),new Date()));
                new AlertDialog.Builder(this)
                        .setTitle("提示")
                        .setMessage("添加成功")
                        .setPositiveButton("继续添加", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                et_name.setText("");
                                et_number.setText("");
                                et_number.isFocused();
                                cb_call.setChecked(false);
                                cb_sms.setChecked(false);
                            }
                        })
                        .setNegativeButton("添加完成", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                                Intent returnIntent = new Intent(AddBlackNumberActivity.this,CallSmsSafeActivity.class);
                                startActivity(returnIntent);
                                overridePendingTransition(R.anim.left_in,R.anim.left_out);
                            }
                        })
                        .create()
                        .show();
                break;
            case R.id.add_from_contacts:

                break;
        }
    }
}
