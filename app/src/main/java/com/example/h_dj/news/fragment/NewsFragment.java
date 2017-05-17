package com.example.h_dj.news.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.example.h_dj.news.Inteface.INewsFragment;
import com.example.h_dj.news.Inteface.ListenerManager;
import com.example.h_dj.news.R;
import com.example.h_dj.news.adapter.MyPagerAdapter;
import com.example.h_dj.news.bean.NewsBean;
import com.example.h_dj.news.presenter.ILoadNewsPresenter;
import com.example.h_dj.news.presenter.Impl.LoadNewsPresenterImpl;
import com.example.h_dj.news.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by H_DJ on 2017/5/16.
 */

public class NewsFragment extends BaseFragment implements INewsFragment {

    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    private ILoadNewsPresenter mPresenter;

    private int newsType = 0;
    private List<String> mTabs;
    private List<Fragment> mPagers;


    @Override
    protected int getlayoutId() {
        return R.layout.fragment_news;
    }

    @Override
    protected void init() {
        super.init();
        mPresenter = new LoadNewsPresenterImpl(getContext(), this);

        initTabList();
        addTab();
        initFragmentPagers();
        initViewPager();
    }

    /**
     * 初始化新闻页面
     */
    private void initFragmentPagers() {
        if (mPagers == null) {
            mPagers = new ArrayList<>();
        }
        mPagers.clear();
        for (int i = 0; i < mTabs.size(); i++) {
            mPagers.add(new NewsContentFragment());
        }


    }

    /**
     * 初始化ViewPager
     */
    private void initViewPager() {
        mViewPager.setAdapter(new MyPagerAdapter(getChildFragmentManager(), mPagers, mTabs));
    }

    /**
     * 添加TabItem
     */
    private void addTab() {
        LogUtil.e(mTabs.size() + "");
        for (String tab : mTabs) {
            mTabLayout.addTab(mTabLayout.newTab().setText(tab));
        }
        mTabLayout.setupWithViewPager(mViewPager, true);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                LogUtil.e("当前tab" + tab.getPosition());
                newsType = tab.getPosition();
                mPresenter.LoadNewsData(newsType);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    /**
     * 初始化tab
     * ,top(头条，默认),shehui(社会),guonei(国内),guoji(国际),
     * yule(娱乐),tiyu(体育)junshi(军事),keji(科技),caijing(财经),shishang(时尚)
     */
    private void initTabList() {
        if (mTabs == null) {
            mTabs = new ArrayList<>();
        }
        mTabs.clear();
        mTabs.add("头条");
        mTabs.add("社会");
        mTabs.add("国内");
        mTabs.add("国际");
        mTabs.add("娱乐");
        mTabs.add("体育");
        mTabs.add("军事");
        mTabs.add("科技");
        mTabs.add("财经");
        mTabs.add("时尚");
    }

    @Override
    public void failed(String s) {
        LogUtil.e(s);
    }

    @Override
    public void success(List<NewsBean.ResultBean.DataBean> data) {
        LogUtil.e("数据类型：" + newsType + ":" + data.size());
        ListenerManager.getInstance().sendBroadCast(data, newsType);
    }
}
