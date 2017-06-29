package com.example.h_dj.news.base;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.h_dj.news.App;
import com.example.h_dj.news.utils.LogUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;

import static cn.bmob.v3.Bmob.getApplicationContext;

/**
 * Created by H_DJ on 2017/5/16.
 */

public abstract class BaseFragment extends Fragment {

    private Unbinder unbinder;
    protected Context mContext;
    private Toast mToast;
    protected App mApp;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtil.e(this.getClass().getSimpleName() + "初始化");
        View inflate = inflater.inflate(getlayoutId(), container, false);
        unbinder = ButterKnife.bind(this, inflate);
        init();
        return inflate;
    }

    protected void init() {
        mApp = (App) ((AppCompatActivity) mContext).getApplication();
    }


    protected void initToolbar(Toolbar mToolbar, String title) {
        ((AppCompatActivity) mContext).setSupportActionBar(mToolbar);
        ActionBar actionBar = ((AppCompatActivity) mContext).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
    }

    protected abstract int getlayoutId();

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

    /**
     * 跳转
     *
     * @param mclass
     */
    public void goTo(Class mclass, Bundle bundle) {
        Intent intent = new Intent(getContext(), mclass);
        intent.putExtra("data", bundle);
        startActivity(intent);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
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


}
