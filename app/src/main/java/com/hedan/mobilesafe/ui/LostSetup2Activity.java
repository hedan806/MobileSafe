package com.hedan.mobilesafe.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Toast;

import com.hedan.mobilesafe.R;

/**
 * Created by Administrator on 2015/10/25.
 */
public class LostSetup2Activity extends Activity{
    private float x1 = 0 ;
    private float x2 = 0 ;
    private float y1 = 0;
    private float y2 = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lost_setup_2);
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
                overridePendingTransition(R.anim.left_in,R.anim.left_outer);
                //Toast.makeText(getApplicationContext(), "向左滑", Toast.LENGTH_SHORT).show();
            } else if(x2 - x1 > 50) {
                Intent intent = new Intent(LostSetup2Activity.this,LostSetup1Activity.class);
                finish();
                startActivity(intent);
                overridePendingTransition(R.anim.left_out,R.anim.left_iner);
                //Toast.makeText(getApplicationContext(), "向右滑", Toast.LENGTH_SHORT).show();
            }
        }
        return super.onTouchEvent(event);
    }
}
