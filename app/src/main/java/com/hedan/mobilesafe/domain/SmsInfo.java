package com.hedan.mobilesafe.domain;

/**
 * Created by Administrator on 2015/11/18.
 */
public class SmsInfo {

    public static final String FILE_NAME = "/sms_backup.xml";
    private int id;
    private String address;
    private String body;
    private String date;
    /**
     * 短信类型：1、接收 2、发送
     */
    private int type;
    /**
     * 是否已读：0、未读 1、已读
     */
    private int read;

    public SmsInfo(int id, String address, String body, String date, int type, int read) {
        this.id = id;
        this.address = address;
        this.body = body;
        this.date = date;
        this.type = type;
        this.read = read;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getRead() {
        return read;
    }

    public void setRead(int read) {
        this.read = read;
    }
}
