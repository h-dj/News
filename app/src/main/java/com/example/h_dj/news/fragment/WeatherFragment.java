package com.example.h_dj.news.fragment;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.h_dj.news.Contracts;
import com.example.h_dj.news.Message.MyMessageEvent;
import com.example.h_dj.news.R;
import com.example.h_dj.news.activity.ChooseAreaActivity;
import com.example.h_dj.news.base.BaseFragment;
import com.example.h_dj.news.bean.AreaInfo;
import com.example.h_dj.news.bean.Temperature;
import com.example.h_dj.news.bean.WeatherInfos;
import com.example.h_dj.news.presenter.ILoadNewsPresenter;
import com.example.h_dj.news.presenter.Impl.LoadNewsPresenterImpl;
import com.example.h_dj.news.utils.LogUtil;
import com.example.h_dj.news.utils.SPutils;
import com.example.h_dj.news.widget.WeatherView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by H_DJ on 2017/5/16.
 */

public class WeatherFragment extends BaseFragment {


    @BindView(R.id.cityName)
    TextView mCityName;
    @BindView(R.id.title_update_time)
    TextView mTitleUpdateTime;
    @BindView(R.id.new_temperature)
    TextView mNewTemperature;
    @BindView(R.id.weather_info_text)
    TextView mWeatherInfoText;
    @BindView(R.id.forecast_layout)
    LinearLayout mForecastLayout;
    @BindView(R.id.aqi_text)
    TextView mAqiText;
    @BindView(R.id.pm2_5)
    TextView mPm25;
    @BindView(R.id.comfort_text)
    TextView mComfortText;
    @BindView(R.id.car_wash_text)
    TextView mCarWashText;
    @BindView(R.id.sport_text)
    TextView mSportText;
    @BindView(R.id.bg)
    ImageView mBg;
    @BindView(R.id.refresh)
    ImageView mRefresh;
    @BindView(R.id.wv)
    WeatherView mWv;


    private ILoadNewsPresenter mPresenter;
    private String city;

    @Override
    protected int getlayoutId() {
        return R.layout.fragment_weather;
    }

    @Override
    protected void init() {
        super.init();
        LogUtil.e("init" + city);
        EventBus.getDefault().register(this);
        mPresenter = new LoadNewsPresenterImpl(mContext);
        mPresenter.loadWeatherInfo(city);
        mPresenter.loadBg();
    }


    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        if (mPresenter != null) {
            mPresenter = null;
        }
        super.onDestroyView();

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MyMessageEvent event) {
        int fromMsg = event.getFromMsg();
        switch (fromMsg) {
            case MyMessageEvent.MSG_FROM_LOAD_WEATHER_SUCCESS:
                WeatherInfos.HeWeather5Bean heWeather5Bean = (WeatherInfos.HeWeather5Bean) event.getT();
                setWeatherInfo(heWeather5Bean);
                break;
            case MyMessageEvent.MSG_FROM_LOAD_WEATHER_FAILED:
                Toast.makeText(mContext, "获取失败", Toast.LENGTH_SHORT).show();
                break;
            case MyMessageEvent.MSG_FROM_LOAD_WEATHER_BG_SUCCESS:
                Glide.with(mContext)
                        .load(event.getT())
                        .asBitmap()
                        .centerCrop()
                        .into(mBg);
                break;
            case MyMessageEvent.MSG_FROM_LOAD_WEATHER_SELECTED_AREA_SUCCESS:
                AreaInfo info = (AreaInfo) event.getT();
                city = info.getName();
                LogUtil.e("::" + city);
                update();
                break;
        }
        stopAnimation();
    }

    /**
     * 设置天气
     *
     * @param heWeather5Bean
     */
    private void setWeatherInfo(WeatherInfos.HeWeather5Bean heWeather5Bean) {
        //设置city
        mCityName.setText(heWeather5Bean.getBasic().getCity());
        //设置更新时间
        String time = heWeather5Bean.getBasic().getUpdate().getLoc().split("\\s+")[1];
        mTitleUpdateTime.setText(time + "");
        //设置温度
        String temperature = heWeather5Bean.getNow().getTmp();
        String weatherInfo = heWeather5Bean.getNow().getCond().getTxt();
        mNewTemperature.setText(String.format("%s ℃", temperature));
        mWeatherInfoText.setText(weatherInfo);

        //设置天气预告
        List<WeatherInfos.HeWeather5Bean.DailyForecastBean> daily_forecast = heWeather5Bean.getDaily_forecast();
        List<Temperature> temperatures = new ArrayList<>();
        temperatures.clear();
        mForecastLayout.removeAllViews();
        for (WeatherInfos.HeWeather5Bean.DailyForecastBean forecastBean : daily_forecast) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.forcast_item, mForecastLayout, false);
            TextView dateText = (TextView) view.findViewById(R.id.date_text);
            TextView infoText = (TextView) view.findViewById(R.id.info_text);
            TextView maxText = (TextView) view.findViewById(R.id.max_text);
            TextView minText = (TextView) view.findViewById(R.id.min_text);
            dateText.setTextColor(Color.WHITE);
            infoText.setTextColor(Color.WHITE);
            maxText.setTextColor(Color.WHITE);
            minText.setTextColor(Color.WHITE);

            dateText.setText(forecastBean.getDate());
            infoText.setText(String.format("%s/%s", forecastBean.getCond().getTxt_d(), forecastBean.getCond().getTxt_n()));
            String max = forecastBean.getTmp().getMax();
            String min = forecastBean.getTmp().getMin();
            maxText.setText(String.format("%s ℃", max));
            minText.setText(String.format("%s ℃", min));
            mForecastLayout.addView(view);
            Temperature temperature1 = new Temperature(Integer.parseInt(max), Integer.parseInt(min));
            temperatures.add(temperature1);
        }
        //设置温度曲线
        mWv.initTemperature(temperatures)
                .initPoint()
                .invalidate();

        //设置空气质量指数
        WeatherInfos.HeWeather5Bean.AqiBean aqi = heWeather5Bean.getAqi();
        if (aqi != null) {
            mAqiText.setText(aqi.getCity().getAqi());
            mPm25.setText(aqi.getCity().getPm25());
        }

        //设置生活建议
        WeatherInfos.HeWeather5Bean.SuggestionBean suggestion = heWeather5Bean.getSuggestion();
        mCarWashText.setText(String.format("舒适度：%s", suggestion.getCw().getTxt()));
        mComfortText.setText(String.format("洗车指数：%s", suggestion.getComf().getTxt()));
        mSportText.setText(String.format("运动指数：%s", suggestion.getSport().getTxt()));
    }


    @OnClick({R.id.refresh, R.id.cityName})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.refresh:
                startRefreshAnimation();
                update();
                break;
            case R.id.cityName:
                Bundle bundle = new Bundle();
                bundle.putString("currentCity", mCityName.getText().toString());
                goTo(ChooseAreaActivity.class, bundle);
                break;
        }
    }

    private void update() {
        SPutils.newInstance(mContext)
                .build(Contracts.WEATHER_INFO, Context.MODE_PRIVATE)
                .remove(Contracts.WEATHER_INFO)
                .remove(Contracts.WEATHER_BG)
                .commit();
        mPresenter.loadWeatherInfo(city);
        mPresenter.loadBg();
    }

    /**
     * 设置天气刷新时的刷新按钮动画
     */
    private void startRefreshAnimation() {
        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.refresh_rotate);
        mRefresh.startAnimation(animation);
    }

    /**
     * 停止动画
     */
    private void stopAnimation() {
        mRefresh.clearAnimation();
    }


}
