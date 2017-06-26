package com.example.h_dj.news;

import android.app.Application;
import android.util.Log;

import com.example.h_dj.news.bean.User;
import com.tencent.smtt.sdk.QbSdk;

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
        initX5();
    }

    /**
     * 初始化腾讯x5内核WebView
     */
    private void initX5() {
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。

        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                // TODO Auto-generated method stub
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.d("app", " onViewInitFinished is " + arg0);
            }
            @Override
            public void onCoreInitFinished() {
                // TODO Auto-generated method stub
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), cb);
    }


    /**
     * 判断用户是已否登陆
     *
     * @return
     */
    public boolean checkLogin() {
        mUser = BmobUser.getCurrentUser(User.class);
        return mUser != null;
    }


}
