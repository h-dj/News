package com.example.h_dj.news.activity;

import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.example.h_dj.news.Contracts;
import com.example.h_dj.news.Message.MyMessageEvent;
import com.example.h_dj.news.R;
import com.example.h_dj.news.base.BaseRecycleViewAdapter;
import com.example.h_dj.news.adapter.CityListAdapter;
import com.example.h_dj.news.base.BaseActivity;
import com.example.h_dj.news.bean.AreaInfo;
import com.example.h_dj.news.presenter.ILoadNewsPresenter;
import com.example.h_dj.news.presenter.Impl.LoadNewsPresenterImpl;
import com.example.h_dj.news.utils.LogUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by H_DJ on 2017/6/11.
 */
public class ChooseAreaActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.areaLayout)
    RecyclerView mRecyclerView;

    private CityListAdapter mCityListAdapter;
    private List<AreaInfo> mCityList;
    private int areaLevel = 0;//当前区域，省级-1，市级-2，县级-3
    private String url = null;
    private ILoadNewsPresenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_choose_area;
    }

    @Override
    protected void init() {
        super.init();
        EventBus.getDefault().register(this);
        Intent intent = getIntent();
        String cityName = null;
        if (intent != null) {
            cityName = intent.getBundleExtra("data").getString("currentCity");
        }
        initToolbar(mToolbar, String.format("当前城市：%s", cityName));

        initRecyclerView();

        mPresenter = new LoadNewsPresenterImpl(this, null);
        mPresenter.loadProvince();//下载
    }

    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mCityList = new ArrayList<>();
        mCityListAdapter = new CityListAdapter(this, R.layout.city_item, mCityList);
        mCityListAdapter.setOnItemClickListener(new BaseRecycleViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                AreaInfo areaInfo = mCityList.get(position);
                LogUtil.e(areaInfo.getName() + areaLevel);
                if (areaLevel == 3) {
                    EventBus.getDefault().post(new MyMessageEvent<>(areaInfo, MyMessageEvent.MSG_FROM_LOAD_WEATHER_SELECTED_AREA_SUCCESS));
                    ChooseAreaActivity.this.finish();
                    return;
                } else if (areaLevel == 2) {
                    url = url + "/" + areaInfo.getId();
                    LogUtil.e(url);
                    mPresenter.loadCounty(url);
                } else if (areaLevel == 1) {
                    url = Contracts.province + areaInfo.getId();
                    LogUtil.e(url);
                    mPresenter.loadCity(url);
                }

            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        mRecyclerView.setAdapter(mCityListAdapter);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MyMessageEvent event) {
        int fromMsg = event.getFromMsg();
        switch (fromMsg) {
            case MyMessageEvent.MSG_FROM_LOAD_WEATHER_AREA_SUCCESS:
                areaLevel++;
                List<AreaInfo> infos = (List<AreaInfo>) event.getT();
                mCityList.clear();
                mCityList.addAll(infos);
                mCityListAdapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case android.R.id.home:
                this.finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        if (mPresenter != null) {
            mPresenter = null;
        }
        super.onDestroy();
    }
}
