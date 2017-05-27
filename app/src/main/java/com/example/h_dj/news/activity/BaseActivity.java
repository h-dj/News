package com.example.h_dj.news.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by H_DJ on 2017/5/15.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private Unbinder unbinder;

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
        Intent intent = new Intent(this, mClass);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();

    }
}
