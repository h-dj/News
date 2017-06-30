package com.example.h_dj.news.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.h_dj.news.R;
import com.example.h_dj.news.adapter.CollectAdapter;
import com.example.h_dj.news.base.BaseActivity;
import com.example.h_dj.news.base.BaseRecycleViewAdapter;
import com.example.h_dj.news.bean.CollectBean;
import com.example.h_dj.news.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CountListener;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by H_DJ on 2017/5/29.
 */
public class CollectionActivity extends BaseActivity {

    private static final int GET_COLLECT_COUNT = 1;//获取收藏数的消息
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh)
    TextView mRefresh;

    private CollectAdapter mCollectAdapter;
    private List<CollectBean> mCollects;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GET_COLLECT_COUNT:
                    Integer count = (Integer) msg.obj;
                    getCollectList(count);
                    break;
            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_collection_list;
    }

    @Override
    protected void init() {
        super.init();
        initToolbar(mToolbar, "收藏");
        mCollects = new ArrayList<>();
        mCollectAdapter = new CollectAdapter(this, R.layout.collect_item, mCollects);
        mCollectAdapter.setOnItemClickListener(new BaseRecycleViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("url", mCollects.get(position).getCollectUrl());
                goTo(WebActivity.class, bundle);
            }

            @Override
            public void onItemLongClick(View view, int position) {
            }
        });
        initRecyclerView();
        getCollectCount();
    }

    /**
     * 获取收藏列表
     */
    private void getCollectList(int count) {
        BmobQuery<CollectBean> query = new BmobQuery<CollectBean>();
        //查询playerName叫“比目”的数据
        query.addWhereEqualTo("userId", mApp.mUser.getObjectId());
        //返回50条数据，如果不加上这条语句，默认返回10条数据
        query.setLimit(count);
        //执行查询方法
        query.findObjects(new FindListener<CollectBean>() {
            @Override
            public void done(List<CollectBean> object, BmobException e) {
                if (e == null) {
                    mCollects.clear();
                    mCollects.addAll(object);
                    mCollectAdapter.notifyDataSetChanged();
                    mRefresh.setVisibility(View.GONE);
                    LogUtil.e("成功：");
                } else {
                    mCollects.clear();
                    mCollectAdapter.notifyDataSetChanged();
                    mRefresh.setVisibility(View.VISIBLE);
                    LogUtil.e("bmob失败：" + e.getMessage() + "," + e.getErrorCode());
                    showToast("获取失败");
                }
                hiddenProgressDialog();
            }
        });
    }

    /**
     * 获取收藏数目
     */
    public void getCollectCount() {
        showProgressDialog("获取收藏列表", null);
        BmobQuery<CollectBean> query = new BmobQuery<>();
        query.addWhereEqualTo("userId", mApp.mUser.getObjectId());
        query.count(CollectBean.class, new CountListener() {
            @Override
            public void done(Integer count, BmobException e) {
                if (e == null) {
                    Message msg = Message.obtain();
                    msg.what = GET_COLLECT_COUNT;
                    msg.obj = count;
                    mHandler.sendMessage(msg);
                    LogUtil.e("成功" + count);
                } else {
                    hiddenProgressDialog();
                    LogUtil.e("bmob失败：" + e.getMessage() + "," + e.getErrorCode());
                    showToast("获取失败");
                }
            }
        });
    }

    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mCollectAdapter);
    }


    @Override
    protected void onDestroy() {
        if (mHandler != null) {
            mHandler = null;
        }
        super.onDestroy();
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


    @OnClick(R.id.refresh)
    public void onViewClicked() {
        getCollectCount();
    }
}
