package com.example.h_dj.news.bean;

import java.io.Serializable;

/**
 * Created by H_DJ on 2017/4/5.
 */
public class MediaItems implements Serializable {

    private String name;   //视频的名字
    private long size;  //视频的大小
    private long duration; //视频的时长
    private String data;//获取视频的绝对路径
    private String aritist;//艺术家

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getAritist() {
        return aritist;
    }

    public void setAritist(String aritist) {
        this.aritist = aritist;
    }
}
