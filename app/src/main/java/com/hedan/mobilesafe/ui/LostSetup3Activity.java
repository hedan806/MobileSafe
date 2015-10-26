package com.hedan.mobilesafe.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hedan.mobilesafe.R;

/**
 * Created by Administrator on 2015/10/25.
 */
public class LostSetup3Activity extends Activity{
    private static final String TAG = "LostSetup3Activity";
    private float x1 = 0 ;
    private float x2 = 0 ;
    private float y1 = 0;
    private float y2 = 0;

    private Button bt_select_contact ;
    private EditText et_number;
    private SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lost_setup_3);

        bt_select_contact = (Button) this.findViewById(R.id.bt_lost_setup_3_opencontacts);
        et_number = (EditText) this.findViewById(R.id.et_lost_setup_3_safephone);
        sp = this.getSharedPreferences("config", Context.MODE_APPEND);

        et_number.setText(sp.getString("safenumber",""));

        bt_select_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.bt_lost_setup_3_opencontacts :
                        Log.i(TAG,"打开联系人选择界面");
                        Intent intent = new Intent(LostSetup3Activity.this,ContactSelectedActivity.class);
                        startActivityForResult(intent, 0);
                        break;
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG,"data: " + data);
        if(data != null){
            String number = data.getStringExtra("number");
            et_number.setText(number);
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
                String number = et_number.getText().toString().trim();
                if("".equals(number)){
                    Toast.makeText(getApplicationContext(),"安全号码不能为空",Toast.LENGTH_SHORT).show();

                }else{
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("safenumber",number);
                    editor.commit();
                    Toast.makeText(getApplicationContext(),"保存成功",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LostSetup3Activity.this,LostSetup4Activity.class);
                    finish();
                    startActivity(intent);
                    overridePendingTransition(R.anim.left_in,R.anim.left_outer);
                }
                //Toast.makeText(getApplicationContext(), "向左滑", Toast.LENGTH_SHORT).show();
            } else if(x2 - x1 > 50) {
                Intent intent = new Intent(LostSetup3Activity.this,LostSetup2Activity.class);
                finish();
                startActivity(intent);
                overridePendingTransition(R.anim.left_out,R.anim.left_iner);
                //Toast.makeText(getApplicationContext(), "向右滑", Toast.LENGTH_SHORT).show();
            }
        }
        return super.onTouchEvent(event);
    }
}
