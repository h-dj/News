package com.example.h_dj.news.activity;

import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.h_dj.news.R;
import com.example.h_dj.news.adapter.GuidePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by H_DJ on 2017/5/15.
 */
public class GuideActivity extends BaseActivity {

    private List<View> mViews;

    @Override
    protected int getLayoutId() {
        requestWindowFeature(Window.FEATURE_NO_TITLE); //设置无标题
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);  //设置全屏
        return R.layout.activity_guide;
    }

    @Override
    protected void init() {
        super.init();
        setSubView();
    }

    /**
     * 初始化view
     */
    private void setSubView() {
        mViews = new ArrayList<View>();
        LayoutInflater inflater = LayoutInflater.from(this);

        //把引导页加入到集合中
        mViews.add(inflater.inflate(R.layout.view_guide_01, null));
        mViews.add(inflater.inflate(R.layout.view_guide_02, null));
        mViews.add(inflater.inflate(R.layout.view_guide_03, null));
        mViews.add(inflater.inflate(R.layout.view_guide_04, null));

        GuidePagerAdapter guidePagerAdapter = new GuidePagerAdapter(mViews, this);
        ((ViewPager) findViewById(R.id.viewPagerGuide)).setAdapter(guidePagerAdapter);
    }
}
