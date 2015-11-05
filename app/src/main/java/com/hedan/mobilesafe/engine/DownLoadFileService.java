package com.hedan.mobilesafe.engine;

import android.app.ProgressDialog;

import com.hedan.mobilesafe.util.LogUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2015/10/20.
 */
public class DownLoadFileService {

    private static final String TAG = DownLoadFileService.class.getSimpleName();

    public File getFile(String path,String filepath,ProgressDialog pd) throws Exception{
        LogUtil.i(TAG,"path : " + path);
        URL url = new URL(path);
//        LogUtil.i(TAG,"url : " + url.getPath());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//        LogUtil.i(TAG,"conn : " + conn.getResponseCode());
        conn.setRequestMethod("GET");
//        LogUtil.i(TAG, "code:" + conn.getResponseCode());
        conn.setConnectTimeout(3000);
        conn.setReadTimeout(3000);
        if(conn.getResponseCode() == 200){
            pd.setMax(conn.getContentLength());
            InputStream is = conn.getInputStream();
            File file = new File(filepath);
            FileOutputStream fos = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len = 0;
            int process = 0;
            while ((len = is.read(buf)) != -1){
                fos.write(buf,0 ,len);
                process += len;
                LogUtil.i(TAG,"len : " + len);
                pd.setProgress(process);
                Thread.sleep(10);
            }
            fos.flush();
            fos.close();
            is.close();
            return file;
        }
        LogUtil.i(TAG,"return null");
        return null;
    }

}
