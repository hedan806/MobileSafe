package com.hedan.mobilesafe.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.hedan.mobilesafe.R;


/**
 * Created by Administrator on 2015/11/3.
 */
public class WXActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wx_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.wx_toolbar);

        toolbar.setLogo(R.drawable.logo_wx);
        toolbar.setTitle("微信");

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.logo_wx);

    }

}
