package com.hedan.mobilesafe.util;

import android.app.ProgressDialog;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2015/10/28.
 */
public class HttpUtil {

    /**
     * 字符串类型
     */
    public static final int STRING = 1;
    /**
     * 文件类型
     */
    public static final int FILE = 2;

    /**
     * 请求String
     * @param address
     * @param listener
     */
    public static void sendHttpRequestStr(final String address,final HttpCallbackListener listener){
        sendHttpRequest(address,STRING,null,null,listener);
    }

    /**
     * 请求File
     * @param address
     * @param filepath
     * @param pd
     * @param listener
     */
    public static void sendHttpRequestFile(final String address,String filepath,ProgressDialog pd,final HttpCallbackListener listener){
        sendHttpRequest(address,FILE,filepath,pd,listener);
    }

    private static void sendHttpRequest(final String address,final int type,final String filepath,final ProgressDialog pd,final HttpCallbackListener listener){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection conn = null;
                try {
                    URL url = new URL(address);
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setConnectTimeout(5000);
                    conn.setReadTimeout(5000);
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    if(pd != null){
                        pd.setMessage("正在下载...");
                        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    }
                    InputStream is = conn.getInputStream();
                    Object result = null;
                    switch (type){
                        case STRING:
                            BufferedReader br = new BufferedReader(new InputStreamReader(is));
                            StringBuilder response = new StringBuilder();
                            String line ;
                            while((line = br.readLine()) != null){
                                response.append(line);
                            }
                            result = response.toString();
                            break;
                        case FILE:
                            File file = new File(filepath);
                            FileOutputStream fos = new FileOutputStream(file);
                            byte[] buf = new byte[1024];
                            int len = 0;
                            int process = 0;
                            while ((len = is.read(buf)) != -1){
                                fos.write(buf,0 ,len);
                                process += len;
                                pd.setProgress(process);
                                Thread.sleep(10);
                            }
                            fos.flush();
                            fos.close();
                            is.close();
                            result = file;
                            break;
                    }

                    if(listener != null){
                        listener.onFinish(result);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    if(listener != null){
                        listener.onError(e);
                    }
                }finally {
                    if(pd != null){
                        pd.dismiss();
                    }
                    if(conn != null){
                        conn.disconnect();
                    }
                }
            }
        }).start();
    }
}
