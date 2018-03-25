package com.example.xuchichi.mytest.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.xuchichi.mytest.R;

public class AActivity extends Activity {
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.e("AonCreate", "AonCreate");


        setContentView(R.layout.activity_a);
        button = findViewById(R.id.btnGO);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AActivity.this, AActivity.class).putExtra("time",System.currentTimeMillis()));
            }
        });

    }

    /**
     * 异常时调用
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e("onSaveInstanceState", "onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.e("onRestoreInstanceState", "onRestoreInstanceState");
    }

    /**
     * 可见，但是不能获取用用户操作
     */
    @Override
    protected void onStart() {
        super.onStart();
        Log.e("AonStart", "AonStart");
    }

    /**
     * 可见，并能于用户交互
     */
    @Override
    protected void onResume() {
        super.onResume();
        Log.e("AonResume", "AonResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("AonRestart", "AonRestart");
    }

    /**
     * 可见
     */
    @Override
    protected void onPause() {
        super.onPause();
        Log.e("AonPause", "AonPause");
    }

    /**
     * 不可见
     */
    @Override
    protected void onStop() {
        super.onStop();
        Log.e("AonStop", "AonStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("AonDestroy", "AonDestroy");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.e("onNewIntent","time="+intent.getLongExtra("time",0));
    }
}
