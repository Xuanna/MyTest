package com.example.xuchichi.mytest.view;

import com.example.xuchichi.mytest.view.adapter.MainActivityAdapter;

/**
 * Created by xuchichi on 2018/3/19.
 */

public interface MainActivityView extends BaseView {
    void setAdapter(MainActivityAdapter adapter);

    void onRecycleItemClick(int position);

    void setToolbar();

}
