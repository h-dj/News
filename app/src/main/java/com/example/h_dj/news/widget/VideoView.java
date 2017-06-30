package com.example.h_dj.news.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

/**
 * Created by H_DJ on 2017/4/20.
 */

public class VideoView extends android.widget.VideoView {

    /**
     * 创建对象时调用
     *
     * @param context
     */
    public VideoView(Context context) {
        this(context, null);
    }

    /**
     * 在布局中使用时调用
     *
     * @param context
     * @param attrs
     */
    public VideoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * 设置自定义样式时，调用
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public VideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);//当重新测量时调用此方法，保存所测量的宽高
    }

    public void setVideoSize(int width, int height) {
        ViewGroup.LayoutParams layoutParams = getLayoutParams();//获取布局参数
        layoutParams.width = width;
        layoutParams.height = height;
        setLayoutParams(layoutParams);
    }
}
