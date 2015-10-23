package com.hedan.mobilesafe;

import android.test.AndroidTestCase;
import android.test.InstrumentationTestCase;

import com.hedan.mobilesafe.domain.UpdateInfo;
import com.hedan.mobilesafe.engine.UpdateInfoService;

/**
 * Created by Administrator on 2015/10/19.
 */
public class TestGetUpdateInfo extends AndroidTestCase {

    public void testGetInfo() throws Exception{
        UpdateInfoService service = new UpdateInfoService(getContext());
        UpdateInfo info = service.getUpdateInfo(R.string.updateurl);
        assertEquals("2.0",info.getVersion());
    }
}
