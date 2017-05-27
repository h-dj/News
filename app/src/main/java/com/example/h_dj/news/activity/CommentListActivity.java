package com.example.h_dj.news.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.h_dj.news.Message.MyMessageEvent;
import com.example.h_dj.news.R;
import com.example.h_dj.news.adapter.MyCommentListAdapter;
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
        initToolbar(mToolbar);
        mCommentBeanList = new ArrayList<>();
        mAdapter = new MyCommentListAdapter(this, R.layout.comment_list, mCommentBeanList);
        initRecycleView();
        mPresenter.loadCommentList(url);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
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
                } else {
                    Toast.makeText(CommentListActivity.this, "获取失败", Toast.LENGTH_SHORT).show();
                }
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


    @OnClick(R.id.send)
    public void onViewClicked() {
    }


}
