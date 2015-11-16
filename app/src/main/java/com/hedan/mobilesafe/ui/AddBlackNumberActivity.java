package com.hedan.mobilesafe.ui;


import android.os.Bundle;

import com.hedan.mobilesafe.R;

/**
 * Created by Administrator on 2015/11/17.
 */
public class AddBlackNumberActivity extends ToolbarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_add_black_number);

        getSupportActionBar().setTitle("添加黑名单");
    }
}
