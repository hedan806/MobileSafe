package com.hedan.mobilesafe;

import android.app.Application;
import android.content.Context;
import android.provider.SyncStateContract;

import com.hedan.dao.DaoMaster;
import com.hedan.dao.DaoSession;
import com.hedan.mobilesafe.util.Constants;

/**
 * Created by Administrator on 2015/11/11.
 */
public class BaseApplication extends Application {
    private static BaseApplication mInstance;
    private static DaoMaster daoMaster;
    private static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        if (mInstance == null)
            mInstance = this;
    }

    /**
     * 取得DaoMaster
     *
     * @param context
     * @return
     */
    public static DaoMaster getDaoMaster(Context context) {
        if (daoMaster == null) {
            DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(context, Constants.DB_NAME, null);
            daoMaster = new DaoMaster(helper.getWritableDatabase());
        }
        return daoMaster;
    }

    /**
     * 取得DaoSession
     *
     * @param context
     * @return
     */
    public static DaoSession getDaoSession(Context context) {
        if (daoSession == null) {
            if (daoMaster == null) {
                daoMaster = getDaoMaster(context);
            }
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }
}
