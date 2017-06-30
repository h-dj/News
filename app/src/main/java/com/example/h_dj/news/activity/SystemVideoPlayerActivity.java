package com.example.h_dj.news.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.h_dj.news.R;
import com.example.h_dj.news.base.BaseActivity;
import com.example.h_dj.news.bean.MediaItems;
import com.example.h_dj.news.utils.DecodeTimeUtil;
import com.example.h_dj.news.utils.LogUtil;
import com.example.h_dj.news.utils.Utils;
import com.example.h_dj.news.widget.VideoView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * Created by H_DJ on 2017/4/5.
 */

public class SystemVideoPlayerActivity extends BaseActivity implements View.OnClickListener {

    private DecodeTimeUtil decodeTimeUtil;
    private VideoView mVideoView;
    private Intent mIntent;
    private TextView tvVideoName;
    private ImageView batteryStatus;
    private TextView tvSystemTime;
    private Button btnVideoVoice;
    private SeekBar seekbarVoice;
    private Button btnSwitchPlay;
    private TextView tvVideoTime;
    private SeekBar seekbarTime;
    private TextView tvDuration;
    private Button btnVideoExit;
    private Button btnVideoPrevious;
    private Button btnVideoPlayPause;
    private Button btnVideoNext;
    private Button btnVideoFullscreen;
    private RelativeLayout media_controller;
    private LinearLayout mLinearLayoutBuffer;
    private TextView mTextViewNetSpeed;
    private boolean isUseSystem = false;
    /**
     * 前一进度
     */
    private int preCurrentTime;
    private Handler handler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case HIDDEN_MEDIACONTROLLER:
                    hiddenMediaController();
                    break;
                case PROGRESS:
                    //1. 获取当前进度
                    int currentTime = mVideoView.getCurrentPosition();
                    seekbarTime.setProgress(currentTime);
                    //2. 设置文本时间进度
                    tvVideoTime.setText(decodeTimeUtil.stringForTime(currentTime));

                    /**
                     *设置缓冲进度
                     */
                    if (isNetUri) {
                        //获取缓冲进度
                        int buffer = mVideoView.getBufferPercentage();
                        int totalBuffer = buffer * seekbarTime.getMax();
                        int secondaryProgress = totalBuffer / 100;
                        LogUtil.e("缓冲" + secondaryProgress + "最大：" + seekbarTime.getMax());
                        seekbarTime.setSecondaryProgress(secondaryProgress);
                    } else {
                        seekbarTime.setSecondaryProgress(0);
                    }
                        //设置缓冲
//                    if (!isUseSystem && mVideoView.isPlaying()) {
//                        int buffer = currentTime - preCurrentTime;
//                        if (buffer < 500) {
//                            mLinearLayoutBuffer.setVisibility(View.VISIBLE);
//                        } else {
//                            mLinearLayoutBuffer.setVisibility(View.GONE);
//                        }
//                    } else {
//                        mLinearLayoutBuffer.setVisibility(View.GONE);
//                    }
                    preCurrentTime = currentTime;
                    //设置系统时间
                    setCurrentSystemTime();
                    LogUtil.e("更新进度");

