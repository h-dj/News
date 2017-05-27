package com.example.h_dj.news.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

/**
 * Created by H_DJ on 2017/5/18.
 */

public class SPutils {

    public static SPutils mSPutils;
    private static Context mContext;
    private SharedPreferences.Editor editor;
    private SharedPreferences sp;

    public static SPutils newInstance(Context context) {
        mContext = context;
        if (mSPutils == null) {
            synchronized (SPutils.class) {
                if (mSPutils == null) {
                    mSPutils = new SPutils();
                }
            }

        }
        return mSPutils;
    }

    /**
     * 构建Editor
     *
     * @param name
     * @param mode
     * @return
     */
    public SPutils build(String name, int mode) {
        sp = mContext.getSharedPreferences(name, mode);
        editor = sp.edit();
        return this;
    }

    public SPutils putString(String key, String data) {
        editor.putString(key, data);
        return this;
    }

    public SPutils putInt(String key, int data) {
        editor.putInt(key, data);
        return this;
    }

    public SPutils putBoolean(String key, boolean data) {
        editor.putBoolean(key, data);
        return this;
    }

    public SPutils putFloat(String key, float data) {
        editor.putFloat(key, data);
        return this;
    }

    public SPutils putLong(String key, long data) {
        editor.putLong(key, data);
        return this;
    }

    public SPutils putStringSet(String key, Set<String> data) {
        editor.putStringSet(key, data);
        return this;
    }

    public void commit() {
        editor.commit();
    }


    public String getString(String key, String defValue) {
        if (sp == null) {
            throw new NullPointerException("sp 为空");
        }
        return sp.getString(key, defValue);
    }

    public int getInt(String key, int defValue) {
        if (sp == null) {
            throw new NullPointerException("sp 为空");
        }
        return sp.getInt(key, defValue);
    }

    public boolean getBoolean(String key, boolean defValue) {
        if (sp == null) {
            throw new NullPointerException("sp 为空");
        }
        return sp.getBoolean(key, defValue);
    }

    public long getLong(String key, long defValue) {
        if (sp == null) {
            throw new NullPointerException("sp 为空");
        }
        return sp.getLong(key, defValue);
    }

    public float getFloat(String key, float defValue) {
        if (sp == null) {
            throw new NullPointerException("sp 为空");
        }
        return sp.getFloat(key, defValue);
    }

    public Set getStringSet(String key, Set defValue) {
        if (sp == null) {
            throw new NullPointerException("sp 为空");
        }
        return sp.getStringSet(key, defValue);
    }

    public boolean isExist(String key) {
        return sp.contains(key);
    }
}
