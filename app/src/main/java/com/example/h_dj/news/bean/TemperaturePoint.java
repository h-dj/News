package com.example.h_dj.news.bean;

/**
 * Created by H_DJ on 2017/6/1.
 */

/**
 * 温度曲线图中的点
 */
public class TemperaturePoint {
    public float x;//x坐标
    public float dy;//dayY坐标
    public float ny;//nightY坐标

    public TemperaturePoint(float x, float dy, float ny) {
        this.x = x;
        this.dy = dy;
        this.ny = ny;
    }

    @Override
    public String toString() {
        return "TemperaturePoint{" +
                "x=" + x +
                ", dy=" + dy +
                ", ny=" + ny +
                '}';
    }
}

