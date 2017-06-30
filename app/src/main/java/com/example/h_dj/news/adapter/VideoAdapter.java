package com.example.h_dj.news.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.h_dj.news.R;
import com.example.h_dj.news.activity.SystemVideoPlayerActivity;
import com.example.h_dj.news.base.BaseRecycleViewAdapter;
import com.example.h_dj.news.bean.MediaItems;
import com.example.h_dj.news.bean.VideoNewsBean;

import java.util.ArrayList;
import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerSimple;

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
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaItems mediaItems = new MediaItems();
                mediaItems.setName(videoListBean.getTitle());
                mediaItems.setData(videoListBean.getMp4_url());
                ArrayList<MediaItems> mediaItemses = new ArrayList<MediaItems>();
                mediaItemses.add(mediaItems);
                Intent intent = new Intent(mContext, SystemVideoPlayerActivity.class);
                intent.putExtra("videoInfo", mediaItemses);
                intent.putExtra("position", 0);
                mContext.startActivity(intent);
            }
        });
        final JCVideoPlayerSimple jcVideoPlayerSimple = holder.getView(R.id.custom_videoplayer_standard);

        holder.setText(R.id.title, videoListBean.getTitle());
        final ImageView imageView = holder.getView(R.id.videoView);
        Glide.with(mContext).load(videoListBean.getCover()).into(imageView);
        holder.getView(R.id.playIcon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TbsVideo.openVideo(mContext, videoListBean.getMp4_url());
            }
        });
        jcVideoPlayerSimple.setUp(videoListBean.getMp4_url(), "");

    }


}
