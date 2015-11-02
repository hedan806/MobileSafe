package com.hedan.mobilesafe.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.hedan.mobilesafe.R;
import com.hedan.mobilesafe.adapter.MainUIAdapter;
import com.hedan.mobilesafe.ui.ChatActivity;
import com.hedan.mobilesafe.ui.LostProtecteActivity;

/**
 * Created by Administrator on 2015/11/2.
 */
public class MainFragment extends Fragment {
    private static final String TAG = "MainFragment";
    private GridView gv_main;

    private MainUIAdapter adapter;
    private SharedPreferences sp ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mainLayout = inflater.inflate(R.layout.main_fragment,container,false);
        return mainLayout;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sp = getActivity().getSharedPreferences("config", Context.MODE_APPEND);
        gv_main = (GridView) getActivity().findViewById(R.id.gv_main);
        adapter = new MainUIAdapter(getActivity());
        gv_main.setAdapter(adapter);
        gv_main.setOnItemClickListener(new ItemOnClickListener());
        gv_main.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view, int position, long id) {
                if(position == 0){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("设置标题");
                    builder.setMessage("请输入要更改的标题名称");
                    final EditText et_title = new EditText(getActivity());
                    et_title.setHint("请输入文本值");
                    builder.setView(et_title);
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String title = et_title.getText().toString().trim();
                            if ("".equals(title)) {
                                Toast.makeText(getActivity().getApplicationContext(), "标题内容不能为空", Toast.LENGTH_SHORT).show();
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
            Log.i(TAG, "点击了" + position + ",");
            switch (position){
                case 0 : //手机防盗
                    Log.i(TAG,"进入手机防盗界面");
                    Intent intent = new Intent(getActivity(),LostProtecteActivity.class);
                    startActivity(intent);
                    break;
                case 9://聊天界面测试
                    Intent chatIntent = new Intent(getActivity(),ChatActivity.class);
                    startActivity(chatIntent);
                    break;
            }
        }
    }
}
