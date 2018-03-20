package com.example.xuchichi.mytest.utils;

import android.content.SharedPreferences;

import com.example.xuchichi.mytest.MyApplication;

/**
 * Created by xuchichi on 2018/3/19.
 */

public class SharePerferenceUtil {

    static SharedPreferences sp = MyApplication.preferences;

    public static void putString(String key, String value) {

        sp.edit().putString(key, value).commit();
    }

    public static String getString(String key, String defValue) {

        return sp.getString(key, defValue);
    }

    public static void putBoolean(String key, boolean value) {

        sp.edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolean(String key, boolean defValue) {

        return sp.getBoolean(key, defValue);
    }

    public static void putFloat(String key, float value){

        sp.edit().putFloat(key, value).commit();

    }


    public static float getFloat(String key, float defValue){

       return sp.getFloat(key, defValue);

    }

}
