package com.example.xuchichi.mytest;

import android.app.Application;
import android.content.SharedPreferences;

/**
 * Created by xuchichi on 2018/3/19.
 */

public class MyApplication extends Application {

    public static SharedPreferences preferences;
    public static MyApplication myApplication;

//    private static AppComponent appComponent;

    @Override

    public void onCreate() {
        super.onCreate();

//        appComponent = DaggerAppComponent.create();

        myApplication = this;
        preferences = getSharedPreferences("share_perference", MODE_PRIVATE);
    }

//    public static AppComponent getAppComponent() {
//        return appComponent;
//    }
}
