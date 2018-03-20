package com.example.xuchichi.mytest.view;

/**
 * Created by xuchichi on 2018/3/19.
 * Ui逻辑抽象成View接口
 */

public interface BaseView {

    void showToast(String msg);

    void printLog(String logStr);

    void onSuccess();

    void onFailed();

    void onError();
}
