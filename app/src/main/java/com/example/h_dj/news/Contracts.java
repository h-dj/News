package com.example.h_dj.news;

/**
 * Created by H_DJ on 2017/5/17.
 */

public class Contracts {

    //http://v.juhe.cn/toutiao/index?type=top&key=APPKEY
    public final static String index = "http://v.juhe.cn/toutiao/index";
    public final static String newsType = "?type=";
    public final static String key = "&key=b087470709b5b292bf6cf93fd1b5062c";
    /**
     * top(头条，默认),shehui(社会),guonei(国内),guoji(国际),
     * yule(娱乐),tiyu(体育)junshi(军事),keji(科技),caijing(财经),shishang(时尚)
     */
    public final static String[] typeValue = new String[]{
            "头条", "社会", "国内", "国际", "娱乐", "体育", "军事", "科技", "财经", "时尚",
    };
    public final static String[] types = new String[]{
            "top", "shehui", "guonei", "guoji", "yule", "tiyu", "junshi", "keji", "caijing", "shishang"
    };
    private static int position = 0;

    public static String getRequestUrl(String type) {
        for (int i = 0; i < typeValue.length; i++) {
            if (typeValue[i].equals(type)) {
                position = i;
                break;
            }
        }
        return index + newsType + types[position] + key;
    }

    /**
     * 视频
     */
    public final static String videoUrl = "http://c.3g.163.com/nc/video/home/10-10.html";


    /**
     * 天气
     */
    //    public final static String weather = "http://weather.mail.163.com/weather/xhr/weather/info.do?sid=&uid=&host=&ver=js6&fontface=yahei&style=1&skin=seablue&color=&city=";
    private final static String weather = "https://free-api.heweather.com/v5/weather?key=13fd963a21324babbe0c861a4d39610f&city=";

    public static String getWeatherUrl(String cityCode) {
        return weather + cityCode;
    }

    /**
     * 图片背景
     */
    public final static String bingBg = "http://guolin.tech/api/bing_pic";
    /**
     * 获取省份
     */
    public final static String province = "http://guolin.tech/api/china/";


    /**
     * 保存数据的key
     */
    public final static String WEATHER_INFO = "weatherJson";
    public final static String WEATHER_BG = "weatherBg";
    public static final String WEATHER_PROVINCE = "province_json";
    public static final String WEATHER_AREA = "area";
    public static final String VIDEO_INFO = "VideoInfo";
}
