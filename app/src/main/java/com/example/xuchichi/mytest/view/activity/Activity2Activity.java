package com.example.xuchichi.mytest.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.xuchichi.mytest.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Activity2Activity extends AppCompatActivity {

    @BindView(R.id.btn1)
    Button btn1;
    @BindView(R.id.btn2)
    Button btn2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        setContentView(R.layout.activity_activity2);
        ButterKnife.bind(this);
        Log.e("onCreate2","onCreate()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("onStart2","onStart()");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("onResume2","onResume()");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("onPause2","onPause()");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("onStop2","onStop()");

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("onRestart2","onRestart()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("onDestroy2","onDestroy()");

    }
    @OnClick({R.id.btn1, R.id.btn2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                startActivity(new Intent(this,Activity1Activity.class));
                break;
            case R.id.btn2:
                startActivity(new Intent(this,Activity2Activity.class));
                break;
        }
    }
}
