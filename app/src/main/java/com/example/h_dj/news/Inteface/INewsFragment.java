package com.example.h_dj.news.Inteface;

import com.example.h_dj.news.bean.NewsBean;

import java.util.List;

/**
 * Created by H_DJ on 2017/5/17.
 */

public interface INewsFragment {
    void failed(String s);

    void success(List<NewsBean.ResultBean.DataBean> data);
}
