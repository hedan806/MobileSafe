package com.hedan.mobilesafe.domain;

/**
 * Created by Administrator on 2015/11/12.
 */
public class MainInfo {
    private String title;
    private int imgId;

    public MainInfo(String title, int imgId) {
        this.title = title;
        this.imgId = imgId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }
}
