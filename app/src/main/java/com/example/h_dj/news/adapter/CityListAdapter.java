package com.example.h_dj.news.adapter;

import android.content.Context;

import com.example.h_dj.news.R;
import com.example.h_dj.news.base.BaseRecycleViewAdapter;
import com.example.h_dj.news.bean.AreaInfo;

import java.util.List;

/**
 * Created by H_DJ on 2017/6/11.
 */
public class CityListAdapter extends BaseRecycleViewAdapter {


    public CityListAdapter(Context context, int layoutId, List datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(MyViewHolder holder, Object o,int position) {
        AreaInfo cityName = (AreaInfo) o;
        holder.setText(R.id.cityName, cityName.getName());
    }


}
