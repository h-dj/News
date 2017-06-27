package com.example.h_dj.news.adapter;

import android.content.Context;

import com.example.h_dj.news.R;
import com.example.h_dj.news.base.BaseRecycleViewAdapter;
import com.example.h_dj.news.bean.CollectBean;

import java.util.List;

/**
 * Created by H_DJ on 2017/5/29.
 */
public class CollectAdapter extends BaseRecycleViewAdapter {

    public CollectAdapter(Context context, int layoutId, List datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(MyViewHolder holder,int position) {
        CollectBean bean = (CollectBean) mList.get(position);
        holder.setText(R.id.title, bean.getCollectTitle());
        holder.setText(R.id.createAtTime, bean.getCreatedAt());
    }
}
