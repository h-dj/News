package com.example.h_dj.news.presenter.Impl;

import android.content.Context;
import android.text.TextUtils;

import com.example.h_dj.news.Inteface.INewsFragment;
import com.example.h_dj.news.R;
import com.example.h_dj.news.bean.NewsBean;
import com.example.h_dj.news.entity.API;
import com.example.h_dj.news.presenter.ILoadNewsPresenter;
import com.example.h_dj.news.utils.GsonUtils;
import com.example.h_dj.news.utils.LogUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

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

    @Override
    public void LoadNewsData(int position) {
        String url = API.getRequestUrl(position);
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
}