                    //3.移除消息，重新发送
                    handler.removeMessages(PROGRESS);
                    handler.sendEmptyMessageDelayed(PROGRESS, 1000);
                    break;

            }
            return true;
        }
    });
    private static final int PROGRESS = 0x1001;  //进度
    private static final int HIDDEN_MEDIACONTROLLER = 0x1002; //隐藏控制条
    /**
     * 默认屏幕大小
     */
    private static final int DEFAULT_SCREEN = 0X1003;
    /**
     * 全屏幕
     */
    private static final int FULL_SCREEN = 0X1004;
    private MyReceiver batteryReceiver;
    private ArrayList<MediaItems> mMediaItems;//视频列表
    private int position = 0; //视频列表的id

    private GestureDetector mDetector;//手势识别器
    private boolean isHiddenMediaController = false;
    private boolean isFullScreen = false;
    /**
     * 屏幕宽
     */
    private int screenWidth = 0;
    /**
     * 屏幕高
     */
    private int screenHeight = 0;
    /**
     * 视频宽
     */
    private int VideoWidth = 0;
    /**
     * 视频高
     */
    private int VideoHeight = 0;

    /**
     * 系统服务--调节音量
     */
    private AudioManager mAudioManager;

    /**
     * 最大音量
     */
    private int MaxVolumn = 0;
    /**
     * 当前音量
     */
    private int currentVolumn = 0;
    private boolean isMult = false;
    private Uri uri;
    /**
     * 是否是网络资源
     */
    private boolean isNetUri;


    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2017-04-11 20:41:44 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        media_controller = (RelativeLayout) findViewById(R.id.media_controller);
        mVideoView = (VideoView) findViewById(R.id.vv_video);
        tvVideoName = (TextView) findViewById(R.id.tv_video_name);
        batteryStatus = (ImageView) findViewById(R.id.battery_status);
        tvSystemTime = (TextView) findViewById(R.id.tv_system_time);
        btnVideoVoice = (Button) findViewById(R.id.btn_video_voice);
        seekbarVoice = (SeekBar) findViewById(R.id.seekbar_voice);
        btnSwitchPlay = (Button) findViewById(R.id.btn_switch_play);
        tvVideoTime = (TextView) findViewById(R.id.tv_video_time);
        seekbarTime = (SeekBar) findViewById(R.id.seekbar_time);
        tvDuration = (TextView) findViewById(R.id.tv_duration);
        btnVideoExit = (Button) findViewById(R.id.btn_video_exit);
        btnVideoPrevious = (Button) findViewById(R.id.btn_video_previous);
        btnVideoPlayPause = (Button) findViewById(R.id.btn_video_play_pause);
        btnVideoNext = (Button) findViewById(R.id.btn_video_next);
        btnVideoFullscreen = (Button) findViewById(R.id.btn_video_fullscreen);


        btnVideoVoice.setOnClickListener(this);
        btnSwitchPlay.setOnClickListener(this);
        btnVideoExit.setOnClickListener(this);
        btnVideoPrevious.setOnClickListener(this);
        btnVideoPlayPause.setOnClickListener(this);
        btnVideoNext.setOnClickListener(this);
        btnVideoFullscreen.setOnClickListener(this);

        seekbarTime.setOnSeekBarChangeListener(new MyVideoSeekBarChangeListener());
        seekbarVoice.setOnSeekBarChangeListener(new MyVoiceSeekBarChangeListener());
        /**
         * 设置音量的seekbar最大值
         */
        seekbarVoice.setMax(MaxVolumn);
        seekbarVoice.setProgress(currentVolumn);
    }

    /**
     * Handle button click events<br />
     * <br />
     * Auto-created on 2017-04-11 20:41:44 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    @Override
    public void onClick(View v) {
        if (v == btnVideoVoice) {
            // Handle clicks for btnVideoVoice
            isMult = !isMult;
            setVoice(currentVolumn, isMult);
        } else if (v == btnSwitchPlay) {
            // Handle clicks for btnSwitchPlay
        } else if (v == btnVideoExit) {
            // Handle clicks for btnVideoExit
            this.finish();
        } else if (v == btnVideoPrevious) {
            setBtnPreStatus();
        } else if (v == btnVideoPlayPause) {
            setStartAndPause();

        } else if (v == btnVideoNext) {
            setBtnNextStatus();

        } else if (v == btnVideoFullscreen) {
            // Handle clicks for btnVideoFullscreen
            setVideoSize();
        }
        preventHiddenMediaController();


    }

    /**
     * 阻止播放面板消失
     */
    private void preventHiddenMediaController() {
        //为了防止用户在操作控制面板的时候，控制面板隐藏需要移除消息，在重发
        handler.removeMessages(HIDDEN_MEDIACONTROLLER);
        handler.sendEmptyMessageDelayed(HIDDEN_MEDIACONTROLLER, 4000);
    }

    /**
     * 设置播放按钮
     */
    private void setStartAndPause() {
        // Handle clicks for btnVideoPlayPause

        if (mVideoView.isPlaying()) {
            //1. 设置seekbar的按钮变换
            btnVideoPlayPause.setBackgroundResource(R.drawable.btn_video_play_selector);
            //2. 停止视频
            mVideoView.pause();
        } else {
            //1. 设置seekbar的按钮变换
            btnVideoPlayPause.setBackgroundResource(R.drawable.btn_video_play_pause_selector);
            //2. 开始视频
            mVideoView.start();
        }
    }

    /**
     * 设置上一个视频按钮的状态
     */
    private void setBtnPreStatus() {
        if (mMediaItems != null && mMediaItems.size() > 0) {
            position--;
            if (position >= 0) {
                setData();
            } else {
                position = 0;
            }
        } else if (uri != null) {
            setButtonEnable(false);
        }
    }

    /**
     * 设置下一个视频按钮的状态
     */
    private void setBtnNextStatus() {
        if (mMediaItems != null && mMediaItems.size() > 0) {
            position++;
            if (position < mMediaItems.size()) {
                setData();
            } else {
                position = mMediaItems.size() - 1;
            }
        } else if (uri != null) {
            setButtonEnable(false);
        }
    }

    public void setButtonStatus() {
        if (mMediaItems != null && mMediaItems.size() > 0) {
            if (mMediaItems.size() == 1) {
                //如果只有一个视频；就设置按钮为不可点击
                setButtonEnable(false);
            } else if (mMediaItems.size() == 2) {
                if (position == 0) {
                    //设置前一个视频不可点击
                    btnVideoPrevious.setEnabled(false);
                    btnVideoPrevious.setBackgroundResource(R.drawable.btn_video_previous_gray_selector);
                    //后一个视频可点击
                    btnVideoNext.setEnabled(true);
                    btnVideoNext.setBackgroundResource(R.drawable.btn_video_next_selector);
                } else if (position == mMediaItems.size() - 1) {
                    //设置前一个视频可点击
                    btnVideoPrevious.setEnabled(true);
                    btnVideoPrevious.setBackgroundResource(R.drawable.btn_video_previous_selector);
                    //后一个视频不可点击
                    btnVideoNext.setEnabled(false);
                    btnVideoNext.setBackgroundResource(R.drawable.btn_video_next_gray_selector);
                }
            } else {
                if (position == 0) {
                    //设置前一个视频不可点击
                    btnVideoPrevious.setEnabled(false);
                    btnVideoPrevious.setBackgroundResource(R.drawable.btn_video_previous_gray_selector);
                } else if (position == mMediaItems.size() - 1) {
                    //后一个视频不可点击
                    btnVideoNext.setEnabled(false);
                    btnVideoNext.setBackgroundResource(R.drawable.btn_video_next_gray_selector);
                } else {
                    setButtonEnable(true);
                }
            }
        } else if (uri != null) {
            setButtonEnable(false);
        }
    }

    //设置按钮为是否可以点击
    private void setButtonEnable(boolean b) {
        btnVideoNext.setEnabled(b);
        btnVideoPrevious.setEnabled(b);
        if (b) {
            btnVideoNext.setBackgroundResource(R.drawable.btn_video_next_selector);
            btnVideoPrevious.setBackgroundResource(R.drawable.btn_video_previous_selector);
        } else {
            btnVideoNext.setBackgroundResource(R.drawable.btn_video_next_gray_selector);
            btnVideoPrevious.setBackgroundResource(R.drawable.btn_video_previous_gray_selector);
        }

    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_systemplayer;
    }

    @Override
    public void init() {
        super.init();
        decodeTimeUtil = new DecodeTimeUtil();
        setGestureDetector();
        getData();
        //监听电量
        registerReceiver();
        findViews();
        setData();
        initVideoView();
    }

    /**
     * 设置手势识别器
     */
    private void setGestureDetector() {
        mDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {

            /**
             * 长按
             * 播放暂停
             * @param e
             */
            @Override
            public void onLongPress(MotionEvent e) {
                setStartAndPause();
            }

            @Override
            public boolean onDown(MotionEvent e) {
                preventHiddenMediaController();
                return true;
            }

            /**
             * 双击
             * @param e
             * @return
             */
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                setVideoSize();
                return super.onDoubleTap(e);
            }

            /**
             * 单击
             * @param e
             * @return
             */
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                if (isHiddenMediaController) {
                    showMediaController();
                    //发送隐藏媒体控制条的消息
                    handler.sendEmptyMessageDelayed(HIDDEN_MEDIACONTROLLER, 4000);
                } else {
                    hiddenMediaController();
                    //移除隐藏媒体控制面板的消息
                    handler.removeMessages(HIDDEN_MEDIACONTROLLER);
                }
                return true;
            }

        });

        /**
         * 获取屏幕的的大小
         *
         */
