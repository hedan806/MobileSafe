package com.hedan.mobilesafe;

import android.test.AndroidTestCase;

import com.hedan.mobilesafe.domain.ContactInfo;
import com.hedan.mobilesafe.engine.ContactInfoService;

import java.util.List;

/**
 * Created by Administrator on 2015/10/25.
 */
public class TestGetContacts extends AndroidTestCase {

    public void testGetContacts() throws Exception{
        ContactInfoService service = new ContactInfoService(getContext());
        List<ContactInfo> infos = service.getContactInfos();
        for (ContactInfo info : infos){
            System.out.println(info.getName() + " : " + info.getPhone());
        }
    }

}
