package com.hedan.mobilesafe.db.dao;

import android.content.Context;
import android.text.TextUtils;

import com.hedan.dao.BlackNumber;
import com.hedan.dao.BlackNumberDao;
import com.hedan.mobilesafe.BaseApplication;
import com.hedan.mobilesafe.db.BaseDaoHelperInterface;
import com.hedan.mobilesafe.util.LogUtil;

import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by Administrator on 2015/11/12.
 */
public class BlackNumberDaoHelper implements BaseDaoHelperInterface<BlackNumber> {
    private static final String TAG = BlackNumberDaoHelper.class.getSimpleName();
    private static BlackNumberDaoHelper instance;
    private BlackNumberDao dao;

    public BlackNumberDaoHelper(Context context) {
        LogUtil.i(TAG,"BlackNumberDaoHelper Constructor");
        dao = BaseApplication.getDaoSession(context).getBlackNumberDao();
        QueryBuilder.LOG_SQL = true;
        QueryBuilder.LOG_VALUES = true;
    }

    public static BlackNumberDaoHelper getInstance(Context context){
        if(instance == null){
            LogUtil.i(TAG,"BlackNumberDaoHelper instance is Null !");
            instance = new BlackNumberDaoHelper(context);
        }
        return instance;
    }

    public BlackNumberDao getDaoInstance(){
        return dao ;
    }

    @Override
    public BlackNumber addData(BlackNumber blackNumber) {
        if(dao != null && blackNumber != null){
            long id = dao.insertOrReplace(blackNumber);
            return getDataById(String.valueOf(id));
        }
        return null;
    }

    @Override
    public void deleteData(String id) {
        if(dao != null && !TextUtils.isEmpty(id)){
            dao.deleteByKey(Long.valueOf(id));
        }
    }

    @Override
    public BlackNumber getDataById(String id) {
        if(dao != null && !TextUtils.isEmpty(id)){
            return dao.load(Long.valueOf(id));
        }
        return null;
    }

    @Override
    public List getAllData() {
        if(dao != null){
            return dao.loadAll();
        }
        return null;
    }

    @Override
    public boolean hasKey(String id) {
        return false;
    }

    @Override
    public long getTotalCount() {
        return 0;
    }

    @Override
    public void deleteAll() {

    }
}
