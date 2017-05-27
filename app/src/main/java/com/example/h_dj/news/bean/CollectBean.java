package com.example.h_dj.news.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by H_DJ on 2017/5/27.
 */

public class CollectBean extends BmobObject {

    private String collectTitle;
    private String collectUrl;
    private String collectTime;
    private String userId;

    public String getCollectTitle() {
        return collectTitle;
    }

    public void setCollectTitle(String collectTitle) {
        this.collectTitle = collectTitle;
    }

    public String getCollectUrl() {
        return collectUrl;
    }

    public void setCollectUrl(String collectUrl) {
        this.collectUrl = collectUrl;
    }

    public String getCollectTime() {
        return collectTime;
    }

    public void setCollectTime(String collectTime) {
        this.collectTime = collectTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
