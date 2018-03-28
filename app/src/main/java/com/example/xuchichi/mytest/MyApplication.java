package com.example.xuchichi.mytest;

import android.app.Application;
import android.content.SharedPreferences;

import com.example.xuchichi.mytest.db.MySqliteHelper;

/**
 * Created by xuchichi on 2018/3/19.
 */

public class MyApplication extends Application {

    public static SharedPreferences preferences;
    public static MyApplication myApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
        preferences = getSharedPreferences("share_perference", MODE_PRIVATE);
    }
}
