package com.example.h_dj.news.adapter;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.h_dj.news.MainActivity;
import com.example.h_dj.news.R;
import com.example.h_dj.news.activity.GuideActivity;

import java.util.List;

/**
 * Created by H_DJ on 2017/5/15.
 */

public class GuidePagerAdapter extends PagerAdapter {
    private List<View> views;
    private Activity activity;

    public GuidePagerAdapter(List<View> views, Activity activity) {
        this.views = views;
        this.activity = activity;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views.get(position));
    }

    @Override
    public int getCount() {
        return (views == null) ? 0 : views.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(views.get(position), 0);
        //当滑动到最后一页的时候，监听按钮
        if (position == views.size() - 1) {
            TextView tvEnter = (TextView) container.findViewById(R.id.tv_guide_enter);

            tvEnter.setText("欢迎使用");
            tvEnter.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    ((GuideActivity) activity).goTo(MainActivity.class);
                    activity.finish();
                }
            });
        }
        return views.get(position);
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return (arg0 == arg1);
    }
}
