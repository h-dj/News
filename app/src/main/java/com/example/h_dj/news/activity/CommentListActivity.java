package com.example.h_dj.news.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.example.h_dj.news.Message.MyMessageEvent;
import com.example.h_dj.news.R;
import com.example.h_dj.news.adapter.MyCommentListAdapter;
import com.example.h_dj.news.base.BaseActivity;
import com.example.h_dj.news.bean.CommentBean;
import com.example.h_dj.news.presenter.Impl.LoadCommentListPresenterImpl;
import com.example.h_dj.news.presenter.LoadCommentListPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by H_DJ on 2017/5/27.
 */
public class CommentListActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.commentList)
    RecyclerView mCommentList;
    @BindView(R.id.comment)
    EditText mComment;
    @BindView(R.id.send)
    Button mSend;

    private List<CommentBean> mCommentBeanList;
    private MyCommentListAdapter mAdapter;
    private LoadCommentListPresenter mPresenter;
    private String url;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_comment_list;
    }

    @Override
    protected void init() {
        super.init();
        Intent intent = getIntent();
        Bundle data = intent.getBundleExtra("data");
        if (data != null) {
            url = data.getString("url");
        }

        mPresenter = new LoadCommentListPresenterImpl(this);
        initToolbar(mToolbar, "");
        mCommentBeanList = new ArrayList<>();
        mAdapter = new MyCommentListAdapter(this, R.layout.comment_list, mCommentBeanList);
        initRecycleView();
        mPresenter.loadCommentList(url);
        showProgressDialog("获取评论！", null);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MyMessageEvent event) {
        int fromMsg = event.getFromMsg();
        switch (fromMsg) {
            case MyMessageEvent.MSG_FROM_LOADCOMMENT:
                List<CommentBean> t = (List<CommentBean>) event.getT();
                if (t != null) {
                    mCommentBeanList.clear();
                    mCommentBeanList.addAll(t);
                    mAdapter.notifyDataSetChanged();
                    showToast("获取成功");
                } else {
                    showToast("获取失败");
                }
                hiddenProgressDialog();
                break;
        }
    }

    /**
     * 初始化RecycleView
     */
    private void initRecycleView() {
        mCommentList.setHasFixedSize(true);
        mCommentList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mCommentList.setLayoutManager(new LinearLayoutManager(this));
        mCommentList.setAdapter(mAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @OnClick(R.id.send)
    public void onViewClicked() {
        hiddenInputMethodManager(mComment);
        if (mApp.checkLogin()) {
            String comment = mComment.getText().toString().trim();
            if (TextUtils.isEmpty(comment)) {
                showToast("还没填写评论");
                return;
            }

            sendComment(comment);
        } else {
            goTo(LoginActivity.class);
        }
    }

    /**
     * 发送评论
     *
     * @param comment 评论内容
     */
    private void sendComment(String comment) {
        showProgressDialog("评论中...", null);
        final CommentBean comments = new CommentBean();
        //注意：不能调用gameScore.setObjectId("")方法
        comments.setUserId(mApp.mUser.getObjectId());
        comments.setCommentContent(comment);
        comments.setCommentUrl(url);
        comments.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if (e == null) {
                    showToast("评论成功");
                    mComment.setText("");
                    mCommentBeanList.add(comments);
                    mAdapter.notifyItemInserted(mCommentBeanList.size());
                    hiddenProgressDialog();
                } else {
                    Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                    showToast("评论失败");
                    hiddenProgressDialog();
                }
            }
        });
    }
}