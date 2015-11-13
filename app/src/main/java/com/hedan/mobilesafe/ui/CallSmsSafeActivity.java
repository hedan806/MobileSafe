package com.hedan.mobilesafe.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.hedan.dao.BlackNumber;
import com.hedan.mobilesafe.R;
import com.hedan.mobilesafe.adapter.CommonAdapter;
import com.hedan.mobilesafe.adapter.ViewHolder;
import com.hedan.mobilesafe.db.dao.BlackNumberDaoHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2015/11/12.
 */
public class CallSmsSafeActivity extends ToolbarActivity implements View.OnClickListener {
    private ListView ll;
    private CommonAdapter mAdapter;
    private Button id_add_black;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.call_sms_safe_main);

        ll = (ListView) findViewById(R.id.id_black_list);
        id_add_black = (Button) findViewById(R.id.id_add_black_number);

        id_add_black.setOnClickListener(this);

        BlackNumberDaoHelper blackHelper = BlackNumberDaoHelper.getInstance(this);
        List<BlackNumber> mDatas = blackHelper.getAllData();
        ll.setAdapter(mAdapter = new CommonAdapter<BlackNumber>(getApplicationContext(),mDatas,R.layout.call_sms_safe_list_item){
            @Override
            public void convert(ViewHolder helper, BlackNumber item) {
                helper.setText(R.id.id_safe_item,item.getPhone());
            }
        });

        getSupportActionBar().setTitle("通讯卫士");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.id_add_black_number:

                break;
        }
    }
}