//        //以前的方法
//        screenWidth = getWindowManager().getDefaultDisplay().getWidth();
//        screenHeight = getWindowManager().getDefaultDisplay().getHeight();

        //新的方法
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;

        /**
         * 设置音量
         */
        mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        MaxVolumn = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC); //最大音量
        currentVolumn = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);//当前音量
    }

    private void setVideoSize() {
        if (isFullScreen) {
            setVideoType(DEFAULT_SCREEN); //设置默认视频大小
        } else {
            setVideoType(FULL_SCREEN);
        }
    }

    /**
     * 获取传递过来的数据
     */
    private void getData() {
        uri = getIntent().getData();
        mMediaItems = (ArrayList<MediaItems>) getIntent().getSerializableExtra("videoInfo");
        position = getIntent().getIntExtra("position", 0);

    }


    /**
     * 设置数据
     */
    private void setData() {
        if (mMediaItems != null && mMediaItems.size() > 0) {
            MediaItems mediaItems = mMediaItems.get(position);
            //设置标题
            tvVideoName.setText(mediaItems.getName());
            //判断是否是网络资源
            isNetUri = Utils.getInstance().isNetUri(mediaItems.getData());
            //设置视频播放路径
            mVideoView.setVideoPath(mediaItems.getData());
        } else if (uri != null) {
            tvVideoName.setText(uri.toString());
            isNetUri = Utils.getInstance().isNetUri(uri.toString());
            mVideoView.setVideoPath(uri.toString());
        } else {
            Toast.makeText(SystemVideoPlayerActivity.this, "视频播放路径错误哦！", Toast.LENGTH_SHORT).show();
            setButtonEnable(false);
        }
        setButtonStatus();
        LogUtil.e("isNetUri:" + isNetUri);
    }

    /**
     * //动态注册广播；监听电量
     */
    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        batteryReceiver = new MyReceiver();
        registerReceiver(batteryReceiver, intentFilter);
    }

    /**
     *
     */
    private class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() == Intent.ACTION_BATTERY_CHANGED) {
                LogUtil.e("监听电量");
                int level = intent.getIntExtra("level", 0);
                if (level <= 0) {
                    batteryStatus.setBackgroundResource(R.drawable.ic_battery_0);
                } else if (level <= 20) {
                    batteryStatus.setBackgroundResource(R.drawable.ic_battery_20);
                } else if (level <= 40) {
                    batteryStatus.setBackgroundResource(R.drawable.ic_battery_40);
                } else if (level <= 60) {
                    batteryStatus.setBackgroundResource(R.drawable.ic_battery_60);
                } else if (level <= 80) {
                    batteryStatus.setBackgroundResource(R.drawable.ic_battery_80);
                } else if (level <= 100) {
                    batteryStatus.setBackgroundResource(R.drawable.ic_battery_100);
                } else {
                    batteryStatus.setBackgroundResource(R.drawable.ic_battery_100);
                }
            }

        }
    }

    /**
     * 设置系统时间
     */
    private void setCurrentSystemTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String time = sdf.format(new Date());
        tvSystemTime.setText(time);
    }

    private void initVideoView() {
        //设置准备播放监听
        mVideoView.setOnPreparedListener(new MyPreparedListener());

        //设置播放错误监听
        mVideoView.setOnErrorListener(new MyErrorListener());

        //设置播放完成监听
        mVideoView.setOnCompletionListener(new MyCompletionListener());

        if (isUseSystem) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                isUseSystem = true;
                mVideoView.setOnInfoListener(new MyInfoListener());
            }
        }

        //设置播放地址
