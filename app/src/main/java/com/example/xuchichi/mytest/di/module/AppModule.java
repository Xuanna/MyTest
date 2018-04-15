package com.example.xuchichi.mytest.di.module;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

/**
 * Created by xuchichi on 2018/4/8.
 */
@Module
public class AppModule {

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient() {
        return new OkHttpClient().newBuilder().build();
    }


}
