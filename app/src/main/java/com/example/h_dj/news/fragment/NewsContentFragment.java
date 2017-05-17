package com.example.h_dj.news.fragment;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.h_dj.news.Inteface.IListener;
import com.example.h_dj.news.Inteface.ListenerManager;
import com.example.h_dj.news.R;
import com.example.h_dj.news.adapter.MyRVAdapter;
import com.example.h_dj.news.bean.NewsBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by H_DJ on 2017/5/16.
 */

public class NewsContentFragment extends BaseFragment implements IListener {


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
        ListenerManager.getInstance().registerListtener(this);
        mDataBeen = new ArrayList<>();
        mMyRVAdapter = new MyRVAdapter(getContext(), R.layout.news_item, mDataBeen);
        initRecyclerView();
    }

    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mMyRVAdapter);
    }


    @Override
    public void notifyAllActivity(Object o, int type) {
            List<NewsBean.ResultBean.DataBean> dataBeanList = (List<NewsBean.ResultBean.DataBean>) o;
            mDataBeen.clear();
            mDataBeen.addAll(dataBeanList);
            mMyRVAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        ListenerManager.getInstance().unRegisterListener(this);
        super.onDestroyView();
    }
}
