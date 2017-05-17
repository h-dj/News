package com.example.h_dj.news.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.h_dj.news.R;
import com.example.h_dj.news.bean.NewsBean;

import java.util.List;

/**
 * Created by H_DJ on 2017/5/16.
 * 适配器
 */

public class MyRVAdapter extends BaseRecycleViewAdapter {
    private Context mContext;

    public MyRVAdapter(Context context, int layoutId, List datas) {
        super(context, layoutId, datas);
        mContext = context;
    }

    @Override
    protected void convert(MyViewHolder holder, Object o) {
        NewsBean.ResultBean.DataBean dataBean = (NewsBean.ResultBean.DataBean) o;
        holder.setText(R.id.item_title, dataBean.getTitle());
        holder.setText(R.id.item_author_name, dataBean.getAuthor_name());
        holder.setText(R.id.item_date, dataBean.getDate());
        Glide.with(mContext)
                .load(dataBean.getThumbnail_pic_s())
                .placeholder(R.mipmap.ic_launcher)
                .centerCrop()
                .error(R.mipmap.ic_launcher)
                .into((ImageView) holder.getView(R.id.item_pic));
    }
}
