package com.example.xuchichi.mytest.presenter;

import android.content.Context;
import android.view.View;

import com.example.xuchichi.mytest.view.MainActivityView;
import com.example.xuchichi.mytest.view.adapter.BaseRecycleViewAdapter;
import com.example.xuchichi.mytest.view.adapter.MainActivityAdapter;

import java.util.List;

/**
 * Created by xuchichi on 2018/3/19.
 */

public class MainPresenterImpl implements MainPresenter {
    MainActivityView mainView;

    @Override
    public void setToolbar() {
        mainView.setToolbar();
    }

    @Override
    public void startActivityOnItemClick(MainActivityAdapter adapter) {
        adapter.setOnItemClickListener(new BaseRecycleViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View v, int position) {
              mainView.onRecycleItemClick(position);
            }
        });
    }

    @Override
    public void setData(List<String> list, Context context) {
        MainActivityAdapter adapter=new MainActivityAdapter(list,context);
        mainView.setAdapter(adapter);
    }

    @Override
    public void attachView(MainActivityView mainView) {
        this.mainView=mainView;
    }

    @Override
    public void detachView() {
        mainView=null;
    }
}
