package com.example.h_dj.news.adapter;

import android.content.Context;

import com.example.h_dj.news.R;
import com.example.h_dj.news.bean.VideoNewsBean;
import com.tencent.smtt.sdk.WebView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by H_DJ on 2017/5/29.
 */
public class VideoAdapter extends BaseRecycleViewAdapter {

    private List<WebView> mWebViews = new ArrayList<>();

    public VideoAdapter(Context context, int layoutId, List datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(MyViewHolder holder, Object o) {
        VideoNewsBean.VideoListBean videoListBean = (VideoNewsBean.VideoListBean) o;
        WebView view = holder.getView(R.id.web);
        mWebViews.add(view);
        view.loadData(getVideoSrcHtml(videoListBean.getMp4_url(), videoListBean.getCover()), "text/html", "utf-8");

//        if (TbsVideo.canUseTbsPlayer(mContext)) {
//            TbsVideo.openVideo(mContext, videoListBean.getMp4_url());
//        }
        holder.setText(R.id.title, videoListBean.getTitle());
    }

    public String getVideoSrcHtml(String src, String poster) {
        return "<video style=\"width:100%; height: auto;\" controls  poster=" + poster + " preload=\"auto\">\n" +
                "           <source src=" + src + " >\n" +
                "        </video>";
    }

    /**
     * 停止视频播放
     */
    public void destoryWebView(boolean isDestory) {
        for (WebView webView : mWebViews) {
            if (isDestory) {
                webView.onPause();
            } else {
                webView.onResume();
            }
        }


    }
}
