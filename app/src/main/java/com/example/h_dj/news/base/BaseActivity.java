package com.example.h_dj.news.base;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.h_dj.news.App;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by H_DJ on 2017/5/15.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private Unbinder unbinder;
    private ProgressDialog progressDialog;
    private Toast mToast;
    protected App mApp;
    private InputMethodManager manager;

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
        mApp = (App) getApplication();
        manager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
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

    public void hiddenInputMethodManager(View view) {
        manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 显示Toast
     *
     * @param msg
     */
    public void showToast(String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(getApplicationContext(), "" + msg, Toast.LENGTH_SHORT);
        }
        mToast.setText(msg);
        mToast.show();
    }

    /**
     * 初始化toolbar
     */
    protected void initToolbar(Toolbar mToolbar, String title) {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }


}
