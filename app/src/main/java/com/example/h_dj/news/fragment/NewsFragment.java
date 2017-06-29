package com.example.h_dj.news.fragment;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArraySet;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.h_dj.news.Contracts;
import com.example.h_dj.news.Message.MyMessageEvent;
import com.example.h_dj.news.R;
import com.example.h_dj.news.adapter.MyPagerAdapter;
import com.example.h_dj.news.adapter.MyTabSelectAdapter;
import com.example.h_dj.news.base.BaseFragment;
import com.example.h_dj.news.base.BaseRecycleViewAdapter;
import com.example.h_dj.news.bean.NewsBean;
import com.example.h_dj.news.presenter.ILoadNewsPresenter;
import com.example.h_dj.news.presenter.Impl.LoadNewsPresenterImpl;
import com.example.h_dj.news.utils.LogUtil;
import com.example.h_dj.news.utils.SPutils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by H_DJ on 2017/5/16.
 */

public class NewsFragment extends BaseFragment {

    private static final String TABS = "TABS";
    private static final String TABS_SELECT = "TABS_SELECT";//已选择栏目
    private static final String TABS_CAN_SELECT = "TABS_CAN_SELECT";//供选择
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.tabs_switcher)
    ImageButton mTabsSwitcher;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;


    private ILoadNewsPresenter mPresenter;

    private int newsType = 0;
    private List<String> mTabs;
    private List<String> mCanSelectTabs;

    private List<Fragment> mPagers;
    private PopupWindow pw;
    private MyPagerAdapter myPagerAdapter;
    private SPutils mSPutils;


    @Override
    protected int getlayoutId() {
        return R.layout.fragment_news;
    }

    @Override
    protected void init() {
        super.init();
        EventBus.getDefault().register(this);
        mSPutils = SPutils.newInstance(mContext).build(TABS, Context.MODE_PRIVATE);
        mPresenter = new LoadNewsPresenterImpl(mContext);
        mTabs = new ArrayList<>();
        mCanSelectTabs = new ArrayList<>();
        initToolbar(mToolbar, getString(R.string.app_name));
        initTabList();
        addTab();
        initTab();
        initFragmentPagers();
        initViewPager();
        initPopupWindow();
    }

    /**
     * 初始化popupwindow
     */
    private void initPopupWindow() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View inflate = inflater.inflate(R.layout.popup_tabs, null);
        pw = new PopupWindow(inflate, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        pw.setAnimationStyle(R.style.popupWindowSelectTabs);

        final MyTabSelectAdapter canSelectAdapter = new MyTabSelectAdapter(mContext, R.layout.tabs, mCanSelectTabs);
        final MyTabSelectAdapter selectAdapter = new MyTabSelectAdapter(mContext, R.layout.tabs, mTabs);
        RecyclerView rvSelected = (RecyclerView) inflate.findViewById(R.id.rv_tabs_selected);
        rvSelected.setItemAnimator(new DefaultItemAnimator());
        rvSelected.setLayoutManager(new GridLayoutManager(mContext, 3));
        rvSelected.setHasFixedSize(true);
        rvSelected.setAdapter(selectAdapter);
        selectAdapter.setOnItemClickListener(new BaseRecycleViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (mTabs.size() < 6) {
                    Toast.makeText(mContext, "至少选择五个栏目", Toast.LENGTH_SHORT).show();
                    return;
                }
                String remove = mTabs.remove(position);
                mCanSelectTabs.add(remove);
                //先更新数据
                selectAdapter.notifyDataSetChanged();
                canSelectAdapter.notifyDataSetChanged();
                //再做删除动画
                selectAdapter.notifyItemRemoved(position);
                canSelectAdapter.notifyItemInserted(mTabs.size());
            }

            @Override
            public void onItemLongClick(View view, int position) {
            }
        });
        final RecyclerView rvCanSelected = (RecyclerView) inflate.findViewById(R.id.rv_can_select_tabs);
        rvCanSelected.setItemAnimator(new DefaultItemAnimator());
        rvCanSelected.setLayoutManager(new GridLayoutManager(mContext, 3));
        rvCanSelected.setHasFixedSize(true);
        rvCanSelected.setAdapter(canSelectAdapter);
        canSelectAdapter.setOnItemClickListener(new BaseRecycleViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String remove = mCanSelectTabs.remove(position);
                mTabs.add(remove);

                selectAdapter.notifyDataSetChanged();
                canSelectAdapter.notifyDataSetChanged();

                canSelectAdapter.notifyItemRemoved(position);
                selectAdapter.notifyItemInserted(mTabs.size());
            }

            @Override
            public void onItemLongClick(View view, int position) {
            }
        });

        inflate.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTab();
                initFragmentPagers();
                myPagerAdapter.notifyDataSetChanged();
                pw.dismiss();
            }
        });
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
        myPagerAdapter = new MyPagerAdapter(getChildFragmentManager(), mPagers, mTabs);
        mViewPager.setAdapter(myPagerAdapter);
    }

    /**
     * 添加TabItem
     */
    private void addTab() {
        LogUtil.e(mTabs.size() + "");
        mTabLayout.removeAllTabs();
        for (String tab : mTabs) {
            mTabLayout.addTab(mTabLayout.newTab().setText(tab));
        }
    }

    /**
     * 初始化tab
     */
    private void initTab() {
        mTabLayout.setupWithViewPager(mViewPager, true);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                LogUtil.e("当前tab" + tab.getPosition());
                String tabValue = tab.getText().toString();
                mPresenter.LoadNewsData(tabValue);
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
        Set<String> set = mSPutils.getStringSet(TABS_SELECT, new ArraySet());
        mTabs.clear();
        mTabs.addAll(set);
        LogUtil.e("mTab:" + mTabs.size());
        if (mTabs != null && mTabs.size() <= 0 && !mSPutils.isExist(TABS_SELECT)) {
            mTabs.add(Contracts.typeValue1[0]);
            mTabs.add(Contracts.typeValue1[1]);
            mTabs.add(Contracts.typeValue1[2]);
            mTabs.add(Contracts.typeValue1[3]);
            mTabs.add(Contracts.typeValue1[4]);
        }
        set = mSPutils.getStringSet(TABS_CAN_SELECT, new ArraySet());
        mCanSelectTabs.clear();
        mCanSelectTabs.addAll(set);
        LogUtil.e("mCanSelectTabs:" + mCanSelectTabs.size());
        if (mCanSelectTabs != null && mCanSelectTabs.size() <= 0 && !mSPutils.isExist(TABS_CAN_SELECT)) {
            mCanSelectTabs.add(Contracts.typeValue1[5]);
            mCanSelectTabs.add(Contracts.typeValue1[6]);
            mCanSelectTabs.add(Contracts.typeValue1[7]);
            mCanSelectTabs.add(Contracts.typeValue1[8]);
            mCanSelectTabs.add(Contracts.typeValue1[9]);

        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MyMessageEvent event) {
        int fromMsg = event.getFromMsg();
        switch (fromMsg) {
            case MyMessageEvent.MSG_FROM_NEWSFRAGMENT_ERROR:
                String msg = (String) event.getT();
                showToast(msg);
                break;
            case MyMessageEvent.MSG_FROM_NEWSFRAGMENT_SUCCESS:
                List<NewsBean.ResultBean.DataBean> result = (List<NewsBean.ResultBean.DataBean>) event.getT();
                LogUtil.e("数据类型：" + newsType + ":" + result.size());
                EventBus.getDefault().post(new MyMessageEvent<>(result, MyMessageEvent.MSG_FROM_NEWSFRAGMENT));
                break;
        }
    }

    @OnClick(R.id.tabs_switcher)
    public void onViewClicked() {
        pw.showAsDropDown(mToolbar);
        pw.showAtLocation(mToolbar, Gravity.TOP | Gravity.CENTER, 0, mToolbar.getHeight());
    }

    @Override
    public void onDestroyView() {
        if (pw != null) {
            pw.dismiss();
        }
        Set<String> set = new ArraySet<>();
        set.addAll(mTabs);
        mSPutils.putStringSet(TABS_SELECT, set);
        set.clear();
        set.addAll(mCanSelectTabs);
        mSPutils.putStringSet(TABS_CAN_SELECT, set)
                .commit();
        mPresenter = null;
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }


}
