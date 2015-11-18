package com.hedan.mobilesafe.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.hedan.dao.BlackNumberDao;
import com.hedan.dao.DaoMaster;
import com.hedan.mobilesafe.util.LogUtil;

/**
 * Created by Administrator on 2015/11/12.
 */
public class BaseDaoHelper extends DaoMaster.OpenHelper{

    private static final String TAG = BaseDaoHelper.class.getSimpleName();

    public BaseDaoHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        LogUtil.i(TAG,"数据库更新。old:" + oldVersion + " /// new :" + newVersion);
        switch (oldVersion){
            case 1:
                LogUtil.i(TAG, "数据库版本1，创建表");
                BlackNumberDao.createTable(db,true);
                break;
            case 2:
                LogUtil.i(TAG,"数据库版本2");
                BlackNumberDao.createTable(db,false);
                break;

        }
    }
}
