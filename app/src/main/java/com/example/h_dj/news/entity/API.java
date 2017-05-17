package com.example.h_dj.news.entity;

/**
 * Created by H_DJ on 2017/5/17.
 */

public class API {

    //http://v.juhe.cn/toutiao/index?type=top&key=APPKEY
    public final static String index = "http://v.juhe.cn/toutiao/index";
    public final static String newsType = "?type=";
    public final static String key = "&key=b087470709b5b292bf6cf93fd1b5062c";
    /**
     * top(头条，默认),shehui(社会),guonei(国内),guoji(国际),
     * yule(娱乐),tiyu(体育)junshi(军事),keji(科技),caijing(财经),shishang(时尚)
     */
    public final static String[] types = new String[]{
            "top", "shehui", "guonei", "guoji", "yule", "tiyu", "junshi", "keji", "caijing", "shishang"
    };

    private static int position;

    public static String getRequestUrl(int type) {
        if (type < 0 || type > 9) {
            return null;
        }
        switch (type) {
            case 0:
                position = 0;
                break;
            case 1:
                position = 1;
                break;
            case 2:
                position = 2;
                break;
            case 3:
                position = 3;
                break;
            case 4:
                position = 4;
                break;
            case 5:
                position = 5;
                break;
            case 6:
                position = 6;
                break;
            case 7:
                position = 7;
                break;
            case 8:
                position = 8;
                break;
            case 9:
                position = 9;
                break;

        }
        return index + newsType + types[position] + key;
    }
}
