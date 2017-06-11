package com.example.h_dj.news.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import com.example.h_dj.news.bean.Temperature;
import com.example.h_dj.news.bean.TemperaturePoint;
import com.example.h_dj.news.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by H_DJ on 2017/6/1.
 */

public class WeatherView extends View {
    private Context mContext;
    private Paint mPaint;//画笔
    private List<TemperaturePoint> mTemperaturePoints;//温度的点
    private List<Temperature> mTemperatures; //温度
    private int Radius = 10;//点的半径

    /**
     * 屏幕的宽高
     */
    private int screenWidth;
    private int screenHight;
    /**
     * 画线的宽
     */
    private float lineWidth = 10;
    /**
     * 白天曲线的颜色
     */
    private String dWeatherColor = "#E91E63";
    /**
     * 晚上曲线的颜色
     */
    private String nWeatherColor = "#F8BBD0";

    public WeatherView(Context context) {
        this(context, null);
    }

    public WeatherView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WeatherView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        getScreentPixel();
        initPaint();
        mTemperatures = new ArrayList<>();
        mTemperaturePoints = new ArrayList<>();
    }


    /**
     * 获取屏幕宽高
     */
    private void getScreentPixel() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        screenHight = displayMetrics.heightPixels;
    }

    /**
     * 初始化点
     */
    public WeatherView initPoint() {
        /**
         * 每天天气的宽
         */
        int width = screenWidth / 3;
        mTemperaturePoints.clear();
        for (int i = 0; i < mTemperatures.size(); i++) {
            int x = width * i + width / 2;
            int dy = (50 - mTemperatures.get(i).dayTemperature) * 10;
            int ny = (50 - mTemperatures.get(i).nightTemperature) * 10;
            mTemperaturePoints.add(new TemperaturePoint(x, dy, ny));
            LogUtil.e("point:" + i + ":" + mTemperaturePoints.get(i).toString());
        }
        return this;
    }

    /**
     * 初始化温度数据
     */
    public WeatherView initTemperature(List<Temperature> temperatures) {
        mTemperatures.clear();
        mTemperatures.addAll(temperatures);
        return this;
//        Temperature t1 = new Temperature(34, 18, "06/01");
//        Temperature t2 = new Temperature(35, 16, "06/02");
//        Temperature t3 = new Temperature(29, 16, "06/03");
//        Temperature t4 = new Temperature(23, 13, "06/04");
//        mTemperatures.clear();
//        mTemperatures.add(t1);
//        mTemperatures.add(t2);
//        mTemperatures.add(t3);
//        mTemperatures.add(t4);
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        /**
         * 画点
         */
        drawPoint(canvas);
        /**
         * 画线
         */
        drawLine(canvas);
        /**
         * 画温度值
         */
        drawText(canvas);
        super.onDraw(canvas);
    }

    /**
     * 画温度值
     *
     * @param canvas
     */
    private void drawText(Canvas canvas) {
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setTextSize(30);
        mPaint.setStrokeWidth(0);
        mPaint.setColor(Color.parseColor("#ffffff"));
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);

        for (int i = 0; i < mTemperaturePoints.size(); i++) {
            LogUtil.e(mTemperaturePoints.size() + "::");
            Temperature temperature = mTemperatures.get(i);
            TemperaturePoint point = mTemperaturePoints.get(i);
            canvas.drawText(temperature.dayTemperature + " ℃", point.x, point.dy - 50, mPaint);
            canvas.drawText(temperature.nightTemperature + " ℃", point.x, point.ny + 50, mPaint);
        }

    }

    /**
     * 画线
     *
     * @param canvas
     */
    private void drawLine(Canvas canvas) {
        Path dPath = new Path();
        Path nPath = new Path();
        for (int i = 0; i < mTemperaturePoints.size(); i++) {
            TemperaturePoint point = mTemperaturePoints.get(i);
            if (i == 0) {
                dPath.moveTo(point.x, point.dy);
                nPath.moveTo(point.x, point.ny);
            } else {
                dPath.lineTo(point.x, point.dy);
                nPath.lineTo(point.x, point.ny);
            }
        }
        mPaint.setStrokeWidth(lineWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.parseColor(dWeatherColor));
        canvas.drawPath(nPath, mPaint);

        mPaint.setColor(Color.parseColor(nWeatherColor));
        canvas.drawPath(dPath, mPaint);


    }

    /**
     * 画圆点
     *
     * @param canvas
     */
    private void drawPoint(Canvas canvas) {
        for (int i = 0; i < mTemperaturePoints.size(); i++) {
            TemperaturePoint point = mTemperaturePoints.get(i);

            mPaint.setColor(Color.parseColor(nWeatherColor));
            canvas.drawCircle(point.x, point.dy, Radius, mPaint);

            mPaint.setColor(Color.parseColor(dWeatherColor));
            canvas.drawCircle(point.x, point.ny, Radius, mPaint);
        }
    }


}
