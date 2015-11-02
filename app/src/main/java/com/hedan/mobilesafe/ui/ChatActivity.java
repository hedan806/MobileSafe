package com.hedan.mobilesafe.ui;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.hedan.mobilesafe.R;
import com.hedan.mobilesafe.adapter.MsgAdapter;
import com.hedan.mobilesafe.domain.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/1.
 */
public class ChatActivity extends Activity {

    private ListView lv_msg;
    private EditText et_msg;
    private Button send;
    private MsgAdapter msgAdapter;
    private List<Message> msgList = new ArrayList<Message>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.chat_main);
        initMsg();//初始化信息

        msgAdapter = new MsgAdapter(ChatActivity.this,R.layout.msg_item,msgList);
        et_msg = (EditText) this.findViewById(R.id.et_input_msg);
        send = (Button) this.findViewById(R.id.bt_send);
        lv_msg = (ListView) this.findViewById(R.id.lv_chat_msg);
        lv_msg.setAdapter(msgAdapter);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = et_msg.getText().toString().trim();
                if(!TextUtils.isEmpty(content)){
                    Message sendMsg = new Message(content,Message.TYPE_SEND);
                    msgList.add(sendMsg);
                    msgAdapter.notifyDataSetChanged();//当有消息时，刷新ListView
                    lv_msg.setSelection(msgList.size());//讲ListView定位到最后一行
                    et_msg.setText("");//清空输入框
                }
            }
        });
    }

    private void initMsg() {
        Message m1 = new Message("哇偶 man",Message.TYPE_RECE);
        Message m2 = new Message("hei woman",Message.TYPE_SEND);
        Message m3 = new Message("i am ww d dlkjsdkfj ",Message.TYPE_RECE);
        Message m4 = new Message("asdf asdfasdf s",Message.TYPE_RECE);
        msgList.add(m1);
        msgList.add(m2);
        msgList.add(m3);
        msgList.add(m4);
    }
}
