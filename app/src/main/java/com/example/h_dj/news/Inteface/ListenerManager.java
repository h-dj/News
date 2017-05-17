package com.example.h_dj.news.Inteface;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by H_DJ on 2017/5/9.
 */

public class ListenerManager<T> {
    /**
     * 单例模式
     */
    public static ListenerManager listenerManager;

    /**
     * 注册的接口集合，发送广播的时候都能收到
     */
    private List<IListener> iListenerList = new CopyOnWriteArrayList<>();

    /**
     * 获得单例对象
     */
    public static ListenerManager getInstance() {
        if (listenerManager == null) {
            synchronized (ListenerManager.class) {
                if (listenerManager == null) {
                    listenerManager = new ListenerManager();
                }
            }

        }
        return listenerManager;
    }

    /**
     * 注册监听
     */
    public void registerListtener(IListener iListener) {
        iListenerList.add(iListener);
    }

    /**
     * 注销监听
     */
    public void unRegisterListener(IListener iListener) {
        if (iListenerList.contains(iListener)) {
            iListenerList.remove(iListener);
        }
    }

    /**
     * 发送广播
     */
    public void sendBroadCast(T t, int type) {
        for (IListener iListener : iListenerList) {
            iListener.notifyAllActivity(t, type);
        }
    }


}
