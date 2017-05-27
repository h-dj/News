package com.example.h_dj.news.fragment;

import android.view.View;
import android.widget.TextView;

import com.example.h_dj.news.R;
import com.example.h_dj.news.activity.LoginActivity;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by H_DJ on 2017/5/16.
 */

public class MyFragment extends BaseFragment {

    @BindView(R.id.setting)
    TextView mSetting;
    @BindView(R.id.profile_image)
    CircleImageView mProfileImage;
    @BindView(R.id.user_name)
    TextView mUserName;
    @BindView(R.id.reading)
    TextView mReading;
    @BindView(R.id.collection)
    TextView mCollection;
    @BindView(R.id.comment)
    TextView mComment;
    @BindView(R.id.gold)
    TextView mGold;
    @BindView(R.id.myMsg)
    TextView mMyMsg;
    @BindView(R.id.goldShop)
    TextView mGoldShop;
    @BindView(R.id.myTask)
    TextView mMyTask;
    @BindView(R.id.myWallet)
    TextView mMyWallet;
    @BindView(R.id.myEmail)
    TextView mMyEmail;

    @Override
    protected int getlayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    protected void init() {
        super.init();
    }


    @OnClick({R.id.setting, R.id.profile_image, R.id.user_name})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.setting:
                break;
            case R.id.profile_image:
            case R.id.user_name:
                goTo(LoginActivity.class, null);
                break;
        }
    }
}
