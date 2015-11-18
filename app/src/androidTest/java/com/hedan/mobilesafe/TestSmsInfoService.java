package com.hedan.mobilesafe;

import android.test.AndroidTestCase;

import com.hedan.mobilesafe.domain.ContactInfo;
import com.hedan.mobilesafe.domain.SmsInfo;
import com.hedan.mobilesafe.engine.ContactInfoService;
import com.hedan.mobilesafe.engine.SmsInfoService;
import com.hedan.mobilesafe.util.LogUtil;

import java.util.List;

/**
 * Created by Administrator on 2015/10/25.
 */
public class TestSmsInfoService extends AndroidTestCase {

    private static final String TAG = TestSmsInfoService.class.getSimpleName();

    public void testGetSmsInfos() throws Exception{
        SmsInfoService service = new SmsInfoService(getContext());
        List<SmsInfo> infos = service.getSmsInfos();
        for(SmsInfo info : infos){
            LogUtil.i(TAG,info.toString());
        }
    }

}
