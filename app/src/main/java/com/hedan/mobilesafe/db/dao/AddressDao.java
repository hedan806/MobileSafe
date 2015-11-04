package com.hedan.mobilesafe.db.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Administrator on 2015/11/4.
 */
public class AddressDao {

    private Context context;

    public AddressDao(Context context) {
        this.context = context;
    }

    public static SQLiteDatabase getAddress(String path){
        return SQLiteDatabase.openDatabase(path,null,SQLiteDatabase.OPEN_READONLY);
    }

}
