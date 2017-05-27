package com.example.h_dj.news;

import android.app.Application;

import cn.bmob.v3.Bmob;

/**
 * Created by H_DJ on 2017/5/17.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //第一：默认初始化
        Bmob.initialize(this, "c98a0cb51ebcac2cf0ba29706006774b");
    }


}
