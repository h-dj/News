package com.example.h_dj.news.adapter;

import android.content.Context;
import android.util.Log;

import com.example.h_dj.news.R;
import com.example.h_dj.news.base.BaseRecycleViewAdapter;
import com.example.h_dj.news.bean.CommentBean;
import com.example.h_dj.news.bean.User;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created by H_DJ on 2017/5/27.
 */

public class MyCommentListAdapter extends BaseRecycleViewAdapter {


    public MyCommentListAdapter(Context context, int layoutId, List datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(MyViewHolder holder, int position) {
        CommentBean commentBean = (CommentBean) mList.get(position);
        String userId = commentBean.getUserId();
        getUserInfo(userId, holder);
        holder.setText(R.id.comment, commentBean.getCommentContent());
        holder.setText(R.id.stars, commentBean.getStars() + "");
    }

    /**
     * 获取用户信息
     *
     * @param objectId
     * @param holder
     * @return
     */
    public void getUserInfo(String objectId, final MyViewHolder holder) {
        BmobQuery<User> query = new BmobQuery<User>();
        query.getObject(objectId, new QueryListener<User>() {

            @Override
            public void done(User object, BmobException e) {
                if (e == null) {
                    holder.setText(R.id.userName, object.getUsername());
                    holder.setText(R.id.userFrom, object.getUser_from());
                } else {
                    Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }
}
