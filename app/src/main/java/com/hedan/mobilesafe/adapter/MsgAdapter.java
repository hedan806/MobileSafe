package com.hedan.mobilesafe.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hedan.mobilesafe.R;
import com.hedan.mobilesafe.domain.Message;

import java.util.List;

/**
 * Created by Administrator on 2015/11/1.
 */
public class MsgAdapter extends ArrayAdapter<Message> {

    private int resId;

    public MsgAdapter(Context context, int textViewResourceId, List<Message> objects) {
        super(context,  textViewResourceId, objects);
        resId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Message msg = getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resId,null);
            viewHolder = new ViewHolder();
            viewHolder.leftLayout = (LinearLayout) view.findViewById(R.id.ll_msg_left);
            viewHolder.rightLayout = (LinearLayout) view.findViewById(R.id.ll_msg_right);
            viewHolder.leftMsg = (TextView) view.findViewById(R.id.tv_msg_left);
            viewHolder.rightMsg = (TextView) view.findViewById(R.id.tv_msg_right);
            view.setTag(viewHolder);
        }else{
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        if(msg.getType() == Message.TYPE_RECE){
            //接收消息  显示左边的ll
            viewHolder.leftLayout.setVisibility(View.VISIBLE);
            viewHolder.rightLayout.setVisibility(View.GONE);
            viewHolder.leftMsg.setText(msg.getContent());
        }else{
            viewHolder.rightLayout.setVisibility(View.VISIBLE);
            viewHolder.leftLayout.setVisibility(View.GONE);
            viewHolder.rightMsg.setText(msg.getContent());
        }
        return view;
    }

    private class ViewHolder {
        LinearLayout leftLayout;
        LinearLayout rightLayout;
        TextView leftMsg;
        TextView rightMsg;
    }
}
