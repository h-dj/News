package com.example.h_dj.news.presenter.Impl;

import android.content.Context;
import android.text.TextUtils;

import com.example.h_dj.news.Contracts;
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

import static com.example.h_dj.news.Contracts.channelId;


/**
 * Created by H_DJ on 2017/5/17.
 */

public class LoadNewsPresenterImpl implements ILoadNewsPresenter {
    private Context mContext;
    private SPutils sPutils;

    public LoadNewsPresenterImpl(Context context) {
        this.mContext = context;
        sPutils = SPutils.newInstance(mContext);
    }

    @Override
    public void LoadNewsData(String value) {
        String url = Contracts.getRequestUrl(value);
        LogUtil.e(url + ":" + channelId + ":" + value);
        if (TextUtils.isEmpty(url)) {
            EventBus.getDefault().post(new MyMessageEvent<>(mContext.getString(R.string.error_url), MyMessageEvent.MSG_FROM_NEWSFRAGMENT_ERROR));
        } else {
            OkHttpUtils.get()
                    .url(url)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            e.printStackTrace();
                            EventBus.getDefault().post(new MyMessageEvent<>(mContext.getString(R.string.error_network), MyMessageEvent.MSG_FROM_NEWSFRAGMENT_ERROR));
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            LogUtil.e("新闻：" + response);
                           NewsBean newsBean = (NewsBean) GsonUtils.String2Obj(response, NewsBean.class);
                            if (newsBean.getError_code()==0) {
                                List<NewsBean.ResultBean.DataBean> data = newsBean.getResult().getData();
                                EventBus.getDefault().post(new MyMessageEvent<>(data, MyMessageEvent.MSG_FROM_NEWSFRAGMENT_SUCCESS));
                            }
                        }
                    });
        }

    }

    @Override
    public void loadVideoNewsList() {
        SPutils sPutils = SPutils.newInstance(mContext).build(Contracts.VIDEO_INFO, Context.MODE_PRIVATE);
        if (sPutils.isExist(Contracts.VIDEO_INFO)) {
            String response = sPutils.getString(Contracts.VIDEO_INFO, null);
            convertToVideoNewsBean(response);
        } else {
            OkHttpUtils.get()
                    .url(Contracts.videoUrl)
                    .addHeader("Host", "c.3g.163.com")
                    .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36")
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            e.printStackTrace();
                            EventBus.getDefault().post(new MyMessageEvent<>(null, MyMessageEvent.MSG_FROM_LOAD_VIDEO_LIST_ERROR));
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            convertToVideoNewsBean(response);
                        }
                    });
        }
    }

    private void convertToVideoNewsBean(String response) {
        VideoNewsBean videoNewsBean = (VideoNewsBean) GsonUtils.String2Obj(response, VideoNewsBean.class);
        if (videoNewsBean != null) {
            LogUtil.e(":::" + videoNewsBean.getVideoList().size());
            List<VideoNewsBean.VideoListBean> videoList = videoNewsBean.getVideoList();
            if (videoList != null && videoList.size() > 0) {
                EventBus.getDefault().post(new MyMessageEvent<>(videoList, MyMessageEvent.MSG_FROM_LOAD_VIDEO_LIST_SUCCESS));
                SPutils.newInstance(mContext).build(Contracts.VIDEO_INFO, Context.MODE_PRIVATE)
                        .putString(Contracts.VIDEO_INFO, response)
                        .commit();
            } else {
                EventBus.getDefault().post(new MyMessageEvent<>(null, MyMessageEvent.MSG_FROM_LOAD_VIDEO_LIST_ERROR));
            }

        }
    }

    @Override
    public void loadWeatherInfo(String city) {
        SPutils sPutils = SPutils.newInstance(mContext)
                .build(Contracts.WEATHER_INFO, Context.MODE_PRIVATE);
        boolean exist = sPutils.isExist(Contracts.WEATHER_INFO);
        LogUtil.e("天气、链接：" + exist);
        if (exist) {
            decodeWeatherResponse(sPutils.getString(Contracts.WEATHER_INFO, null));
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
                                    .build(Contracts.WEATHER_INFO, Context.MODE_PRIVATE)
                                    .putString(Contracts.WEATHER_INFO, response)
                                    .commit();
                            decodeWeatherResponse(response);
                        }
                    });
        }
    }

    @Override
    public void loadBg() {
        SPutils sPutils = SPutils.newInstance(mContext)
                .build(Contracts.WEATHER_INFO, Context.MODE_PRIVATE);
        boolean exist = sPutils.isExist(Contracts.WEATHER_BG);
        LogUtil.e("图片链接：" + exist);
        if (exist) {
            EventBus.getDefault().post(new MyMessageEvent<>(sPutils.getString(Contracts.WEATHER_BG, null), MyMessageEvent.MSG_FROM_LOAD_WEATHER_BG_SUCCESS));
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
                                    .build(Contracts.WEATHER_INFO, Context.MODE_PRIVATE)
                                    .putString(Contracts.WEATHER_BG, response)
                                    .commit();
                            EventBus.getDefault().post(new MyMessageEvent<>(response, MyMessageEvent.MSG_FROM_LOAD_WEATHER_BG_SUCCESS));
                        }
                    });
        }
    }

    /**
     * 查询省份
     *
     * @param url
     */
    private void loadProvince(String url) {
        OkHttpUtils.get()
                .url(url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        sPutils.putString(Contracts.WEATHER_PROVINCE, response).commit();
                        decodeWeatherAreaResponse(response);
                    }
                });

    }


    /**
     * 查询城市
     *
     * @param url
     */
    private void loadCity(final String url) {
        OkHttpUtils.get()
                .url(url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        String provinceCode = url.substring(url.lastIndexOf("/"));
                        sPutils.build(Contracts.WEATHER_AREA, Context.MODE_PRIVATE)
                                .putString(Contracts.WEATHER_CITY + provinceCode, response)
                                .commit();
                        decodeWeatherAreaResponse(response);
                    }
                });
    }


    /**
     * 查询县
     *
     * @param url
     */
    private void loadCounty(final String url) {
        OkHttpUtils.get()
                .url(url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        String cityCode = url.substring(url.lastIndexOf("/"));
                        sPutils.build(Contracts.WEATHER_AREA, Context.MODE_PRIVATE)
                                .putString(Contracts.WEATHER_COUNTRY + cityCode, response)
                                .commit();
                        decodeWeatherAreaResponse(response);
                    }
                });
    }

    @Override
    public void queryArea(String url, String area) {
        final SPutils sPutils = SPutils.newInstance(mContext)
                .build(Contracts.WEATHER_AREA, Context.MODE_PRIVATE);
        if ("province".equals(area)) {
            boolean exist = sPutils.isExist(Contracts.WEATHER_PROVINCE);
            LogUtil.e("省级：：" + exist);
            if (exist) {
                decodeWeatherAreaResponse(sPutils.getString(Contracts.WEATHER_PROVINCE, null));
            } else {
                loadProvince(url);
            }
        } else if ("city".equals(area)) {
            String provinceCode = url.substring(url.lastIndexOf("/"));
            boolean exist = sPutils.isExist(Contracts.WEATHER_CITY + provinceCode);
            LogUtil.e("市级：：" + exist);
            if (exist) {
                decodeWeatherAreaResponse(sPutils.getString(Contracts.WEATHER_CITY + provinceCode, null));
            } else {
                loadCity(url);
            }
        } else if ("county".equals(area)) {
            String cityCode = url.substring(url.lastIndexOf("/"));
            boolean exist = sPutils.isExist(Contracts.WEATHER_COUNTRY + cityCode);
            LogUtil.e("县级：：" + exist);
            if (exist) {
                decodeWeatherAreaResponse(sPutils.getString(Contracts.WEATHER_COUNTRY + cityCode, null));
            } else {
                loadCounty(url);
            }
        }


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
            WeatherInfos info = (WeatherInfos) GsonUtils.String2Obj(response, WeatherInfos.class);
            WeatherInfos.HeWeather5Bean heWeather5Bean = info.getHeWeather5().get(0);
            if (heWeather5Bean != null) {
                if ("ok".equals(heWeather5Bean.getStatus())) {
                    EventBus.getDefault().post(new MyMessageEvent<>(heWeather5Bean, MyMessageEvent.MSG_FROM_LOAD_WEATHER_SUCCESS));
                }
            }
        }
    }
}
