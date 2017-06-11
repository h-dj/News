package com.example.h_dj.news.bean;

/**
 * Created by H_DJ on 2017/6/1.
 */

/**
 * 温度数据
 */
public class Temperature {
    public int dayTemperature;//day温度
    public int nightTemperature;//night温度


    public Temperature(int dayTemperature, int nightTemperature) {
        this.dayTemperature = dayTemperature;
        this.nightTemperature = nightTemperature;
    }
}