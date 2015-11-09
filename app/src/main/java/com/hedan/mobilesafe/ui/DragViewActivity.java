package com.hedan.mobilesafe.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hedan.mobilesafe.R;
import com.hedan.mobilesafe.util.LogUtil;
import com.hedan.mobilesafe.util.ScreenUtils;
import com.hedan.mobilesafe.util.SharedPreferencesUtils;

/**
 * Created by Administrator on 2015/11/9.
 */
public class DragViewActivity extends Activity implements View.OnTouchListener {

    private static final String TAG = DragViewActivity.class.getSimpleName();
    private RelativeLayout rl ;
    private TextView address_desc;

    private int startX;
    private int startY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_drag_view);

        rl = (RelativeLayout) findViewById(R.id.id_rl_address);
        address_desc = (TextView) findViewById(R.id.address_desc);

        rl.setOnTouchListener(this);

        int lastX = (int) SharedPreferencesUtils.getParam(DragViewActivity.this,"lastX",0);
        int lastY = (int) SharedPreferencesUtils.getParam(DragViewActivity.this,"lastY",0);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) rl.getLayoutParams();
        params.setMargins(lastX,lastY,0,0);
        rl.invalidate();
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()){
            case R.id.id_rl_address:
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN://手指第一次按下
                        startX = (int) event.getRawX();
                        startY = (int) event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE://手指移动
                        int x = (int) event.getRawX();
                        int y = (int) event.getRawY();
                        int screenH = ScreenUtils.getScreenHeight(this);
                        int screenW = ScreenUtils.getScreenWidth(this);
                        LogUtil.i(TAG,"screenH : " + screenH + "////screenW : " + screenW);
                        if(y > screenH/2){
                            address_desc.layout(address_desc.getLeft(),screenH/8,address_desc.getRight(),screenH/8 + 150);
                        }else{
                            address_desc.layout(address_desc.getLeft(),screenH/10*8 ,address_desc.getRight(),screenH/10*8 + 150);
                        }
                        //获取手指移动的距离
                        int dx = x - startX;
                        int dy = y - startY;

                        int l = rl.getLeft();
                        int t = rl.getTop();
                        int r = rl.getRight();
                        int b = rl.getBottom();

                        rl.layout(l + dx,t+dy,r+dx,b+dy);

                        //获取手指移动后的位置
                        startX = (int) event.getRawX();
                        startY = (int) event.getRawY();



                        break;
                    case MotionEvent.ACTION_UP://手指松开

                        int lastX = rl.getLeft();
                        int lastY = rl.getTop();
                        SharedPreferencesUtils.setParam(DragViewActivity.this,"lastX",lastX);
                        SharedPreferencesUtils.setParam(DragViewActivity.this,"lastY",lastY);

                        break;
                }
                break;
        }
        return true;
    }
}
