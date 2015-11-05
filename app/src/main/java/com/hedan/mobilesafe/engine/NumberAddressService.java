package com.hedan.mobilesafe.engine;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.hedan.mobilesafe.db.dao.AddressDao;
import com.hedan.mobilesafe.util.LogUtil;

/**
 * Created by Administrator on 2015/11/4.
 */
public class NumberAddressService {

    private static final String TAG = NumberAddressService.class.getSimpleName();

    private static SQLiteDatabase db;
    private static Cursor cursor;

    /**
     * 查询手机号码归属地
     * @param number
     * @return
     */
    public static String getAddress(String number){
        String regex = "^1[34578]\\d{9}$";
        String address = number;
        if(number.matches(regex)){
            LogUtil.i(TAG,"输入的为手机号码");
            db = AddressDao.getAddress(Environment.getExternalStorageDirectory().getPath() + "address.db");
            if(db.isOpen()){
                cursor = db.rawQuery("select city from info where mobileprefix=?", new String[]{number.substring(0, 7)});
                if(cursor.moveToNext()){
                    address = cursor.getString(0);
                }
                cursor.close();
                db.close();
            }
        }else{
            LogUtil.i(TAG,"输入的是固定号码");
            int len = number.length();
            switch (len){
                case 4:
                    address = "模拟器";
                    break;
                case 7:
                case 8:
                    address = "本地号码";
                    break;
                case 10://3位区号，7位电话号码
                    db = AddressDao.getAddress(Environment.getExternalStorageDirectory().getPath() + "address.db");
                    if(db.isOpen()) {
                        cursor = db.rawQuery("select city from info where area=? limit 1", new String[]{number.substring(0, 3)});
                        if (cursor.moveToNext()) {
                            address = cursor.getString(0);
                        }
                        cursor.close();
                        db.close();
                    }
                    break;
                case 11://4位区号，7位电话号码 或者 3位区号，8位电话号码
                    db = AddressDao.getAddress(Environment.getExternalStorageDirectory().getPath() + "address.db");
                    if(db.isOpen()) {
                        cursor = db.rawQuery("select city from info where area=? limit 1", new String[]{number.substring(0, 3)});
                        if (cursor.moveToNext()) {
                            address = cursor.getString(0);
                        }
                        Cursor cursor2 = db.rawQuery("select city from info where area=? limit 1", new String[]{number.substring(0, 4)});
                        if (cursor.moveToNext()) {
                            address = cursor2.getString(0);
                        }
                        cursor.close();
                        cursor2.close();
                        db.close();
                    }
                    break;
                case 12 ://4位区号，8位电话号码
                    db = AddressDao.getAddress(Environment.getExternalStorageDirectory().getPath() + "address.db");
                    if(db.isOpen()) {
                        cursor = db.rawQuery("select city from info where area=? limit 1", new String[]{number.substring(0, 4)});
                        if (cursor.moveToNext()) {
                            address = cursor.getString(0);
                        }
                        cursor.close();
                        db.close();
                    }
                    break;
            }
        }
        return address;
    }

}
