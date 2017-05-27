package com.example.h_dj.news.presenter.Impl;

import android.content.Context;

import com.example.h_dj.news.Message.MyMessageEvent;
import com.example.h_dj.news.bean.CommentBean;
import com.example.h_dj.news.presenter.LoadCommentListPresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by H_DJ on 2017/5/27.
 */

public class LoadCommentListPresenterImpl implements LoadCommentListPresenter {
    private Context mContext;

    public LoadCommentListPresenterImpl(Context context) {
        this.mContext = context;
    }

    @Override
    public void loadCommentList(String url) {
        BmobQuery<CommentBean> query = new BmobQuery<>();
        query.setLimit(20).order("createdAt")
                .addWhereEqualTo("commentUrl", url)
                .findObjects(new FindListener<CommentBean>() {
                    @Override
                    public void done(List<CommentBean> object, BmobException e) {
                        if (e == null) {
                            EventBus.getDefault().post(new MyMessageEvent<>(object, MyMessageEvent.MSG_FROM_LOADCOMMENT));
                        } else {
                            EventBus.getDefault().post(new MyMessageEvent<>(null, MyMessageEvent.MSG_FROM_LOADCOMMENT));
                        }
                    }
                });
    }
}
