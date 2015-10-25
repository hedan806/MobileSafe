package com.hedan.mobilesafe.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.hedan.mobilesafe.R;

/**
 * Created by Administrator on 2015/10/25.
 */
public class LostSetup4Activity extends Activity{
    private float x1 = 0 ;
    private float x2 = 0 ;
    private float y1 = 0;
    private float y2 = 0;

    private static final String TAG = "LostSetup4Activity";

    private CheckBox cb_open;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lost_setup_4);

        cb_open = (CheckBox) this.findViewById(R.id.cb_lost_setup_4_opensafe);
        sp = this.getSharedPreferences("config", Context.MODE_APPEND);

        boolean is_open = sp.getBoolean("open_lost",false);
        if(is_open){
            cb_open.setText("保护未开启");
            cb_open.setChecked(false);
        }else{
            cb_open.setText("保护已开启");
            cb_open.setChecked(true);
        }

        cb_open.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){

                }else{

                }
            }
        });

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
                Intent intent = new Intent(LostSetup4Activity.this,LostProtecteActivity.class);
                finish();
                startActivity(intent);
                //Toast.makeText(getApplicationContext(), "向左滑", Toast.LENGTH_SHORT).show();
            } else if(x2 - x1 > 50) {
                Intent intent = new Intent(LostSetup4Activity.this,LostSetup3Activity.class);
                finish();
                startActivity(intent);
                overridePendingTransition(R.anim.left_out,R.anim.left_iner);
                //Toast.makeText(getApplicationContext(), "向右滑", Toast.LENGTH_SHORT).show();
            }
        }
        return super.onTouchEvent(event);
    }
}
