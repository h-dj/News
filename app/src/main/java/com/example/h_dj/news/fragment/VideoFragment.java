package com.example.h_dj.news.fragment;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.h_dj.news.Message.MyMessageEvent;
import com.example.h_dj.news.R;
import com.example.h_dj.news.adapter.VideoAdapter;
import com.example.h_dj.news.base.BaseFragment;
import com.example.h_dj.news.bean.VideoNewsBean;
import com.example.h_dj.news.presenter.ILoadNewsPresenter;
import com.example.h_dj.news.presenter.Impl.LoadNewsPresenterImpl;
import com.example.h_dj.news.utils.LogUtil;
import com.example.h_dj.news.utils.SPutils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by H_DJ on 2017/5/16.
 */

public class VideoFragment extends BaseFragment {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.empty)
    LinearLayout empty;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout mSwipeRefresh;


    private VideoAdapter mVideoAdapter;
    private List<VideoNewsBean.VideoListBean> videoList;

    private ILoadNewsPresenter mPresenter;

    @Override
    protected int getlayoutId() {
        return R.layout.fragment_video;
    }


    @Override
    protected void init() {
        super.init();
        EventBus.getDefault().register(this);
        initToolbar(mToolbar, "视频");
        videoList = new ArrayList<>();
        mVideoAdapter = new VideoAdapter(mContext, R.layout.video_item, videoList);
        initRecyclerView();
        mPresenter = new LoadNewsPresenterImpl(mContext);
        mPresenter.loadVideoNewsList();
        initSwipeRefresh();
    }

    private void initSwipeRefresh() {
        mSwipeRefresh.setColorSchemeColors(getResources().getColor(R.color.colorPrimary),
                getResources().getColor(R.color.primary_light),
                getResources().getColor(R.color.icons));
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefresh.setRefreshing(true);
                SPutils.newInstance(mContext).build("VideoInfo", Context.MODE_PRIVATE)
                        .remove("VideoInfo")
                        .commit();
                mPresenter.loadVideoNewsList();
            }
        });
    }

    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mVideoAdapter);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MyMessageEvent event) {
        empty.setVisibility(View.GONE);
        switch (event.getFromMsg()) {
            case MyMessageEvent.MSG_FROM_LOAD_VIDEO_LIST_ERROR:
                LogUtil.e("失败");
                empty.setVisibility(View.VISIBLE);
                videoList.clear();
                mVideoAdapter.notifyDataSetChanged();
                Toast.makeText(mContext, "加载失败", Toast.LENGTH_SHORT).show();
                break;
            case MyMessageEvent.MSG_FROM_LOAD_VIDEO_LIST_SUCCESS:
                LogUtil.e("成功");
                empty.setVisibility(View.GONE);
                List<VideoNewsBean.VideoListBean> videoListBeen = (List<VideoNewsBean.VideoListBean>) event.getT();
                videoList.clear();
                videoList.addAll(videoListBeen);
                mVideoAdapter.notifyDataSetChanged();
                break;
        }
        mSwipeRefresh.setRefreshing(false);
    }


    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        if (mPresenter != null) {
            mPresenter = null;
        }
        super.onDestroyView();
    }


}
