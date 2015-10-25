package com.hedan.mobilesafe.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Administrator on 2015/10/24.
 */
public class MD5Encoder {
    public static String encode(String pwd){
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] result = digest.digest(pwd.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0 ; i < result.length;i++){
                String s = Integer.toHexString(0xff&result[i]);
                if(s.length() == 1){
                    sb.append("0" + s);
                }else{
                    sb.append(s);
                }
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public static String encode(String pwd,int size){
        String str = encode(pwd);
        for (int i = 0 ; i < size;i++){
            str = encode(str);
        }
        return str;
    }

}
