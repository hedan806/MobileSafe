package com.hedan.mobilesafe.engine;

import android.app.ProgressDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2015/10/20.
 */
public class DownLoadFileService {

    public File getFile(String path,String filepath,ProgressDialog pd) throws Exception{
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(5000);
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
                pd.setProgress(process);
                Thread.sleep(500);
            }
            fos.flush();
            fos.close();
            is.close();
            return file;
        }
        return null;
    }

}
