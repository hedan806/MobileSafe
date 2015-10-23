package com.hedan.mobilesafe.engine;

import android.content.Context;

import com.hedan.mobilesafe.domain.UpdateInfo;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Administrator on 2015/10/19.
 */
public class UpdateInfoService {

    private Context context;

    public UpdateInfoService(Context context) {
        this.context = context;
    }

    public UpdateInfo getUpdateInfo(int urlid) throws Exception{

        String path = context.getResources().getString(urlid);
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5000);
        conn.setRequestMethod("GET");
        InputStream is = conn.getInputStream();
        return UpdateInfoParser.getUpdateInfo(is);
    }

}
