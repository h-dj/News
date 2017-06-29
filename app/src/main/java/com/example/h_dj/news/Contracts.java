package com.example.h_dj.news;

/**
 * Created by H_DJ on 2017/5/17.
 */

public class Contracts {

    // 请求的url格式：
    // http://c.m.163.com/nc/article/headline/新闻类别id/偏移量-每页条数.html
    // 请求的url示例：
    // 头条： 第1页，一页有20条
    // http://c.m.163.com/nc/article/headline/T1348647909107/0-20.html
    // 头条： 第2页，一页有20条
    // http://c.m.163.com/nc/article/headline/T1348647909107/20-20.html
    // 头条： 第3页，一页有20条

    // 支持的一些新闻类别类别id如下：
    public final static String[] channelId = new String[]{
            "T1348647909107",   // 头条
            "T1348648037603",   // 社会
            "T1348649580692",   // 科技
            "T1348648756099",   // 财经
            "T1348649079062",   // 体育
            "T1348654060988",   // 汽车
    };
    public final static String[] typeValue = new String[]{
            "头条", "社会", "科技", "财经", "体育", "汽车"
    };

    /**
     * 获取一页新闻数据
     *
     * @param type 新闻类别
     * @return
     */
    public static String getUrl(String type) {
        // http://c.m.163.com/nc/article/headline/T1348647909107/0-20.html
        int pos = getPosition(type);
        return "http://c.m.163.com/nc/article/headline/" + channelId[pos] + "/0-20.html";
    }
    public static int getPosition(String type) {
        for (int i = 0; i < typeValue.length; i++) {
            if (typeValue[i].equals(type)) {
                position = i;
                break;
            }
        }
        return position;
    }

    // 视频url路径
    public static final String VideoURL = //
            "http://c.m.163.com/nc/video/list/V9LG4B3A0/y/0-20.html";

    /*聚合数据API*/
    //http://v.juhe.cn/toutiao/index?type=top&key=APPKEY
    public final static String index = "http://v.juhe.cn/toutiao/index";
    public final static String newsType = "?type=";
    public final static String key = "&key=b087470709b5b292bf6cf93fd1b5062c";
    /**
     * top(头条，默认),shehui(社会),guonei(国内),guoji(国际),
     * yule(娱乐),tiyu(体育)junshi(军事),keji(科技),caijing(财经),shishang(时尚)
     */
    public final static String[] typeValue1 = new String[]{
            "头条", "社会", "国内", "国际", "娱乐", "体育", "军事", "科技", "财经", "时尚",
    };
    public final static String[] types = new String[]{
            "top", "shehui", "guonei", "guoji", "yule", "tiyu", "junshi", "keji", "caijing", "shishang"
    };
    private static int position = 0;

    public static String getRequestUrl(String type) {
        for (int i = 0; i < typeValue1.length; i++) {
            if (typeValue1[i].equals(type)) {
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
    public static final String WEATHER_CITY = "city_json";
    public static final String WEATHER_COUNTRY = "country_json";
    public static final String WEATHER_AREA = "area";
    public static final String VIDEO_INFO = "VideoInfo";
}
