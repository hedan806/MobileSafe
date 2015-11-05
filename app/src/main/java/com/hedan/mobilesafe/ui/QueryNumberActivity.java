package com.hedan.mobilesafe.ui;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hedan.mobilesafe.R;
import com.hedan.mobilesafe.engine.NumberAddressService;
import com.hedan.mobilesafe.util.LogUtil;

/**
 * Created by Administrator on 2015/11/4.
 */
public class QueryNumberActivity extends Activity implements View.OnClickListener {
    private static final String TAG = QueryNumberActivity.class.getSimpleName();
    private EditText et_query_number;
    private Button bt_query;
    private TextView tv_query_result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.atools_query_number);
        initView();
        bt_query.setOnClickListener(this);
    }

    /**
     * 初始化组件
     */
    private void initView() {
        et_query_number = (EditText) findViewById(R.id.et_query_number);
        bt_query = (Button) findViewById(R.id.bt_query);
        tv_query_result = (TextView) findViewById(R.id.tv_query_result);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_query:
                LogUtil.i(TAG,"查询号码归属地");
                String number = et_query_number.getText().toString().trim();
                if(TextUtils.isEmpty(number)){
                    Animation animation = AnimationUtils.loadAnimation(this,R.anim.shake);
                    et_query_number.startAnimation(animation);
                    Toast.makeText(getApplicationContext(),"要查询的手机号不能为空",Toast.LENGTH_SHORT).show();
                    return ;
                }else{
                    String address = NumberAddressService.getAddress(number);
                    LogUtil.i(TAG,"号码归属地：" + address);
                    tv_query_result.setText("号码归属地：" + address);
                }
                break;
        }
    }
}
