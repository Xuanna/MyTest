package com.example.xuchichi.mytest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.xuchichi.mytest.presenter.MainPresenterImpl;
import com.example.xuchichi.mytest.view.MainActivityView;
import com.example.xuchichi.mytest.view.activity.BreakPointResumeActivity;
import com.example.xuchichi.mytest.view.activity.DbActivity;
import com.example.xuchichi.mytest.view.activity.HttpUrlConnectionActivity;
import com.example.xuchichi.mytest.view.adapter.MainActivityAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainActivityView{

    MainPresenterImpl mainPresenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.btnDb)
    Button btnDb;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;

    List<String> mlist=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mainPresenter=new MainPresenterImpl();
        mainPresenter.attachView(this);
        init();
    }

    public void init(){

        mlist.add("自定义流式布局");
        mlist.add("数据库的使用");
        mlist.add("HttpUrlConnection网络请求，并下载图片做文件缓存");
        mlist.add("断点续传");
        mlist.add("仿微信语音");
        mlist.add("地图开发");

        mainPresenter.setData(mlist,this);
        mainPresenter.setToolbar();

    }
    @Override
    public void setAdapter(MainActivityAdapter adapter) {
        mainPresenter.startActivityOnItemClick(adapter);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        recycleView.setAdapter(adapter);
    }

    @Override
    public void onRecycleItemClick(int position) {
        switch (position){
            case 0:
                break;
            case 1:
                startActivity(new Intent(MainActivity.this, DbActivity.class));
                break;
            case 2:
                startActivity(new Intent(MainActivity.this, HttpUrlConnectionActivity.class));
                break;
            case 3:
                startActivity(new Intent(MainActivity.this, BreakPointResumeActivity.class));
                break;
        }
    }

    @Override
    public void setToolbar() {
        //标题必须在support之前设置
        //调用supportActionBar之后，menu就没用了 toolbar.inflateMenu(R.menu.menu_main);失效
        //推荐和actionBar一起使用
        toolbar.setTitle("Title");
        toolbar.setSubtitle("SubTitle");
        toolbar.setNavigationIcon(R.mipmap.ic_launcher);
        toolbar.inflateMenu(R.menu.menu_main);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.addFriend:
                        Log.e("Log", "addFriend");
                        break;
                    case R.id.scan:
                        Log.e("Log", "scan");
                        break;
                }
                return true;
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Log", "onclick");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainPresenter.attachView(this);
    }


}
