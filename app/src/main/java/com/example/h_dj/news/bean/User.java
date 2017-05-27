package com.example.h_dj.news.bean;

import cn.bmob.v3.BmobUser;

/**
 * Created by H_DJ on 2017/5/27.
 * <p>
 * 用户
 */

public class User extends BmobUser {

    private String user_icon;
    private String user_from;

    public String getUser_icon() {
        return user_icon;
    }

    public void setUser_icon(String user_icon) {
        this.user_icon = user_icon;
    }

    public String getUser_from() {
        return user_from;
    }

    public void setUser_from(String user_from) {
        this.user_from = user_from;
    }
}
