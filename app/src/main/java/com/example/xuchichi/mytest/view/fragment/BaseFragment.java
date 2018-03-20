package com.example.xuchichi.mytest.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by xuchichi on 2018/3/19.
 */

public abstract class BaseFragment extends Fragment {
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(initLayout(), container);
        ButterKnife.bind(view);
        initView();
        return view;


    }

    public abstract int initLayout();

    public abstract void initView();

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
