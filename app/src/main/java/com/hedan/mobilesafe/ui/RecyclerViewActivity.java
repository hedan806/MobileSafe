package com.hedan.mobilesafe.ui;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.hedan.mobilesafe.R;

/**
 * Created by Administrator on 2015/11/16.
 */
public class RecyclerViewActivity extends ToolbarActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyler_main);
    }
}
