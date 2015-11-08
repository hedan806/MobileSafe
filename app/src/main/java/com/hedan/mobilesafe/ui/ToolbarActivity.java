package com.hedan.mobilesafe.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.hedan.mobilesafe.R;
import com.hedan.mobilesafe.util.ToolbarHelper;

/**
 * Created by Administrator on 2015/11/8.
 */
public abstract class ToolbarActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ToolbarHelper mToolHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID) {
        mToolHelper = new ToolbarHelper(this,layoutResID);
        toolbar = mToolHelper.getToolbar();
        setContentView(mToolHelper.getContentView());
        setSupportActionBar(toolbar);
        /**
         * 自定义的一些操作
         */
        onCreateCustomToolbar(toolbar);
    }

    public void onCreateCustomToolbar(Toolbar toolbar){
        toolbar.setContentInsetsRelative(0,0);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.left_iner, R.anim.left_outer);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
