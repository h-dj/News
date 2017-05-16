package com.example.h_dj.news.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;

import com.example.h_dj.news.R;
import com.example.h_dj.news.adapter.MyPagerAdapter;
import com.example.h_dj.news.fragment.SocietyFragment;
import com.example.h_dj.news.fragment.TopFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.tl_nav)
    TabLayout mTlNav;
    @BindView(R.id.vp_contain)
    ViewPager mVpContain;
    @BindView(R.id.activity_main)
    LinearLayout mActivityMain;
    private List<String> mTitles;
    private List<Fragment> mFragmentList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        super.init();
        initFragment();
        initTabTitle();
        initTabLayout();
        initViewPager();
    }

    /**
     * 初始化tablayout
     */
    private void initTabLayout() {
//        mTlNav.setTabMode(TabLayout.MODE_FIXED);//设置tab模式，当前为系统默认模式
        mTlNav.addTab(mTlNav.newTab().setText(mTitles.get(0)));//添加tab选项卡
        mTlNav.addTab(mTlNav.newTab().setText(mTitles.get(1)));

    }

    /**
     * 初始化ViewPager
     */
    private void initViewPager() {
        MyPagerAdapter mAdapter = new MyPagerAdapter(getSupportFragmentManager(), mFragmentList,mTitles);
        mVpContain.setAdapter(mAdapter);//给ViewPager设置适配器
        mTlNav.setupWithViewPager(mVpContain);//将TabLayout和ViewPager关联起来。
        mTlNav.setTabsFromPagerAdapter(mAdapter);//给Tabs设置适配器
    }

    /**
     * 初始化tabLayout的标题
     */
    private void initTabTitle() {
        mTitles = new ArrayList<>();
        mTitles.add("头条");
        mTitles.add("社会");
    }

    /**
     * 初始化fragment页面
     * top(头条，默认),shehui(社会),guonei(国内),guoji(国际),
     * yule(娱乐),tiyu(体育)junshi(军事),keji(科技),caijing(财经),shishang(时尚)
     */
    private void initFragment() {
        mFragmentList = new ArrayList<>();
        mFragmentList.add(new TopFragment());
        mFragmentList.add(new SocietyFragment());
    }


}
