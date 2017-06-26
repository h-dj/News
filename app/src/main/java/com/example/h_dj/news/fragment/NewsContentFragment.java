package com.example.h_dj.news.fragment;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.h_dj.news.Message.MyMessageEvent;
import com.example.h_dj.news.R;
import com.example.h_dj.news.activity.WebActivity;
import com.example.h_dj.news.adapter.MyRVAdapter;
import com.example.h_dj.news.base.BaseFragment;
import com.example.h_dj.news.bean.NewsBean;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by H_DJ on 2017/5/16.
 */

public class NewsContentFragment extends BaseFragment {


    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private List<NewsBean.ResultBean.DataBean> mDataBeen;
    private MyRVAdapter mMyRVAdapter;


    @Override
    protected int getlayoutId() {
        return R.layout.fragment_top;
    }

    @Override
    protected void init() {
        super.init();
        EventBus.getDefault().register(this);
        mDataBeen = new ArrayList<>();
        mMyRVAdapter = new MyRVAdapter(mContext, R.layout.news_item, mDataBeen);
        mMyRVAdapter.setItemListener(new MyRVAdapter.OnItemListener() {
            @Override
            public void onBannerListener(int position) {
                Bundle bundle = new Bundle();
                bundle.putString("url", mDataBeen.get(position).getUrl());
                bundle.putString("title", mDataBeen.get(position).getTitle());
                goTo(WebActivity.class, bundle);
            }

            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("url", mDataBeen.get(position).getUrl());
                bundle.putString("title", mDataBeen.get(position).getTitle());
                goTo(WebActivity.class, bundle);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });

        initRecyclerView();
    }

    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mMyRVAdapter);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MyMessageEvent event) {
        int fromMsg = event.getFromMsg();
        switch (fromMsg) {
            case MyMessageEvent.MSG_FROM_NEWSFRAGMENT:
                mDataBeen.clear();
                List<NewsBean.ResultBean.DataBean> t = (List<NewsBean.ResultBean.DataBean>) event.getT();
                mDataBeen.addAll(t);
                mMyRVAdapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }
}
