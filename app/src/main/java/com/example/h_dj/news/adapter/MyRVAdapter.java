package com.example.h_dj.news.adapter;

import android.content.Context;

import com.example.h_dj.news.bean.NewsBean;

import java.util.List;

/**
 * Created by H_DJ on 2017/5/16.
 */

public class MyRVAdapter extends BaseRecycleViewAdapter {

    private List<NewsBean.ResultBean.DataBean> mResultBeen;

    public MyRVAdapter(Context context, int layoutId, List datas) {
        super(context, layoutId, datas);
        mResultBeen = datas;
    }

    @Override
    protected void convert(MyViewHolder holder, Object o) {

    }
}
