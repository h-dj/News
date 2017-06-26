package com.example.h_dj.news.adapter;

import android.content.Context;

import com.example.h_dj.news.R;
import com.example.h_dj.news.base.BaseRecycleViewAdapter;

import java.util.List;

/**
 * Created by H_DJ on 2017/5/18.
 */

public class MyTabSelectAdapter extends BaseRecycleViewAdapter {

    public MyTabSelectAdapter(Context context, int layoutId, List datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(MyViewHolder holder, Object o,int position) {
        holder.setText(R.id.tv_tab, (String) o);
    }
}
