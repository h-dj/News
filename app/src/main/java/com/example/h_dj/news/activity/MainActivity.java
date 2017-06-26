package com.example.h_dj.news.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.example.h_dj.news.R;
import com.example.h_dj.news.base.BaseActivity;
import com.example.h_dj.news.factory.FragmentFactory;
import com.example.h_dj.news.utils.LogUtil;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import butterknife.BindView;


public class MainActivity extends BaseActivity {


    @BindView(R.id.contentContainer)
    FrameLayout mContentContainer;
    @BindView(R.id.bottomBar)
    BottomBar mBottomBar;
    private Fragment currentFragment;
    private int position;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        super.init();
        initBottomBar();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
        LogUtil.e("position:" + position);
        outState.putInt("position", position);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
        this.position = savedInstanceState.getInt("position");
        LogUtil.e("position:" + position);
        setFragment(position);
        mBottomBar.selectTabWithId(position);
    }

    /**
     * 初始化底部导航
     */
    private void initBottomBar() {
        mBottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                setFragment(tabId);
            }
        });
    }

    /**
     * 设置fragment 页面
     */
    private void setFragment(int position) {
        this.position = position;
        //1.获取fragmentManager
        FragmentManager manager = getSupportFragmentManager();
        //2.开始事务
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        //3. 替换事务
        Fragment fragment = FragmentFactory.getInstance().createFragment(this.position);
        //如果当前的fragment为空；新添加一个
        if (currentFragment == null) {
            fragmentTransaction.add(R.id.contentContainer, fragment).commit();
        } else if (currentFragment != fragment) {
            //如果currentFragment不等于要添加的fragment;则隐藏currentFragment；显示fragment
            //如果被添加过；则隐藏currentFragment；显示fragment
            if (fragment.isAdded()) {
                fragmentTransaction.hide(currentFragment).show(fragment).commit();
            } else {
                fragmentTransaction.hide(currentFragment).add(R.id.contentContainer, fragment).commit();
            }
        }
        currentFragment = fragment;
    }


}
