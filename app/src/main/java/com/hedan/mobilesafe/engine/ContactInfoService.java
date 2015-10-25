package com.hedan.mobilesafe.engine;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import com.hedan.mobilesafe.domain.ContactInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/10/25.
 */
public class ContactInfoService {

    private static final String TAG = "ContactInfoService";
    private Context context;

    public ContactInfoService(Context context) {
        this.context = context;
    }

    public List<ContactInfo> getContactInfos(){
        ContentResolver resolver = context.getContentResolver();
        List<ContactInfo> infos = new ArrayList<ContactInfo>();
        ContactInfo info ;
        //1.获取联系ID
        //2.根据联系ID获取联系人姓名
        //3.根据联系人ID的type，获取联系人的电话
        Uri uri = ContactsContract.RawContacts.CONTENT_URI;
//        Log.i(TAG,"uri:" + uri);
        Cursor cursor = resolver.query(uri, null, null, null, null);
        while (cursor.moveToNext()){
            info = new ContactInfo();
            String id = cursor.getString(cursor.getColumnIndex(ContactsContract.RawContacts._ID));
            //Log.i(TAG,"联系人ID：" + id);
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.RawContacts.DISPLAY_NAME_PRIMARY));
            //Log.i(TAG, "联系人名称：" + name);
            info.setName(name);
            Cursor data = resolver.query(ContactsContract.Data.CONTENT_URI, null, ContactsContract.Data.RAW_CONTACT_ID + "=?", new String[]{id}, null);
            while (data.moveToNext()){
                String data1 = data.getString(data.getColumnIndex(ContactsContract.Data.DATA1));
                String type = data.getString(data.getColumnIndex(ContactsContract.Data.MIMETYPE));
                //Log.i(TAG,"data1:" + data1 + " -- " + "type:" + type);
                if(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE.equals(type)){
                    info.setPhone(data1);
                }
            }
            infos.add(info);
            info = null;
        }
        return infos;
    }

}
