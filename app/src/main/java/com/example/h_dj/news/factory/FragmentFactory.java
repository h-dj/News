package com.example.h_dj.news.factory;

import android.support.v4.app.Fragment;

import com.example.h_dj.news.R;
import com.example.h_dj.news.fragment.MyFragment;
import com.example.h_dj.news.fragment.NewsFragment;
import com.example.h_dj.news.fragment.ReadFragment;
import com.example.h_dj.news.fragment.RecoveryFragment;
import com.example.h_dj.news.fragment.VideoFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by H_DJ on 2017/5/16.
 * 工厂模式
 * 创建fragment
 */

public class FragmentFactory {

    private FragmentFactory() {
    }

    private static FragmentFactory mFragmentFactory;

    public static FragmentFactory getInstance() {
        if (mFragmentFactory == null) {
            synchronized (FragmentFactory.class) {
                if (mFragmentFactory == null) {
                    mFragmentFactory = new FragmentFactory();
                }
            }
        }
        return mFragmentFactory;
    }

    private Map<Integer, Fragment> mFragmentMap = new HashMap<>();

    public Fragment createFragment(int position) {
        Fragment fragment = null;
        fragment = mFragmentMap.get(position);
        if (fragment == null) {
            switch (position) {
                case R.id.News:
                    fragment = new NewsFragment();
                    break;
                case R.id.tab_read:
                    fragment = new ReadFragment();
                    break;
                case R.id.tab_video:
                    fragment = new VideoFragment();
                    break;
                case R.id.tab_recovery:
                    fragment = new RecoveryFragment();
                    break;
                case R.id.tab_my:
                    fragment = new MyFragment();
                    break;
            }
            if (fragment != null) {
                mFragmentMap.put(position, fragment);
            }
        }
        return fragment;
    }
}
