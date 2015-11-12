package com.hedan.mobilesafe.ui;

import android.os.Bundle;
import android.widget.ListView;

import com.hedan.mobilesafe.R;
import com.hedan.mobilesafe.adapter.CommonAdapter;
import com.hedan.mobilesafe.adapter.ViewHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2015/11/12.
 */
public class CallSmsSafeActivity extends ToolbarActivity {
    private ListView ll;
    private List<String> mDatas = new ArrayList<String>(Arrays.asList("Hello","World","Welcome"));
    private CommonAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.call_sms_safe_main);
        ll = (ListView) findViewById(R.id.id_black_list);
        ll.setAdapter(mAdapter = new CommonAdapter<String>(getApplicationContext(),mDatas,R.layout.call_sms_safe_list_item){
            @Override
            public void convert(ViewHolder helper, String item) {
                helper.setText(R.id.id_safe_item,item);
            }
        });

        getSupportActionBar().setTitle("通讯卫士");
    }
}
