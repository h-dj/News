package com.example.h_dj.news.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by H_DJ on 2017/5/27.
 */

public class CommentBean extends BmobObject {

    private String commentContent;
    private String commentUrl;
    private String userId;
    private int stars;


    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public String getCommentUrl() {
        return commentUrl;
    }

    public void setCommentUrl(String commentUrl) {
        this.commentUrl = commentUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

}
