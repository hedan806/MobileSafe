package com.hedan.mobilesafe.ui;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hedan.mobilesafe.R;
import com.hedan.mobilesafe.util.MD5Encoder;

/**
 * Created by Administrator on 2015/10/22.
 */
public class LostProtecteActivity extends Activity implements View.OnClickListener {

    private float x1 = 0 ;
    private float x2 = 0 ;
    private float y1 = 0;
    private float y2 = 0;

    private static final String TAG = "LostProtecteActivity";

    private SharedPreferences sp;
    private Dialog dialog;
    private LayoutInflater inflater;

    private EditText et_pwd;
    private EditText et_repwd;
    private ActionBar actionBar;

    private TextView tv_safe_number;
    private TextView tv_is_open;
    private CheckBox cb_is_open;
    private LinearLayout bt_to_setup;
    private ImageView return_icom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sp = this.getSharedPreferences("config", Context.MODE_APPEND);
        inflater = LayoutInflater.from(this);

        if (isSetPwd()) {
            showInputDialog();
        } else {
            showSetPwdDialog();
        }

    }

    /**
     * 正常登陆输入密码
     */
    private void showInputDialog() {
        dialog = new Dialog(this);
        dialog.setTitle("输入密码");
        dialog.setCancelable(false);
        View view = inflater.inflate(R.layout.lost_input_pwd, null);
        dialog.setContentView(view);
        et_pwd = (EditText) view.findViewById(R.id.et_input_pwd);
        Button b_done = (Button) view.findViewById(R.id.b_input_done);
        Button b_cancel = (Button) view.findViewById(R.id.b_input_cancel);
        b_done.setOnClickListener(this);
        b_cancel.setOnClickListener(this);
        dialog.show();
    }

    /**
     * 显示设置密码界面
     */
    private void showSetPwdDialog() {
        dialog = new Dialog(this);
        dialog.setTitle("设置密码");
        dialog.setCancelable(false);
        View view = inflater.inflate(R.layout.lost_set_pwd, null);
        dialog.setContentView(view);
        et_pwd = (EditText) view.findViewById(R.id.et_lost_pwd);
        et_repwd = (EditText) view.findViewById(R.id.et_lost_repwd);
        Button b_done = (Button) view.findViewById(R.id.b_set_done);
        Button b_cancel = (Button) view.findViewById(R.id.b_set_cancel);
        b_done.setOnClickListener(this);
        b_cancel.setOnClickListener(this);
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.b_set_done:
                Log.i(TAG, "用户点击了确认，进行密码设置");
                String pwd = et_pwd.getText().toString().trim();
                String repwd = et_repwd.getText().toString().trim();
                if ("".equals(pwd) || "".equals(repwd)) {
                    Toast.makeText(getApplicationContext(), "密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    if (pwd.equals(repwd)) {
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("lost_pwd", MD5Encoder.encode(pwd, 100));
                        editor.commit();
                        Toast.makeText(getApplicationContext(), "设置成功", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        showInputDialog();
                    } else {
                        Toast.makeText(getApplicationContext(), "两次密码不一样", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                break;
            case R.id.b_set_cancel:
                dialog.dismiss();
                finish();
                break;
            case R.id.b_input_cancel:
                dialog.dismiss();
                finish();
                break;
            case R.id.b_input_done:
                String input_pwd = et_pwd.getText().toString().trim();
                if ("".equals(input_pwd)) {
                    Toast.makeText(getApplicationContext(), "密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    String sp_input_pwd = sp.getString("lost_pwd", "");
                    if (MD5Encoder.encode(input_pwd, 100).equals(sp_input_pwd)) {
                        Toast.makeText(getApplicationContext(), "登陆成功", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        Log.i(TAG, "登陆成功，进入设置向导或者主界面");
                        if (isSetup()) {
                            Log.i(TAG, "已经设置过了向导，直接进入主界面");
                            setContentView(R.layout.lost_main);
                            sp = this.getSharedPreferences("config",Context.MODE_APPEND);
                            tv_safe_number = (TextView) this.findViewById(R.id.tv_safe_number);
                            tv_is_open = (TextView) this.findViewById(R.id.tv_is_open);
                            cb_is_open = (CheckBox) this.findViewById(R.id.cb_is_open);
                            bt_to_setup = (LinearLayout) this.findViewById(R.id.ll_to_setup);
                            return_icom = (ImageView) this.findViewById(R.id.return_icon);

                            return_icom.setOnClickListener(this);

                            tv_safe_number.setText(sp.getString("safe_number", ""));
                            boolean is_open = sp.getBoolean("open_lost",false);
                            if(is_open){
                                cb_is_open.setChecked(true);
                                tv_is_open.setText("防护已开启");
                            }else{
                                cb_is_open.setChecked(false);
                                tv_is_open.setText("防护未开启");
                            }

                            bt_to_setup.setOnClickListener(this);


                        } else {
                            Log.i(TAG, "没有设置向导，进入向导设置页面");
                            Intent intent = new Intent(LostProtecteActivity.this, LostSetup1Activity.class);
                            finish();
                            startActivity(intent);
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "密码错误", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                break;
            case R.id.ll_to_setup:
                Intent intent = new Intent(this,LostSetup1Activity.class);
                finish();
                startActivity(intent);
                break;
            case R.id.return_icon:
                finish();
                overridePendingTransition(R.anim.left_iner,R.anim.left_outer);
                break;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            x1 = event.getX();
            y1 = event.getY();
        }
        if(event.getAction() == MotionEvent.ACTION_UP) {
            //当手指离开的时候
            x2 = event.getX();
            y2 = event.getY();
            if(y1 - y2 > 50) {
                Toast.makeText(getApplicationContext(), "向上滑", Toast.LENGTH_SHORT).show();
            } else if(y2 - y1 > 50) {
                Toast.makeText(getApplicationContext(), "向下滑", Toast.LENGTH_SHORT).show();
            } else if(x1 - x2 > 50) {
                //Toast.makeText(getApplicationContext(), "向左滑", Toast.LENGTH_SHORT).show();
            } else if(x2 - x1 > 50) {
                finish();
                overridePendingTransition(R.anim.left_iner, R.anim.left_outer);
//                Toast.makeText(getApplicationContext(), "向右滑", Toast.LENGTH_SHORT).show();
            }
        }
        return super.onTouchEvent(event);
    }

    /**
     * 是否设置了向导
     *
     * @return
     */
    private boolean isSetup() {
        return sp.getBoolean("lost_setup", false);
    }

    /**
     * 是否为第一次进入防盗界面
     *
     * @return
     */
    private boolean isSetPwd() {
        String sp_pwd = sp.getString("lost_pwd", "");
        if ("".equals(sp_pwd)) {
            return false;
        } else {
            return true;
        }
    }
}
