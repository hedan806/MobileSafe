package com.hedan.mobilesafe.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.hedan.mobilesafe.R;

/**
 * Created by Administrator on 2015/10/25.
 */
public class LostSetup2Activity extends Activity implements View.OnClickListener {
    private float x1 = 0 ;
    private float x2 = 0 ;
    private float y1 = 0;
    private float y2 = 0;

    private Button bt_bind ;
    private CheckBox cb_bind;

    private SharedPreferences sp ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lost_setup_2);

        sp = this.getSharedPreferences("config", Context.MODE_APPEND);

        bt_bind = (Button) this.findViewById(R.id.bt_lost_setup_2_bind);
        cb_bind = (CheckBox) this.findViewById(R.id.cb_lost_setup_2_bind);

        bt_bind.setOnClickListener(this);
        String sim = sp.getString("sim","");
        if("".equals(sim)){
            cb_bind.setText("未绑定");
            cb_bind.setChecked(false);
        }else{
            cb_bind.setText("已经绑定");
            cb_bind.setChecked(true);
        }
        cb_bind.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    cb_bind.setText("已经绑定");
                    setSimInfo();
                }else{
                    cb_bind.setText("未绑定");
                    unBindSim();
                }
            }
        });

    }

    /**
     * 解绑SIM卡信息
     */
    private void unBindSim() {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("sim","");
        editor.commit();
        Toast.makeText(getApplicationContext(),"解绑成功",Toast.LENGTH_SHORT).show();
    }

    /**
     * 绑定SIM卡信息
     */
    private void setSimInfo() {
        TelephonyManager manager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        String simSerial = manager.getSimSerialNumber();
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("sim",simSerial);
        editor.commit();
        Toast.makeText(getApplicationContext(),"绑定成功",Toast.LENGTH_SHORT).show();
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
                Intent intent = new Intent(LostSetup2Activity.this,LostSetup3Activity.class);
                finish();
                startActivity(intent);
                overridePendingTransition(R.anim.left_in,R.anim.left_out);
                //Toast.makeText(getApplicationContext(), "向左滑", Toast.LENGTH_SHORT).show();
            } else if(x2 - x1 > 50) {
                Intent intent = new Intent(LostSetup2Activity.this,LostSetup1Activity.class);
                finish();
                startActivity(intent);
                overridePendingTransition(R.anim.left_iner,R.anim.left_outer);
                //Toast.makeText(getApplicationContext(), "向右滑", Toast.LENGTH_SHORT).show();
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_lost_setup_2_bind :
                setSimInfo();
                cb_bind.setText("已经绑定");
                cb_bind.setChecked(true);
                break;
        }
    }
}
