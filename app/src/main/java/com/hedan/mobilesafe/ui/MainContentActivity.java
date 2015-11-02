package com.hedan.mobilesafe.ui;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.hedan.mobilesafe.R;
import com.hedan.mobilesafe.fragment.ContentFragment;
import com.hedan.mobilesafe.fragment.MainFragment;
import com.hedan.mobilesafe.fragment.MessageFragment;

/**
 * Created by Administrator on 2015/11/2.
 */
public class MainContentActivity extends Activity implements View.OnClickListener{

    private MainFragment mainFragment;
    private MessageFragment messageFragment;
    private ContentFragment contentFragment;

    private View mainLayout;
    private View messageLayout;
    private View contentLayout;

    private ImageView iv_main;
    private ImageView iv_message;
    private ImageView iv_content;

    private TextView tv_main;
    private TextView tv_message;
    private TextView tv_content;

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main_screen);
        initView();
        fragmentManager = getFragmentManager();
        setTabSelection(0);
        
    }

    private void setTabSelection(int i) {
        clearTabSelection();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragment(transaction);
        switch (i){
            case 0:
                iv_message.setImageResource(R.drawable.message_selected);
                tv_message.setTextColor(Color.WHITE);
                if(messageFragment == null){
                    messageFragment = new MessageFragment();
                    transaction.add(R.id.fl_content,messageFragment);
                }else{
                    transaction.show(messageFragment);
                }
                break;
            case 1:
                iv_content.setImageResource(R.drawable.contacts_selected);
                tv_content.setTextColor(Color.WHITE);
                if(contentFragment == null){
                    contentFragment = new ContentFragment();
                    transaction.add(R.id.fl_content,contentFragment);
                }else{
                    transaction.show(contentFragment);
                }
                break;
            case 2:
                iv_main.setImageResource(R.drawable.setting_selected);
                tv_main.setTextColor(Color.WHITE);
                if(mainFragment == null){
                    mainFragment = new MainFragment();
                    transaction.add(R.id.fl_content,mainFragment);
                }else{
                    transaction.show(mainFragment);
                }
                break;
            default:
                break;
        }
        transaction.commit();
    }

    private void hideFragment(FragmentTransaction transaction) {
        if(messageFragment != null)
            transaction.hide(messageFragment);
        if(contentFragment != null)
            transaction.hide(contentFragment);
        if(mainFragment != null)
            transaction.hide(mainFragment);
    }

    private void clearTabSelection() {
        iv_message.setImageResource(R.drawable.message_unselected);
        tv_message.setTextColor(Color.parseColor("#82858b"));
        iv_content.setImageResource(R.drawable.contacts_unselected);
        tv_content.setTextColor(Color.parseColor("#82858b"));
        iv_main.setImageResource(R.drawable.setting_unselected);
        tv_main.setTextColor(Color.parseColor("#82858b"));
    }

    private void initView() {
        messageLayout = findViewById(R.id.rl_msg_layout);
        mainLayout = findViewById(R.id.rl_main_layout);
        contentLayout = findViewById(R.id.rl_content_layout);

        iv_main = (ImageView) findViewById(R.id.iv_main);
        iv_content = (ImageView) findViewById(R.id.iv_content);
        iv_message = (ImageView) findViewById(R.id.iv_msg);

        tv_main = (TextView) findViewById(R.id.tv_main);
        tv_content = (TextView) findViewById(R.id.tv_content);
        tv_message = (TextView) findViewById(R.id.tv_msg);

        messageLayout.setOnClickListener(this);
        mainLayout.setOnClickListener(this);
        contentLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_msg_layout:
                setTabSelection(0);
                break;
            case R.id.rl_content_layout:
                setTabSelection(1);
                break;
            case R.id.rl_main_layout:
                setTabSelection(2);
                break;
            default:
                break;
        }
    }

    
}
