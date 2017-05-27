package com.example.h_dj.news.bean;

import cn.bmob.v3.BmobUser;

/**
 * Created by H_DJ on 2017/5/27.
 *
 * 用户
 */

public class User extends BmobUser {

    private String user_icon;
    private String user_from;
    private String user_name;
    private String user_password;

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

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }
}
