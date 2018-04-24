package com.example.xuchichi.mytest.view.activity;

import android.content.Context;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Button;

import com.example.xuchichi.mytest.R;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CameraActivity extends AppCompatActivity implements SurfaceHolder.Callback {

    Camera mCamera;
    @BindView(R.id.surfaceView)
    SurfaceView surfaceView;
    @BindView(R.id.btn_take_photo)
    Button btnTakePhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        ButterKnife.bind(this);


    }

    private void checkCamera() {
        if (mCamera == null) {
            throw new IllegalStateException("Camera must be set when start/stop preview, call <setCamera(Camera)> to set");
        }
    }

    private void startPreviewDisplay(SurfaceHolder holder) {
        checkCamera();
        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        } catch (IOException e) {
            Log.e("e", "Error while START preview for camera", e);
        }
    }

    private void stopPreviewDisplay() {
        checkCamera();
        try {
            mCamera.stopPreview();
        } catch (Exception e) {
            Log.e("e", "Error while STOP preview for camera", e);
        }
    }

    public static void followScreenOrientation(Context context, Camera camera) {
        //设备的屏幕方向
        final int orientation = context.getResources().getConfiguration().orientation;

        //设置预览方向
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            camera.setDisplayOrientation(180);
        } else if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            camera.setDisplayOrientation(90);
        }
    }

    public void setCamera(Camera camera) {
        mCamera = camera;
        final Camera.Parameters params = mCamera.getParameters();
        params.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        params.setSceneMode(Camera.Parameters.SCENE_MODE_BARCODE);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        int cameras = Camera.getNumberOfCameras();
        if (cameras >= 0) {
            mCamera.open();
        }
        startPreviewDisplay(holder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        setCamera(mCamera);
        followScreenOrientation(CameraActivity.this, mCamera);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        stopPreviewDisplay();
    }

    @OnClick(R.id.btn_take_photo)
    public void onViewClicked() {
    }
}
