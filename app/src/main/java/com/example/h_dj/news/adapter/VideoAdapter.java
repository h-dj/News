package com.example.h_dj.news.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.h_dj.news.R;
import com.example.h_dj.news.base.BaseRecycleViewAdapter;
import com.example.h_dj.news.bean.VideoNewsBean;
import com.tencent.smtt.sdk.TbsVideo;

import java.util.List;

/**
 * Created by H_DJ on 2017/5/29.
 */
public class VideoAdapter extends BaseRecycleViewAdapter {

    public VideoAdapter(Context context, int layoutId, List datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(MyViewHolder holder, int position) {
        final VideoNewsBean.VideoListBean videoListBean = (VideoNewsBean.VideoListBean) mList.get(position);
        holder.setText(R.id.title, videoListBean.getTitle());
        ImageView imageView = holder.getView(R.id.videoView);
        Glide.with(mContext).load(videoListBean.getCover()).into(imageView);
        holder.getView(R.id.playIcon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TbsVideo.canUseTbsPlayer(mContext)) {
                    TbsVideo.openVideo(mContext, videoListBean.getMp4_url());
                }
            }
        });
    }


}
