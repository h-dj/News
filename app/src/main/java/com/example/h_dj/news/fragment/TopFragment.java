package com.example.h_dj.news.fragment;

import android.support.v7.widget.RecyclerView;

import com.example.h_dj.news.R;

import butterknife.BindView;

/**
 * Created by H_DJ on 2017/5/16.
 */

public class TopFragment extends BaseFragment {


    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;


    @Override
    protected int getlayoutId() {
        return R.layout.fragment_top;
    }

    @Override
    protected void init() {
        super.init();

    }


}
