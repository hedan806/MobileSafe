package com.hedan.mobilesafe;

import android.test.AndroidTestCase;
import android.util.Log;

import com.hedan.dao.BlackNumber;
import com.hedan.dao.BlackNumberDao;
import com.hedan.mobilesafe.db.dao.BlackNumberDaoHelper;
import com.hedan.mobilesafe.util.LogUtil;
import com.hedan.mobilesafe.util.MD5Encoder;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/10/24.
 */
public class TestBlackNumberDaoHelper extends AndroidTestCase{

    private static final String TAG = TestBlackNumberDaoHelper.class.getSimpleName();

    public void testBlackNumberDaoAdd(){
        BlackNumberDaoHelper helper = BlackNumberDaoHelper.getInstance(getContext());
        BlackNumber blackNumber = helper.addData(new BlackNumber(null, "123143546","aaa",true,false, new Date()));
        LogUtil.i(TAG,blackNumber.getId() + "添加的ID");
    }

    public void testFindAll(){
        BlackNumberDaoHelper helper = BlackNumberDaoHelper.getInstance(getContext());
        List<BlackNumber> all = helper.getAllData();
        for (BlackNumber blackNumber : all) {
            LogUtil.i(TAG,blackNumber.getId() + " : " + blackNumber.getPhone() + " time : " + blackNumber.getCtime());
        }
    }

    public void testUpdate(){
        BlackNumberDaoHelper helper = BlackNumberDaoHelper.getInstance(getContext());
        BlackNumber blackNumber = helper.getDataById("1");
        LogUtil.i(TAG,blackNumber.getId() + " : " + blackNumber.getPhone());
        blackNumber.setPhone("111111111");
        helper.addData(blackNumber);
        LogUtil.i(TAG,blackNumber.getId() + " : " + blackNumber.getPhone());
    }

}
