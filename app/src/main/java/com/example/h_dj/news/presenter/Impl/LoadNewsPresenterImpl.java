package com.example.h_dj.news.presenter.Impl;

import android.content.Context;
import android.text.TextUtils;

import com.example.h_dj.news.Contracts;
import com.example.h_dj.news.Inteface.INewsFragment;
import com.example.h_dj.news.Message.MyMessageEvent;
import com.example.h_dj.news.R;
import com.example.h_dj.news.bean.AreaInfo;
import com.example.h_dj.news.bean.NewsBean;
import com.example.h_dj.news.bean.VideoNewsBean;
import com.example.h_dj.news.bean.WeatherInfos;
import com.example.h_dj.news.presenter.ILoadNewsPresenter;
import com.example.h_dj.news.utils.GsonUtils;
import com.example.h_dj.news.utils.LogUtil;
import com.example.h_dj.news.utils.SPutils;
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
    /**
     * 保存json数据key
     */
    public final static String WEATHER_CITY = "city_json";
    public final static String WEATHER_INFO = "weatherJson";
    public final static String WEATHER_BG = "weatherBg";
    private static final String WEATHER_PROVINCE = "province_json";
    private static final String WEATHER_CONUTY = "county_json";
    private static final String WEATHER_AREA = "area";

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

    @Override
    public void loadWeatherInfo(String city) {
        SPutils sPutils = SPutils.newInstance(mContext)
                .build(WEATHER_INFO, Context.MODE_PRIVATE);
        boolean exist = sPutils.isExist(WEATHER_INFO);
        LogUtil.e("天气、链接：" + exist);
        if (exist) {
            decodeWeatherResponse(sPutils.getString(WEATHER_INFO, null));
        } else {
            OkHttpUtils.get()
                    .url(Contracts.getWeatherUrl(city))
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            EventBus.getDefault().post(new MyMessageEvent<>(null, MyMessageEvent.MSG_FROM_LOAD_WEATHER_FAILED));
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            SPutils.newInstance(mContext)
                                    .build(WEATHER_INFO, Context.MODE_PRIVATE)
                                    .putString(WEATHER_INFO, response)
                                    .commit();
                            decodeWeatherResponse(response);
                        }
                    });
        }
    }

    @Override
    public void loadBg() {
        SPutils sPutils = SPutils.newInstance(mContext)
                .build(WEATHER_INFO, Context.MODE_PRIVATE);
        boolean exist = sPutils.isExist(WEATHER_BG);
        LogUtil.e("图片链接：" + exist);
        if (exist) {
            EventBus.getDefault().post(new MyMessageEvent<>(sPutils.getString(WEATHER_BG, null), MyMessageEvent.MSG_FROM_LOAD_WEATHER_BG_SUCCESS));
        } else {
            OkHttpUtils.get()
                    .url(Contracts.bingBg)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {

                        }

                        @Override
                        public void onResponse(String response, int id) {
                            SPutils.newInstance(mContext)
                                    .build(WEATHER_INFO, Context.MODE_PRIVATE)
                                    .putString(WEATHER_BG, response)
                                    .commit();
                            EventBus.getDefault().post(new MyMessageEvent<>(response, MyMessageEvent.MSG_FROM_LOAD_WEATHER_BG_SUCCESS));
                        }
                    });
        }
    }

    @Override
    public void loadProvince() {
        final SPutils sPutils = SPutils.newInstance(mContext)
                .build(WEATHER_AREA, Context.MODE_PRIVATE);
        boolean exist = sPutils.isExist(WEATHER_PROVINCE);
        LogUtil.e("省：" + exist);
        if (exist) {
            decodeWeatherAreaResponse(sPutils.getString(WEATHER_PROVINCE, null));
        } else {
            OkHttpUtils.get()
                    .url(Contracts.province)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {

                        }

                        @Override
                        public void onResponse(String response, int id) {
                            sPutils.putString(WEATHER_PROVINCE, response).commit();
                            decodeWeatherAreaResponse(response);
                        }
                    });
        }
    }

    @Override
    public void loadCity(String url) {
            OkHttpUtils.get()
                    .url(url)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {

                        }

                        @Override
                        public void onResponse(String response, int id) {
                            decodeWeatherAreaResponse(response);
                        }
                    });
    }

    @Override
    public void loadCounty(String url) {
            OkHttpUtils.get()
                    .url(url)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {

                        }

                        @Override
                        public void onResponse(String response, int id) {
                            decodeWeatherAreaResponse(response);
                        }
                    });
    }

    /**
     * 解析位置json
     *
     * @param response
     */
    private void decodeWeatherAreaResponse(String response) {
        if (response != null) {
            LogUtil.e(response);
            List<AreaInfo> areaInfos = GsonUtils.String2AreaInfo(response);
            if (areaInfos.size() > 0) {
                EventBus.getDefault().post(new MyMessageEvent<>(areaInfos, MyMessageEvent.MSG_FROM_LOAD_WEATHER_AREA_SUCCESS));
            }
        }
    }


    /**
     * 解析天气响应
     *
     * @param response
     */

    private void decodeWeatherResponse(String response) {
        if (response != null) {
            LogUtil.e(response);
            WeatherInfos info = GsonUtils.String2WeatherBean(response);
            WeatherInfos.HeWeather5Bean heWeather5Bean = info.getHeWeather5().get(0);
            if (heWeather5Bean != null) {
                if ("ok".equals(heWeather5Bean.getStatus())) {
                    EventBus.getDefault().post(new MyMessageEvent<>(heWeather5Bean, MyMessageEvent.MSG_FROM_LOAD_WEATHER_SUCCESS));
                }
            }
        }
    }
}
