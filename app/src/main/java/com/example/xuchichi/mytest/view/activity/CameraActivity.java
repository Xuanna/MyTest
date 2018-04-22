package com.example.xuchichi.mytest.view.activity;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.xuchichi.mytest.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CameraActivity extends AppCompatActivity implements SurfaceHolder.Callback {
    String TAG = "E";

    @BindView(R.id.surfaceView)
    SurfaceView surfaceView;
    @BindView(R.id.btn_take_photo)
    Button btnTakePhoto;
    @BindView(R.id.iv)
    ImageView iv;

    Camera mCamera;
    SurfaceHolder mHolder;
    File imgFiles;
    File recordFiles;
    String path;
    File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        ButterKnife.bind(this);

        mHolder = surfaceView.getHolder();
        mHolder.addCallback(this);
        init();
    }

    public void init() {
        path = Environment.getExternalStorageDirectory().getAbsolutePath();
        file = new File(path, "/MyTest");
        if (!file.exists()) {
            file.mkdirs();
        }
        if (file.exists()) {
            imgFiles = new File(file, "Images");
            if (!imgFiles.exists()) {
                imgFiles.mkdirs();
            }
        }

        if (file.exists()) {
            recordFiles = new File(file, "Record");
            if (!recordFiles.exists()) {
                recordFiles.mkdirs();
            }
        }
    }


    private void checkCamera() {
        if (mCamera == null) {
            throw new IllegalStateException("Camera must be set when start/stop preview, call <setCamera(Camera)> to set");
        }
    }

    public void setCamera(Camera camera) {
        mCamera = camera;
        final Camera.Parameters params = mCamera.getParameters();
        params.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        params.setSceneMode(Camera.Parameters.SCENE_MODE_BARCODE);
    }

    private void startPreviewDisplay(SurfaceHolder holder) {
        checkCamera();
        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        } catch (IOException e) {
            Log.e(TAG, "Error while START preview for camera", e);
        }
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // 例如在拥有前后摄像头的手机设备上，其返回结果是2，
        // 则第一个摄像头的cameraId是0，通常对应手机背后那个大摄像头；
        // 第二个摄像头的cameraId是1，通常对应着手机的前置自拍摄像头；
        int cameras = Camera.getNumberOfCameras();
        if (cameras >= 0) {
            mCamera = mCamera.open();
            setCamera(mCamera);
        }

        Log.d(TAG, "Start preview display[SURFACE-CREATED]");
        startPreviewDisplay(holder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (mHolder.getSurface() == null) {
            return;
        }
        followScreenOrientation(this, mCamera);
        Log.d(TAG, "Restart preview display[SURFACE-CHANGED]");
        stopPreviewDisplay();
        startPreviewDisplay(mHolder);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    private void stopPreviewDisplay() {
        checkCamera();
        try {
            mCamera.stopPreview();
        } catch (Exception e) {
            Log.e(TAG, "Error while STOP preview for camera", e);
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

    @OnClick(R.id.btn_take_photo)
    public void onViewClicked() {
        checkCamera();
        mCamera.takePicture(null, null, new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {

                Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                iv.setVisibility(View.VISIBLE);
                surfaceView.setVisibility(View.GONE);
                iv.setImageBitmap(bitmap);
                if (file == null) {
                    Log.d(TAG, "Error creating media file, check storage permissions: ");
                    return;
                }
                FileOutputStream fos;
                String imgPath = file.getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg";
                try {

                    fos = new FileOutputStream(imgPath);
                    if (data != null) {
                        Log.e("if", "if");
                        fos.write(data);
                        fos.close();
                    } else {
                        Log.e("else", "else");
                    }

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }


}
