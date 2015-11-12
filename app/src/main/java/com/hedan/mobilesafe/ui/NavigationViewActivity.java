package com.hedan.mobilesafe.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.hedan.mobilesafe.R;
import com.hedan.mobilesafe.util.LogUtil;

/**
 * Created by Administrator on 2015/11/5.
 */
public class NavigationViewActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = NavigationViewActivity.class.getSimpleName();

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private Button toMain;
    private Button toList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.navigation_view);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.id_drawerLayout);
        mNavigationView = (NavigationView) findViewById(R.id.id_nav_view);
        toMain = (Button) findViewById(R.id.id_to_main);
        toList = (Button)findViewById(R.id.id_to_list);



        Toolbar toolbar = (Toolbar) findViewById(R.id.id_toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);

        setupNavigation(mNavigationView);
        toMain.setOnClickListener(this);
        toList.setOnClickListener(this);
    }

    private void setupNavigation(NavigationView mNavigationView) {
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                LogUtil.i(TAG,"click menuItem : " + menuItem.getItemId());
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_splash,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        LogUtil.i(TAG,"click item Id : " + item.getItemId());
        switch (item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.id_to_main:
                Intent mainIntent = new Intent(NavigationViewActivity.this, MainActivity.class);
                startActivity(mainIntent);
                overridePendingTransition(R.anim.left_in, R.anim.left_out);
                break;
            case R.id.id_to_list:
                Intent listIntent = new Intent(NavigationViewActivity.this, CallSmsSafeActivity.class);
                startActivity(listIntent);
                overridePendingTransition(R.anim.left_in, R.anim.left_out);
                break;
        }
    }
}
