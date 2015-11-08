package com.hedan.mobilesafe.util;

/**
 * Created by Administrator on 2015/10/28.
 */
public interface HttpCallbackListener {
    void onFinish(Object response) throws Exception;
    void onError(Exception e);
}
