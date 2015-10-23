package com.hedan.mobilesafe.domain;

/**
 * Created by Administrator on 2015/10/19.
 */
public class UpdateInfo {
    private String version;
    private String desc;
    private String apkurl;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getApkurl() {
        return apkurl;
    }

    public void setApkurl(String apkurl) {
        this.apkurl = apkurl;
    }
}
