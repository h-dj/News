package com.example.h_dj.news.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.h_dj.news.utils.LogUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by H_DJ on 2017/5/16.
 */

public abstract class BaseFragment extends Fragment {

    private Unbinder unbinder;
    protected Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtil.e(this.getClass().getSimpleName() + "初始化");
        View inflate = inflater.inflate(getlayoutId(), container, false);
        unbinder = ButterKnife.bind(this, inflate);
        init();
        return inflate;
    }

    protected void init() {
    }

    protected abstract int getlayoutId();

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

    /**
     * 跳转
     *
     * @param mclass
     */
    public void goTo(Class mclass, Bundle bundle) {
        Intent intent = new Intent(getContext(), mclass);
        intent.putExtra("data", bundle);
        startActivity(intent);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }
}
