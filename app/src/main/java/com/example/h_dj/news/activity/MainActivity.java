package com.example.h_dj.news.activity;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.example.h_dj.news.R;
import com.example.h_dj.news.factory.FragmentFactory;
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


    /**
     * 初始化底部导航
     */
    private void initBottomBar() {
        mBottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId) {
                    case R.id.News:
                        position = tabId;
                        break;
                    case R.id.tab_read:
                        position = tabId;
                        break;
                    case R.id.tab_video:
                        position = tabId;
                        break;
                    case R.id.tab_recovery:
                        position = tabId;
                        break;
                    case R.id.tab_my:
                        position = tabId;
                        break;

                }
                setFragment(position);
            }
        });
    }

    /**
     * 设置fragment 页面
     */
    private void setFragment(int position) {

        //1.获取fragmentManager
        FragmentManager manager = getSupportFragmentManager();
        //2.开始事务
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        //3. 替换事务
        Fragment fragment = FragmentFactory.getInstance().createFragment(position);
        //如果当前的fragment为空；新添加一个
        if (currentFragment == null) {
            fragmentTransaction.add(R.id.contentContainer, fragment).commitAllowingStateLoss();
            currentFragment = fragment;
        } else if (currentFragment != fragment) {
            //如果currentFragment不等于要添加的fragment;则隐藏currentFragment；显示fragment
            //如果被添加过；则隐藏currentFragment；显示fragment
            if (fragment.isAdded()) {
                fragmentTransaction.hide(currentFragment).show(fragment).commitAllowingStateLoss();
            } else {
                fragmentTransaction.hide(currentFragment).add(R.id.contentContainer, fragment).commitAllowingStateLoss();
            }
            currentFragment = fragment;
        }
    }

    @Override
    protected void onDestroy() {
        if (currentFragment != null) {
            currentFragment.onDestroyView();
            currentFragment = null;
        }
        super.onDestroy();
    }
}
