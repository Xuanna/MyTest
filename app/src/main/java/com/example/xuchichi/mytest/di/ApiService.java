package com.example.xuchichi.mytest.di;

import android.util.Log;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by xuchichi on 2018/4/8.
 */

public class ApiService {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    OkHttpClient mOkHttpClient;

    public ApiService(OkHttpClient mOkHttpClient) {
        this.mOkHttpClient = mOkHttpClient;
    }

//    @Inject
//    public ApiService() {
//        Log.e("ApiService", "ApiService");
//    }
    public void login(){
        Log.e("ApiService", "ApiServicelogin:" + mOkHttpClient);

    }


    public void register() {
        //云端保存数据
        Log.e("log", "ApiServiceRegister:" + mOkHttpClient);
//
//        RequestBody body = RequestBody.create(JSON, "");
//        Request request = new Request.Builder()
//                .url("")
//                .post(body)
//                .build();
//        mOkHttpClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//
//            }
//        });
    }
}
