package com.example.xuchichi.mytest.presenter;

import com.example.xuchichi.mytest.view.MainActivityView;

/**
 * Created by xuchichi on 2018/3/19.
 *业务逻辑抽象成Prenster接口
 * View和Model的桥梁
 */

public interface BasePrenster {
    void attachView(MainActivityView mainView);
    void detachView();

}
