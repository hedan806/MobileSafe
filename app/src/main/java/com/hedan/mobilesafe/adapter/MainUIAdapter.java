package com.hedan.mobilesafe.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hedan.mobilesafe.R;

/**
 * Created by Administrator on 2015/10/22.
 */
public class MainUIAdapter extends BaseAdapter {

    private Context context ;
    private LayoutInflater inflater ;
    private SharedPreferences sp;

    private static TextView tv_name;
    private static ImageView iv_icon;

    public MainUIAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.sp = context.getSharedPreferences("config",Context.MODE_PRIVATE);
    }

    private static String[] names = {
            "手机防盗"
            ,"通讯卫士"
            ,"软件管理"
            ,"任务管理"
            ,"上网管理"
            ,"手机杀毒"
            ,"系统优化"
            ,"高级工具"
            ,"设置中心"
            ,"聊天界面测试"
            ,"微信界面UI"};

    private static int[] icons = {
            R.drawable.icon1
            ,R.drawable.icon2
            ,R.drawable.icon3
            ,R.drawable.icon4
            ,R.drawable.icon5
            ,R.drawable.icon6
            ,R.drawable.icon7
            ,R.drawable.icon8
            ,R.drawable.icon9
            ,R.drawable.icon9
            ,R.drawable.icon7
    };

    @Override
    public int getCount() {
        return names.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.mainscreen_item, null);
        tv_name = (TextView) view.findViewById(R.id.tv_main_name);
        iv_icon = (ImageView) view.findViewById(R.id.iv_main_item);
        tv_name.setText(names[position]);
        iv_icon.setImageResource(icons[position]);
        if(position == 0){
            String title = sp.getString("loas_name",null);
            if(title != null){
                tv_name.setText(title);
            }
        }
        return view;
    }
}
