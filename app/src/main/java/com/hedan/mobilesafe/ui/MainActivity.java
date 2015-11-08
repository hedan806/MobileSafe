package com.hedan.mobilesafe.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.hedan.mobilesafe.R;
import com.hedan.mobilesafe.adapter.MainUIAdapter;
import com.hedan.mobilesafe.util.LogUtil;

/**
 * Created by Administrator on 2015/10/20.
 */
public class MainActivity extends ToolbarActivity{

    private static final String TAG = "MainActivity";

    private GridView gv_main;

    private MainUIAdapter adapter;
    private SharedPreferences sp ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_fragment);

        sp = this.getSharedPreferences("config", Context.MODE_APPEND);

        gv_main = (GridView) this.findViewById(R.id.gv_main);
        adapter = new MainUIAdapter(this);
        gv_main.setAdapter(adapter);
        gv_main.setOnItemClickListener(new ItemOnClickListener());
        gv_main.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view, int position, long id) {
                if(position == 0){
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("设置标题");
                    builder.setMessage("请输入要更改的标题名称");
                    final EditText et_title = new EditText(MainActivity.this);
                    et_title.setHint("请输入文本值");
                    builder.setView(et_title);
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String title = et_title.getText().toString().trim();
                            if ("".equals(title)) {
                                Toast.makeText(getApplicationContext(), "标题内容不能为空", Toast.LENGTH_SHORT).show();
                                return;
                            } else {
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putString("loas_name",title);
                                editor.commit();
                                TextView tv_title = (TextView) view.findViewById(R.id.tv_main_name);
                                tv_title.setText(title);
                            }
                        }
                    });

                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.create().show();
                }
                return true;
            }
        });
    }

    private class ItemOnClickListener implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.i(TAG,"点击了" + position );
            switch (position){
                case 0 : //手机防盗
                    Log.i(TAG,"进入手机防盗界面");
                    Intent intent = new Intent(MainActivity.this,LostProtecteActivity.class);
                    startActivity(intent);
                    break;
                case 7 :
                    LogUtil.i(TAG,"进入高级工具");
                    Intent aToolsIntent = new Intent(MainActivity.this,AtoolsActivity.class);
                    startActivity(aToolsIntent);
                    overridePendingTransition(R.anim.left_in,R.anim.left_out);
                    break;
                case 9 ://聊天界面测试
                    Intent chatIntent = new Intent(MainActivity.this,ChatActivity.class);
                    startActivity(chatIntent);
                    break;
                case 10 ://微信UI
                    LogUtil.i(TAG,"进入微信UI界面1");
                    Intent wxIntent = new Intent(MainActivity.this,WXActivity.class);
                    startActivity(wxIntent);
                    LogUtil.i(TAG,"进入微信UI界面2");
                    break;
            }
        }
    }

    @Override
    public void onCreateCustomToolbar(Toolbar toolbar) {
        super.onCreateCustomToolbar(toolbar);
        getSupportActionBar().setTitle("手机安全卫士");
    }
}
