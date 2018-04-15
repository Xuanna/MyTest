package com.example.xuchichi.mytest;

import android.app.Application;
import android.content.SharedPreferences;

import com.example.xuchichi.mytest.db.MySqliteHelper;
import com.example.xuchichi.mytest.di.component.AppComponent;
import com.example.xuchichi.mytest.di.component.DaggerAppComponent;

import javax.inject.Inject;

/**
 * Created by xuchichi on 2018/3/19.
 */

public class MyApplication extends Application {

    public static SharedPreferences preferences;
    public static MyApplication myApplication;

    private static AppComponent appComponent;

    @Override

    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent.create();

        myApplication = this;
        preferences = getSharedPreferences("share_perference", MODE_PRIVATE);
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }
}
