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
import com.example.h_dj.news.adapter.CityListAdapter;
import com.example.h_dj.news.base.BaseActivity;
import com.example.h_dj.news.base.BaseRecycleViewAdapter;
import com.example.h_dj.news.bean.AreaInfo;
import com.example.h_dj.news.presenter.ILoadNewsPresenter;
import com.example.h_dj.news.presenter.Impl.LoadNewsPresenterImpl;

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
    private int currentLevel = 0;//当前区域等级，
    private final static int PROVINCE_LEVEL = 1;//省级-1，
    private final static int CITY_LEVEL = 2;//市级-2
    private final static int COUNTRY_LEVEL = 3;//，县级-3
    /**
     * 省份列表
     */
    private List<AreaInfo> provinces = new ArrayList<>();
    /**
     * 城市列表
     */
    private List<AreaInfo> citys = new ArrayList<>();
    /**
     * 市级列表
     */
    private List<AreaInfo> countrys = new ArrayList<>();
    /**
     * 选中的省份
     */
    private AreaInfo selectProvince;
    /**
     * 选中的的城市
     */
    private AreaInfo selectCity;
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
        mPresenter = new LoadNewsPresenterImpl(this);
        queryProvinces();
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
            public void onItemClick(View view, int pos) {
                if (currentLevel == PROVINCE_LEVEL) {
                    selectProvince = mCityList.get(pos);
                    queryCitys();
                } else if (currentLevel == CITY_LEVEL) {
                    selectCity = mCityList.get(pos);
                    queryCountrys();
                } else if (currentLevel == COUNTRY_LEVEL) {
                    AreaInfo area = mCityList.get(pos);
                    EventBus.getDefault().post(new MyMessageEvent<>(area, MyMessageEvent.MSG_FROM_LOAD_WEATHER_SELECTED_AREA_SUCCESS));
                    finish();
                }
            }

            @Override
            public void onItemLongClick(View view, int position) {
            }
        });
        mRecyclerView.setAdapter(mCityListAdapter);
    }

    /**
     * 查询县
     */
    private void queryCountrys() {
        currentLevel = COUNTRY_LEVEL;
        int cityCode = selectCity.getId();
        int provinceCode = selectProvince.getId();
        String url = Contracts.province + provinceCode + "/" + cityCode;
        mPresenter.queryArea(url, "county");
    }

    /**
     * 查询市
     */
    private void queryCitys() {
        currentLevel = CITY_LEVEL;
        int provinceCode = selectProvince.getId();
        String url = Contracts.province + provinceCode;
        mPresenter.queryArea(url, "city");
    }

    /**
     * 查询省
     */
    private void queryProvinces() {
        currentLevel = PROVINCE_LEVEL;
        String url = Contracts.province;
        mPresenter.queryArea(url, "province");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MyMessageEvent event) {
        int fromMsg = event.getFromMsg();
        switch (fromMsg) {
            case MyMessageEvent.MSG_FROM_LOAD_WEATHER_AREA_SUCCESS:
                int size = mCityList.size();
                mCityListAdapter.notifyDataSetChanged();
                mCityList.clear();

                mCityListAdapter.notifyItemRangeRemoved(0, size);
                if (currentLevel == PROVINCE_LEVEL) {
                    provinces = (List<AreaInfo>) event.getT();
                    mCityList.addAll(provinces);
                } else if (currentLevel == CITY_LEVEL) {
                    citys = (List<AreaInfo>) event.getT();
                    mCityList.addAll(citys);
                } else if (currentLevel == COUNTRY_LEVEL) {
                    countrys = (List<AreaInfo>) event.getT();
                    mCityList.addAll(countrys);
                }
                mCityListAdapter.notifyItemRangeInserted(0, mCityList.size());
                mRecyclerView.scrollToPosition(0);
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case android.R.id.home:
                if (currentLevel == COUNTRY_LEVEL) {
                    queryCitys();
                } else if (currentLevel == CITY_LEVEL) {
                    queryProvinces();
                } else if (currentLevel == PROVINCE_LEVEL) {
                    this.finish();
                    return true;
                }
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
