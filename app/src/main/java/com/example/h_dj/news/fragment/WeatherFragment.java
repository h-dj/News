package com.example.h_dj.news.fragment;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.h_dj.news.Message.MyMessageEvent;
import com.example.h_dj.news.R;
import com.example.h_dj.news.bean.WeatherInfos;
import com.example.h_dj.news.presenter.ILoadNewsPresenter;
import com.example.h_dj.news.presenter.Impl.LoadNewsPresenterImpl;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by H_DJ on 2017/5/16.
 */

public class WeatherFragment extends BaseFragment {

    @BindView(R.id.local)
    ImageView mLocal;
    @BindView(R.id.share)
    ImageView mShare;
    @BindView(R.id.weatherIcon)
    ImageView mWeatherIcon;
    @BindView(R.id.temperature)
    TextView mTemperature;
    @BindView(R.id.weatherInfo)
    TextView mWeatherInfo;
    @BindView(R.id.pm)
    TextView m5;
    @BindView(R.id.weatherIcon1)
    ImageView mWeatherIcon1;
    @BindView(R.id.week1)
    TextView mWeek1;
    @BindView(R.id.temperature1)
    TextView mTemperature1;
    @BindView(R.id.weatherInfo1)
    TextView mWeatherInfo1;
    @BindView(R.id.weatherIcon2)
    ImageView mWeatherIcon2;
    @BindView(R.id.week2)
    TextView mWeek2;
    @BindView(R.id.temperature2)
    TextView mTemperature2;
    @BindView(R.id.weatherInfo2)
    TextView mWeatherInfo2;
    @BindView(R.id.weatherIcon3)
    ImageView mWeatherIcon3;
    @BindView(R.id.week3)
    TextView mWeek3;
    @BindView(R.id.temperature3)
    TextView mTemperature3;
    @BindView(R.id.weatherInfo3)
    TextView mWeatherInfo3;


    private ILoadNewsPresenter mPresenter;

    @Override
    protected int getlayoutId() {
        return R.layout.fragment_weather;
    }

    @Override
    protected void init() {
        super.init();
        EventBus.getDefault().register(this);
        mPresenter = new LoadNewsPresenterImpl(mContext);
        mPresenter.loadWeatherInfo("101280103");
    }


    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MyMessageEvent event) {
        int fromMsg = event.getFromMsg();
        switch (fromMsg) {
            case MyMessageEvent.MSG_FROM_LOAD_WEATHER_SUCCESS:
                //设置天气信息
                WeatherInfos weatherInfos = (WeatherInfos) event.getT();
                setWeatherInfo(weatherInfos);
                break;
            case MyMessageEvent.MSG_FROM_LOAD_WEATHER_FAILED:
                Toast.makeText(mContext, "获取失败", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    /**
     * 设置天气信息
     *
     * @param weatherInfos
     */
    private void setWeatherInfo(WeatherInfos weatherInfos) {
        WeatherInfos.RealtimeBean realtime = weatherInfos.getRealtime();
        String date = realtime.getDate();
        String week = getWeek(realtime.getWeek());
        String weather = realtime.getWeather().getInfo();
        String wind = realtime.getWind().getDirect() + " " + realtime.getWind().getPower();
        String temperature = realtime.getWeather().getTemperature();
        mTemperature.setText(temperature);
        mWeatherInfo.setText(String.format("%s  %s \n %s  %s", date, week, weather, wind));
        weatherInfos.getWeather();

        mWeek1.setText(getWeek(realtime.getWeek() + 1));
        mWeek2.setText(getWeek(realtime.getWeek() + 2));
        mWeek3.setText(getWeek(realtime.getWeek() + 3));
        WeatherInfos.WeatherBeanX weatherBeanX1 = weatherInfos.getWeather().get(1);
        WeatherInfos.WeatherBeanX weatherBeanX2 = weatherInfos.getWeather().get(2);
        WeatherInfos.WeatherBeanX weatherBeanX3 = weatherInfos.getWeather().get(3);

        mTemperature1.setText(weatherBeanX1.getInfo().getDay().get(2));
        mTemperature2.setText(weatherBeanX2.getInfo().getDay().get(2));
        mTemperature3.setText(weatherBeanX3.getInfo().getDay().get(2));

        String s1 = weatherBeanX1.getInfo().getDay().get(1);
        String s4 = weatherBeanX1.getInfo().getDay().get(4);
        mWeatherInfo1.setText(String.format("%s %s", s1, s4));

        s1 = weatherBeanX2.getInfo().getDay().get(1);
        s4 = weatherBeanX2.getInfo().getDay().get(4);
        mWeatherInfo2.setText(String.format("%s %s", s1, s4));

        s1 = weatherBeanX3.getInfo().getDay().get(1);
        s4 = weatherBeanX3.getInfo().getDay().get(4);
        mWeatherInfo3.setText(String.format("%s %s", s1, s4));

    }


    private String getWeek(int week) {
        week = week % 7;
        switch (week) {
            case 0:
                return "星期一";
            case 1:
                return "星期二";
            case 2:
                return "星期三";
            case 3:
                return "星期四";
            case 4:
                return "星期五";
            case 5:
                return "星期六";
            case 6:
                return "星期日";
        }
        return "";
    }

    @OnClick({R.id.local, R.id.share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.local:
                break;
            case R.id.share:
                break;
        }
    }
}
