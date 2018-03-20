package com.example.xuchichi.mytest;

import android.app.Application;
import android.content.SharedPreferences;

/**
 * Created by xuchichi on 2018/3/19.
 */

public class MyApplication extends Application {

   public static SharedPreferences preferences;

    @Override
    public void onCreate() {
        super.onCreate();
        preferences=getSharedPreferences("share_perference",MODE_PRIVATE);
    }
}
