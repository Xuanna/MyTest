package com.example.xuchichi.mytest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class BActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);
        Log.e("BonCreate", "BonCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("BonStart", "BonStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("BonResume", "BonResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("BonRestart", "BonRestart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("BonPause", "BonPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("BonStop", "BonStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("BonDestroy", "BonDestroy");
    }
}