//       mVideoView.setVideoURI(mIntent.getData());

        //设置视频控制控件
//       mVideoView.setMediaController(new MediaController(this));


    }

    /**
     * 监听视频播放的缓冲状态
     */
    private class MyInfoListener implements MediaPlayer.OnInfoListener {
        @Override
        public boolean onInfo(MediaPlayer mp, int what, int extra) {
            switch (what) {
                case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                    //显示缓冲提示
                    // mLinearLayoutBuffer.setVisibility(View.VISIBLE);
                    break;
                case MediaPlayer.MEDIA_INFO_BUFFERING_END:
//                   隐藏缓冲提示
                    //   mLinearLayoutBuffer.setVisibility(View.GONE);
                    break;
            }
            return true;
        }
    }

    private class MyPreparedListener implements MediaPlayer.OnPreparedListener {

        @Override
        public void onPrepared(MediaPlayer mp) {
            VideoWidth = mp.getVideoWidth();
            VideoHeight = mp.getVideoHeight();
            mp.start();
            // 1.设置seekbar的Max
            int duration = mVideoView.getDuration();
            seekbarTime.setMax(duration);

            //2. 更新seekbar
            handler.sendEmptyMessage(PROGRESS);

            //3. 设置总时长文本显示
            tvDuration.setText(decodeTimeUtil.stringForTime(duration));
            LogUtil.e("视频准备播放");


            /**
             * 设置VideoView的大小
             */
//            mVideoView.setVideoSize(mp.getVideoWidth(), mp.getVideoHeight());

            setVideoType(DEFAULT_SCREEN);
        }
    }

    /**
     * 视频播放类型 : 全屏或默认大小
     *
     * @param defaultScreen
     */
    private void setVideoType(int defaultScreen) {
        switch (defaultScreen) {
            case DEFAULT_SCREEN:
                //1.设置Video为默认大小
                //屏幕的宽高
                int width = screenWidth;
                int height = screenHeight;

                /**
                 * 视频的原始大小
                 */
                int mVideoWidth = VideoWidth;
                int mVideoHeight = VideoHeight;
                // for compatibility, we adjust size based on aspect ratio
                if (mVideoWidth * height < width * mVideoHeight) {
                    //Log.i("@@@", "image too wide, correcting");
                    width = height * mVideoWidth / mVideoHeight;
                } else if (mVideoWidth * height > width * mVideoHeight) {
                    //Log.i("@@@", "image too tall, correcting");
                    height = width * mVideoHeight / mVideoWidth;
                }
                mVideoView.setVideoSize(width, height);
                //2. 设置按钮为全屏样式
                btnVideoFullscreen.setBackgroundResource(R.drawable.btn_video_fullscreen_selector);

                //3. 设置标识为false
                isFullScreen = false;
                break;
            case FULL_SCREEN:

                //1.设置Video为全屏
                mVideoView.setVideoSize(screenWidth, screenHeight);
                //2. 设置按钮为全屏样式
                btnVideoFullscreen.setBackgroundResource(R.drawable.btn_video_default_selector);
                //3. 设置标识为true
                isFullScreen = true;
                break;
        }


    }

    private class MyErrorListener implements MediaPlayer.OnErrorListener {

        @Override
        public boolean onError(MediaPlayer mp, int what, int extra) {
            LogUtil.e("视频播放错误" + what + " " + extra);
            return false;
        }
    }

    private class MyCompletionListener implements MediaPlayer.OnCompletionListener {

        @Override
        public void onCompletion(MediaPlayer mp) {
            //设置播放按钮为播放状态
            btnVideoPlayPause.setBackgroundResource(R.drawable.btn_video_play_selector);
            playNextVideo();
            LogUtil.e("视频播放完毕");
        }
    }

    /**
     * 播放下一个视频
     */
    private void playNextVideo() {
        if (mMediaItems != null && mMediaItems.size() > 0) {
            position++;
            if (position < mMediaItems.size()) {
                MediaItems mediaItems = mMediaItems.get(position);
                //设置视频标题
                tvVideoName.setText(mediaItems.getName());
                isNetUri = Utils.getInstance().isNetUri(mediaItems.getData());
                //设置播放资源
                mVideoView.setVideoPath(mediaItems.getData());
                //设置下一个按钮的状态
                setBtnNextStatus();
            } else if (uri != null) {
                finish();
            }
        }
    }

    private class MyVideoSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {


        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (fromUser) {
                mVideoView.seekTo(progress);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            handler.removeMessages(HIDDEN_MEDIACONTROLLER);
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            handler.sendEmptyMessageDelayed(HIDDEN_MEDIACONTROLLER, 4000);
        }
    }


    /**
     * 按下时的Y
     */
    private float startY;
    //滑动的范围；屏幕的高
    private float tounRang;
    //.当前的音量
    private int vol;

    /**
     * 触摸事件
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //.设置手势事件传递
        mDetector.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                handler.removeMessages(HIDDEN_MEDIACONTROLLER);
                //1. 按下时获取Y坐标
                startY = event.getY();
                //2. 获取当前的音量
                vol = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                // 3. 获取屏幕宽高中的最小值
                tounRang = Math.min(screenHeight, screenWidth);
                break;
            case MotionEvent.ACTION_MOVE:
                //4. 获取滑动结束的Y坐标
                float endY = event.getY();
                //滑动的距离
                float distance = startY - endY;
                //5. 获取增加的音量
                float addVoice = (distance / tounRang) * MaxVolumn;
                int voice = (int) Math.min(Math.max(addVoice + vol, 0), MaxVolumn);
                LogUtil.i(voice + "");
                if (addVoice != 0) {
                    isMult = false;
                    setVoice(voice, isMult);
                }
                break;
            case MotionEvent.ACTION_UP:
                handler.sendEmptyMessageDelayed(HIDDEN_MEDIACONTROLLER, 4000);
                break;
            default:
                return super.onTouchEvent(event);
        }

        return true;
    }


    /**
     * 设置媒体控制器显示
     */
    public void showMediaController() {
        isHiddenMediaController = false;
        media_controller.setVisibility(View.VISIBLE);
    }

    public void hiddenMediaController() {
        isHiddenMediaController = true;
        media_controller.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        //反注册广播
        if (batteryReceiver != null) {
            unregisterReceiver(batteryReceiver);
            batteryReceiver = null;
        }

        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }

        super.onDestroy();
        LogUtil.e("onDestroy");
    }

    /**
     * 设置声音的Seekbar
     */
    private class MyVoiceSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (fromUser) {
                if (progress > 0) {
                    isMult = false;
                } else {
                    isMult = true;
                }
                setVoice(progress, isMult);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            handler.removeMessages(HIDDEN_MEDIACONTROLLER);
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            handler.sendEmptyMessageDelayed(HIDDEN_MEDIACONTROLLER, 4000);
        }

    }

    /**
     * 设置音量
     *
     * @param progress
     */
    private void setVoice(int progress, boolean isMult) {
        if (isMult) {
            seekbarVoice.setProgress(0);
            mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
        } else {
            currentVolumn = progress;
            seekbarVoice.setProgress(currentVolumn);
            mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currentVolumn, 0);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            preventHiddenMediaController();
            --currentVolumn;
            setVoice(currentVolumn, false);
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            preventHiddenMediaController();
            ++currentVolumn;
            setVoice(currentVolumn, false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
