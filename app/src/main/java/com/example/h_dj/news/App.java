package com.example.h_dj.news;

import android.app.Application;

import com.example.h_dj.news.bean.User;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;

/**
 * Created by H_DJ on 2017/5/17.
 */

public class App extends Application {

    public User mUser;
    @Override
    public void onCreate() {
        super.onCreate();
        //第一：默认初始化
        Bmob.initialize(this, "c98a0cb51ebcac2cf0ba29706006774b");
    }

    /**
     * 判断用户是已否登陆
     *
     * @return
     */
    public boolean checkLogin() {
        mUser = BmobUser.getCurrentUser(User.class);
        if (mUser != null) {
            return true;
        } else {
            return false;
        }
    }


}
