package com.example.h_dj.news.utils;


import com.example.h_dj.news.bean.AreaInfo;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by H_DJ on 2017/5/17.
 */

public class GsonUtils {

    private static Gson mGson = new Gson();

    public static Object String2Obj(String response, Class mClass) {
        Gson mGson = new Gson();
        return mGson.fromJson(response, mClass);
    }


    public static List<AreaInfo> String2AreaInfo(String result) {
        List<AreaInfo> mAreaInfos = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(result);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                AreaInfo areaInfo = new AreaInfo();
                areaInfo.setId(jsonObject.getInt("id"));
                areaInfo.setName(jsonObject.getString("name"));
                mAreaInfos.add(areaInfo);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mAreaInfos;
    }
}
