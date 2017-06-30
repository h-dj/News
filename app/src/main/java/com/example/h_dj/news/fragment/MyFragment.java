package com.example.h_dj.news.fragment;

import android.view.View;
import android.widget.TextView;

import com.example.h_dj.news.Message.MyMessageEvent;
import com.example.h_dj.news.R;
import com.example.h_dj.news.activity.CollectionActivity;
import com.example.h_dj.news.activity.LoginActivity;
import com.example.h_dj.news.activity.SettingActivity;
import com.example.h_dj.news.base.BaseFragment;
import com.example.h_dj.news.bean.User;
import com.example.h_dj.news.utils.LogUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
        EventBus.getDefault().register(this);
        initUserInfo();
    }

    @Override
    public void onResume() {
        super.onResume();
        initUserInfo();
    }

    /**
     * 初始化用户
     * 判断是否已登陆
     */
    private void initUserInfo() {
        if (mApp.checkLogin()) {
            LogUtil.e("当前用户id:" + mApp.mUser.getObjectId());
            mUserName.setText(mApp.mUser.getUsername());
        } else {
            mUserName.setText(getString(R.string.login_name));
        }
    }


    @OnClick({R.id.setting, R.id.profile_image, R.id.user_name, R.id.collection, R.id.comment})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.setting:
                goTo(SettingActivity.class, null);
                break;
            case R.id.profile_image:
            case R.id.user_name:
                if (mApp.mUser != null) {

                } else {
                    goTo(LoginActivity.class, null);
                }
                break;
            case R.id.collection:
                if (mApp.checkLogin()) {
                    goTo(CollectionActivity.class, null);
                } else {
                    showToast("请先登录");
                }
            case R.id.comment:

                break;
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MyMessageEvent event) {
        int fromMsg = event.getFromMsg();
        switch (fromMsg) {
            case MyMessageEvent.MSG_FROM_LOGIN:
                User user = (User) event.getT();
                mUserName.setText(user.getUsername());
                break;
        }
    }


    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }
}
