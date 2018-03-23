package com.example.xuchichi.mytest.presenter;

import android.content.Context;

import com.example.xuchichi.mytest.view.adapter.BaseRecycleViewAdapter;
import com.example.xuchichi.mytest.view.adapter.MainActivityAdapter;

import java.util.List;

/**
 * Created by xuchichi on 2018/3/19.
 */

public interface MainPresenter extends BasePrenster {

    void setData(List<String> list,Context context);

    void startActivityOnItemClick(MainActivityAdapter adapter);

    void setToolbar();

}
