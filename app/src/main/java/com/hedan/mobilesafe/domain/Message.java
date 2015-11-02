package com.hedan.mobilesafe.domain;

/**
 * Created by Administrator on 2015/11/1.
 */
public class Message {
    /**
     * 消息类型--接收
     */
    public static final int TYPE_RECE = 0;
    /**
     * 消息类型--发送
     */
    public static final int TYPE_SEND = 1;

    private String content;
    private int type;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Message(String content, int type) {
        this.content = content;
        this.type = type;
    }
}
