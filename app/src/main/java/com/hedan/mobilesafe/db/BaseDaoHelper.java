package com.hedan.mobilesafe.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.hedan.dao.BlackNumberDao;
import com.hedan.dao.DaoMaster;

/**
 * Created by Administrator on 2015/11/12.
 */
public class BaseDaoHelper extends DaoMaster.OpenHelper{

    public BaseDaoHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (oldVersion){
            case 1:
                BlackNumberDao.createTable(db,true);
                break;

        }
    }
}
