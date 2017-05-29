package com.example.h_dj.news.activity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.h_dj.news.bean.User;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bmob.v3.BmobUser;

/**
 * Created by H_DJ on 2017/5/15.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private Unbinder unbinder;
    private ProgressDialog progressDialog;
    protected User mUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getLayoutId() != 0) {
            setContentView(getLayoutId());
        }
        unbinder = ButterKnife.bind(this);
        init();
    }


    /**
     * 初始化方法
     */
    protected void init() {
    }

    /**
     * 布局id
     *
     * @return
     */
    protected abstract int getLayoutId();

    public void goTo(Class mClass) {
        goTo(mClass, null);
    }

    public void goTo(Class mClass, Bundle bundle) {
        Intent intent = new Intent(this, mClass);
        intent.putExtra("data", bundle);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
        super.onDestroy();
    }

    /**
     * 显示进度
     */
    public void showProgressDialog(String title, String msg) {
        progressDialog = ProgressDialog.show(this, title, msg, false);
    }

    /**
     * 隐藏进度
     */
    public void hiddenProgressDialog() {
        progressDialog.dismiss();
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

    /**
     * 初始化toolbar
     */
    protected void initToolbar(Toolbar mToolbar) {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
}
