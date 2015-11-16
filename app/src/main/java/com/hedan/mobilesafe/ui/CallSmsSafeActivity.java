package com.hedan.mobilesafe.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hedan.dao.BlackNumber;
import com.hedan.mobilesafe.R;
import com.hedan.mobilesafe.adapter.CommonAdapter;
import com.hedan.mobilesafe.adapter.ViewHolder;
import com.hedan.mobilesafe.db.dao.BlackNumberDaoHelper;
import com.hedan.mobilesafe.decoration.DividerItemDecoration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/11/12.
 */
public class CallSmsSafeActivity extends ToolbarActivity implements View.OnClickListener {
    private RecyclerView rv;
    private HomeAdapter adapter;
    private Button id_add_black;
    private List<BlackNumber> mDatas;

    private BlackNumberDaoHelper blackDaoHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        blackDaoHelper = BlackNumberDaoHelper.getInstance(this);
        mDatas = blackDaoHelper.getAllData();
        if(mDatas.size() <= 0){
            setContentView(R.layout.call_sms_safe_main_null);
        }else{
            setContentView(R.layout.call_sms_safe_main);
            rv = (RecyclerView) findViewById(R.id.id_rv_black_list);
            rv.setLayoutManager(new LinearLayoutManager(this));
            rv.setAdapter(adapter = new HomeAdapter());
            rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));

            adapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
//                Toast.makeText(getApplicationContext(), "click position : " + position, Toast.LENGTH_LONG).show();
                }

                @Override
                public void onItemLongClick(View view, final int position) {
//                Toast.makeText(getApplicationContext(), "long click position : " + position, Toast.LENGTH_LONG).show();
                    final BlackNumber blackNumber = mDatas.get(position);
                    new AlertDialog.Builder(CallSmsSafeActivity.this)
                            .setTitle("提示")
                            .setMessage("是否删除此黑名单号码")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    blackDaoHelper.deleteData(String.valueOf(blackNumber.getId()));
                                    mDatas = blackDaoHelper.getAllData();
                                    adapter.notifyItemRemoved(position);
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .create()
                            .show();
                }
            });
        }
        id_add_black = (Button) findViewById(R.id.id_add_black_number);
        id_add_black.setOnClickListener(this);

        getSupportActionBar().setTitle("通讯卫士");
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

        private OnItemClickListener mOnItemClickListener;

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.mOnItemClickListener = onItemClickListener;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(CallSmsSafeActivity.this).inflate(R.layout.recycler_item, parent, false));

            return holder;
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            holder.tv.setText(mDatas.get(position).getPhone());
            if (mOnItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holder.getLayoutPosition();
                        mOnItemClickListener.onItemClick(holder.itemView, pos);
                    }
                });

                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        int pos = holder.getLayoutPosition();
                        mOnItemClickListener.onItemLongClick(holder.itemView, pos);
                        return true;
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }


        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tv;

            public MyViewHolder(View itemView) {
                super(itemView);
                tv = (TextView) itemView.findViewById(R.id.id_num);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_add_black_number:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("添加黑名单");
                final EditText et = new EditText(this);
                builder.setView(et);
                builder.setPositiveButton("添加", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String number = et.getText().toString().trim();
                        if (TextUtils.isEmpty(number)) {
                            Toast.makeText(getApplicationContext(), "黑名单号码不能为空", Toast.LENGTH_SHORT).show();
                        } else {
                            blackDaoHelper.addData(new BlackNumber(null, number, new Date()));
                            mDatas = blackDaoHelper.getAllData();
                            adapter.notifyItemInserted(mDatas.size());
                        }
                    }
                });

                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.create().show();
                break;
        }
    }
}
