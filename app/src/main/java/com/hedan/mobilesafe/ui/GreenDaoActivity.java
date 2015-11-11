package com.hedan.mobilesafe.ui;

import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.widget.EditText;

import com.hedan.dao.DaoMaster;
import com.hedan.dao.DaoSession;

/**
 * Created by Administrator on 2015/11/11.
 */
public class GreenDaoActivity extends ListActivity {
    private SQLiteDatabase db;
    private EditText editText;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private Cursor cursor;
    private static final String TAG = GradientDrawable.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
