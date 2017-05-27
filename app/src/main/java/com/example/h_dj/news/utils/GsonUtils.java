package com.example.h_dj.news.utils;


import com.example.h_dj.news.bean.NewsBean;
import com.google.gson.Gson;

/**
 * Created by H_DJ on 2017/5/17.
 */

public class GsonUtils {

    private static Gson mGson = new Gson();

    public static NewsBean String2Obj(String result) {
        return mGson.fromJson(result, NewsBean.class);
    }
}
