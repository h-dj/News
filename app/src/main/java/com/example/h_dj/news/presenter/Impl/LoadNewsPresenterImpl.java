package com.example.h_dj.news.presenter.Impl;

import android.content.Context;
import android.text.TextUtils;

import com.example.h_dj.news.Inteface.INewsFragment;
import com.example.h_dj.news.Message.MyMessageEvent;
import com.example.h_dj.news.R;
import com.example.h_dj.news.bean.NewsBean;
import com.example.h_dj.news.Contracts;
import com.example.h_dj.news.bean.VideoNewsBean;
import com.example.h_dj.news.presenter.ILoadNewsPresenter;
import com.example.h_dj.news.utils.GsonUtils;
import com.example.h_dj.news.utils.LogUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import okhttp3.Call;

/**
 * Created by H_DJ on 2017/5/17.
 */

public class LoadNewsPresenterImpl implements ILoadNewsPresenter {

    private INewsFragment mINewsFragment;
    private Context mContext;

    public LoadNewsPresenterImpl(Context mContext, INewsFragment mINewsFragment) {
        this.mINewsFragment = mINewsFragment;
        this.mContext = mContext;
    }

    public LoadNewsPresenterImpl(Context context) {
        this.mContext = context;
    }

    @Override
    public void LoadNewsData(String value) {
        String url = Contracts.getRequestUrl(value);
        LogUtil.e(url);
        if (TextUtils.isEmpty(url)) {
            mINewsFragment.failed(mContext.getString(R.string.error_url));
        } else {
            OkHttpUtils.get()
                    .url(url)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            mINewsFragment.failed(mContext.getString(R.string.error_network));
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            NewsBean newsBean = GsonUtils.String2Obj(response);
                            if (newsBean.getError_code() == 0) {
                                LogUtil.e("成功");
                                List<NewsBean.ResultBean.DataBean> data = newsBean.getResult().getData();
                                mINewsFragment.success(data);
                            }
                        }
                    });
        }

    }

    @Override
    public void loadVideoNewsList() {
        OkHttpUtils.get()
                .url(Contracts.videoUrl)
                .addHeader("Host", "c.3g.163.com")
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        EventBus.getDefault().post(new MyMessageEvent<>(null, MyMessageEvent.MSG_FROM_LOAD_VIDEO_LIST_ERROR));
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        VideoNewsBean videoNewsBean = GsonUtils.String2VideoNewsBean(response);
                        if (videoNewsBean != null) {
                            List<VideoNewsBean.VideoListBean> videoList = videoNewsBean.getVideoList();
                            if (videoList != null && videoList.size() > 0) {
                                EventBus.getDefault().post(new MyMessageEvent<>(videoList, MyMessageEvent.MSG_FROM_LOAD_VIDEO_LIST_SUCCESS));
                            } else {
                                EventBus.getDefault().post(new MyMessageEvent<>(null, MyMessageEvent.MSG_FROM_LOAD_VIDEO_LIST_ERROR));
                            }

                        }
                    }
                });
    }
}
