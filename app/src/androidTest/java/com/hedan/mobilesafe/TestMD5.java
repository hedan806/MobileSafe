package com.hedan.mobilesafe;

import android.test.AndroidTestCase;
import android.util.Log;

import com.hedan.mobilesafe.util.MD5Encoder;

/**
 * Created by Administrator on 2015/10/24.
 */
public class TestMD5 extends AndroidTestCase{

    private static final String TAG = "TestMD5";

    public void testMD5(){
        String str = MD5Encoder.encode("123456",100);
        Log.i(TAG,"100次加密后的值：" + str);
        String str1 = MD5Encoder.encode("123456");
        Log.i(TAG,"默认1次加密后的值：" + str1);
    }

}
