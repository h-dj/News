package com.example.h_dj.news.fragment;

import android.graphics.PixelFormat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.h_dj.news.Message.MyMessageEvent;
import com.example.h_dj.news.R;
import com.example.h_dj.news.adapter.VideoAdapter;
import com.example.h_dj.news.bean.VideoNewsBean;
import com.example.h_dj.news.presenter.ILoadNewsPresenter;
import com.example.h_dj.news.presenter.Impl.LoadNewsPresenterImpl;
import com.example.h_dj.news.utils.LogUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by H_DJ on 2017/5/16.
 */

public class VideoFragment extends BaseFragment {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh)
    TextView mRefresh;

    private VideoAdapter mVideoAdapter;
    private List<VideoNewsBean.VideoListBean> videoList;

    private ILoadNewsPresenter mPresenter;

    @Override
    protected int getlayoutId() {
        return R.layout.fragment_video;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        // TODO: 2017/5/29  还没解决 fragment 隐藏webView视频还在播放？？
        mVideoAdapter.destoryWebView(hidden);
    }

    @Override
    protected void init() {
        super.init();
        //这个对宿主没什么影响，建议声明
        ((AppCompatActivity) mContext).getWindow().setFormat(PixelFormat.TRANSLUCENT);
        EventBus.getDefault().register(this);
        initToolbar(mToolbar, "视频");
        videoList = new ArrayList<>();
        mVideoAdapter = new VideoAdapter(mContext, R.layout.video_item, videoList);
        initRecyclerView();
        mPresenter = new LoadNewsPresenterImpl(mContext);
        mPresenter.loadVideoNewsList();
    }

    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mVideoAdapter);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MyMessageEvent event) {
        switch (event.getFromMsg()) {
            case MyMessageEvent.MSG_FROM_LOAD_VIDEO_LIST_ERROR:
                LogUtil.e("失败");
                mRefresh.setVisibility(View.VISIBLE);
                Toast.makeText(mContext, "加载失败", Toast.LENGTH_SHORT).show();
                break;
            case MyMessageEvent.MSG_FROM_LOAD_VIDEO_LIST_SUCCESS:
                LogUtil.e("成功");
                mRefresh.setVisibility(View.GONE);
                List<VideoNewsBean.VideoListBean> videoListBeen = (List<VideoNewsBean.VideoListBean>) event.getT();
                videoList.clear();
                videoList.addAll(videoListBeen);
                mVideoAdapter.notifyDataSetChanged();
                break;
        }
    }


    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        if (mPresenter != null) {
            mPresenter = null;
        }
        super.onDestroyView();

    }


    @OnClick(R.id.refresh)
    public void onViewClicked() {
        mPresenter.loadVideoNewsList();
    }
}
