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
}