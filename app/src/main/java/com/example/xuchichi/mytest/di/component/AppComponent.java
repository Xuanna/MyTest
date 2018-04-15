package com.example.xuchichi.mytest.di.component;

import com.example.xuchichi.mytest.annotation.ActivityScope;
import com.example.xuchichi.mytest.di.module.AppModule;

import javax.inject.Singleton;

import dagger.Component;
import okhttp3.OkHttpClient;

/**
 * Created by xuchichi on 2018/4/8.
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    OkHttpClient OK_HTTP_CLIENT();

}
