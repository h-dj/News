package com.example.h_dj.news.presenter;

/**
 * Created by H_DJ on 2017/5/17.
 */

public interface ILoadNewsPresenter {

    void LoadNewsData(String value);

    void loadVideoNewsList();

    void loadWeatherInfo(String cityCode);

    void loadBg();

    void queryArea(String url, String province);
}
