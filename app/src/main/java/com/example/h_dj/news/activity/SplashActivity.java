package com.example.h_dj.news.activity;

import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;

/**
 * Created by H_DJ on 2017/5/15.
 */

public class SplashActivity extends BaseActivity {

    private static final int DELAYED = 5000;
    private static final int GOTO_MAINACTIVITY = 1;
    private static final int GOTO_GUIDEACTIVITY = 2;
    private static final String COUNT = "count";
    private int count = 0;//app启动次数
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GOTO_MAINACTIVITY:
                    goTo(MainActivity.class);
                    break;
                case GOTO_GUIDEACTIVITY:
                    goTo(GuideActivity.class);
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
            SplashActivity.this.finish();

        }
    };

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void init() {
        super.init();
        //获取app启动次数
        getCount();

        //判断软件是否是第一次启动
        if (count > 0) {
            mHandler.sendEmptyMessageDelayed(GOTO_MAINACTIVITY, DELAYED);
        } else {
            setCount();
            mHandler.sendEmptyMessageDelayed(GOTO_GUIDEACTIVITY, DELAYED);
        }
    }

    /**
     * 设置app启动次数
     */
    private void setCount() {
        SharedPreferences sp = getSharedPreferences(COUNT, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(COUNT, ++count);
        editor.commit();
    }

    /**
     * 获取app启动次数
     */
    private void getCount() {
        SharedPreferences sp = getSharedPreferences(COUNT, MODE_PRIVATE);
        count = sp.getInt(COUNT, 0);
    }


}
