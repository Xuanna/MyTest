package com.example.xuchichi.mytest.di.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

/**
 * Created by xuchichi on 2018/4/8.
 */
@Module

public class HttpModule {

    @Provides
    @Singleton
    public OkHttpClient mOkHttpClient() {
        return new OkHttpClient().newBuilder().build();
    }


}
