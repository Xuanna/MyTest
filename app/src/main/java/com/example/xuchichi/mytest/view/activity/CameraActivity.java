package com.example.xuchichi.mytest.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;

import com.example.xuchichi.mytest.R;
import com.example.xuchichi.mytest.view.widget.MyCameraSurfaceView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CameraActivity extends AppCompatActivity {


    @BindView(R.id.surfaceView)
    MyCameraSurfaceView surfaceView;
    @BindView(R.id.iv)
    ImageView iv;
    @BindView(R.id.btn_take_photo)
    Button btnTakePhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        ButterKnife.bind(this);


    }
}
