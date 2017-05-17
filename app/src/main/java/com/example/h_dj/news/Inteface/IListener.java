package com.example.h_dj.news.Inteface;

/**
 * Created by H_DJ on 2017/5/9.
 */
public interface IListener<T> {

    void notifyAllActivity(T t, int type);
}
