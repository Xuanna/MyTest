package com.example.xuchichi.mytest;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("AonStart", "AonStart");
    }

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

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("AonPause", "AonPause");
    }

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
