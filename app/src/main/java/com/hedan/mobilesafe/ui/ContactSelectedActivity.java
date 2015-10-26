package com.hedan.mobilesafe.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.hedan.mobilesafe.R;
import com.hedan.mobilesafe.domain.ContactInfo;
import com.hedan.mobilesafe.engine.ContactInfoService;

import java.util.List;

/**
 * Created by Administrator on 2015/10/25.
 */
public class ContactSelectedActivity extends Activity {

    private static final String TAG = "ContactSelectedActivity";
    private ListView lv;
    private List<ContactInfo> infos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_select);

        ContactInfoService service = new ContactInfoService(this);
        infos = service.getContactInfos();
        Log.i(TAG,"infos:" + infos);
        lv = (ListView) this.findViewById(R.id.lv_contact_select);
        lv.setAdapter(new ContactSelecteAdapter());

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ContactInfo info = infos.get(position);
                Intent intent = new Intent();
                intent.putExtra("number",info.getPhone());
                setResult(0,intent);
                finish();
            }
        });
    }

    private class ContactSelecteAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return infos.size();
        }

        @Override
        public Object getItem(int position) {
            return infos.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Log.i(TAG, "getView invoke:" + position);
            ContactInfo info = infos.get(position);
            LinearLayout ll = new LinearLayout(ContactSelectedActivity.this);
            ll.setOrientation(LinearLayout.VERTICAL);
            TextView tv_name = new TextView(ContactSelectedActivity.this);
            TextView tv_phone = new TextView(ContactSelectedActivity.this);
            tv_name.setText("姓名："+info.getName());
            tv_phone.setText("电话：" + info.getPhone());

            ll.addView(tv_name);
            ll.addView(tv_phone);
            return ll;
        }
    }
}
