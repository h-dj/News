package com.example.h_dj.news.Message;

/**
 * Created by H_DJ on 2017/5/27.
 */

public class MyMessageEvent<T> {

    /**
     * 定义消息来源
     */
    public final static int MSG_FROM_LOGIN = 1;//登陆页面发出消息
    public final static int MSG_FROM_LOADCOMMENT = 2;//获取评论presenter发出消息
    public static final int MSG_FROM_NEWSFRAGMENT = 3;//新闻列表fragment
    public static final int MSG_FROM_LOAD_VIDEO_LIST_ERROR = 4;//加载视频列表失败
    public static final int MSG_FROM_LOAD_VIDEO_LIST_SUCCESS = 5;//加载视频列表成功
    public static final int MSG_FROM_LOAD_WEATHER_SUCCESS = 6;//加载天气
    public static final int MSG_FROM_LOAD_WEATHER_FAILED = 7;
    public static final int MSG_FROM_LOAD_WEATHER_BG_SUCCESS = 8; //天气背景
    public static final int MSG_FROM_LOAD_WEATHER_AREA_SUCCESS = 9;//位置
    public static final int MSG_FROM_LOAD_WEATHER_SELECTED_AREA_SUCCESS = 10;
    public static final int MSG_FROM_NEWSFRAGMENT_ERROR = 11;//加载新闻列表错误
    public static final int MSG_FROM_NEWSFRAGMENT_SUCCESS = 12;//加载新闻列表成功
    private int fromMsg;//消息来源
    private T t;//数据

    public MyMessageEvent(T t, int fromMsg) {
        this.t = t;
        this.fromMsg = fromMsg;
    }

    public T getT() {
        return t;
    }

    public int getFromMsg() {
        return fromMsg;
    }
}
