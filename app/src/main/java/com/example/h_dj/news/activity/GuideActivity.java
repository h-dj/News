package com.example.h_dj.news.activity;

import android.animation.Animator;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.example.h_dj.news.R;
import com.example.h_dj.news.base.BaseActivity;
import com.example.h_dj.news.utils.LogUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by H_DJ on 2017/5/15.
 */
public class GuideActivity extends BaseActivity {

    private static final int UPDATA_ANIMATION = 0x1111;
    @BindView(R.id.iv_01)
    ImageView mIv01;
    @BindView(R.id.musicIcon)
    ImageView mMusicIcon;
    @BindView(R.id.btn_go)
    Button mBtnGo;

    /**
     * 导航页的图片
     */
    private int[] images = new int[]{
            R.drawable.ad_new_version1_img1,
            R.drawable.ad_new_version1_img2,
            R.drawable.ad_new_version1_img3,
            R.drawable.ad_new_version1_img4,
            R.drawable.ad_new_version1_img5,
            R.drawable.ad_new_version1_img6,
            R.drawable.ad_new_version1_img7,
    };
    /**
     * 当前图片下标
     */
    private int count;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATA_ANIMATION:
                    startAnimation();
                    break;
            }
            super.handleMessage(msg);
        }
    };
    // 在GuideActivity.java类中添加如下代码：
    private MediaPlayer mMediaPlayer;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_guide;
    }

    @Override
    protected void init() {
        super.init();
        initData();
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.refresh_rotate);
        mMusicIcon.startAnimation(animation);
    }

    private void initData() {
        startAnimation();
    }

    private void startAnimation() {
        count++;
        count = count % images.length;
        mIv01.setImageResource(images[count]);
        mIv01.setScaleX(1.0f);
        mIv01.setScaleY(1.0f);

        mIv01.animate()
                .scaleX(1.2f)
                .scaleY(1.2f)
                .setDuration(3500)      // 动画执行时间是3.5秒
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mHandler.sendEmptyMessageDelayed(UPDATA_ANIMATION, 1500);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {
                    }
                }).start();
    }

    /**
     * 循环播放背景音乐
     */
    private void playBackgroundMusic() {
        try {
            AssetFileDescriptor fileDescriptor = getAssets().openFd("new_version.mp3");

            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setDataSource(fileDescriptor.getFileDescriptor(),
                    0L, fileDescriptor.getLength());
            mMediaPlayer.setLooping(true);        // 循环播放
            mMediaPlayer.setVolume(1.0f, 1.0f);   // 左声道音量 右声道音量
            mMediaPlayer.prepare();               // 缓冲文件
            mMediaPlayer.start();                 // 开始播放
            LogUtil.e(mMediaPlayer.isPlaying() + "");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Activity界面显示时调用
    @Override
    protected void onStart() {
        super.onStart();
        // 开始播放
        playBackgroundMusic();

    }

    // Activity界面退出时调用
    @Override
    protected void onStop() {
        // 释放MediaPlayer资源
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
        mIv01.animate().cancel();
        mMusicIcon.clearAnimation();
        mHandler.removeMessages(UPDATA_ANIMATION);
        super.onStop();
    }


    @OnClick(R.id.btn_go)
    public void onViewClicked() {
        goTo(MainActivity.class);
        this.finish();

    }
}
