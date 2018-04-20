package com.example.xuchichi.mytest.view.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

/**
 * Created by xuchichi on 2018/4/20.
 */

public class CameraSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    private final static String TAG = CameraSurfaceView.class.getSimpleName();
    private Camera mCamera;
    private SurfaceHolder mSurfaceHolder;


    public CameraSurfaceView(Context context) {
        super(context, null);
    }

    public CameraSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public CameraSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mSurfaceHolder = this.getHolder();
        mSurfaceHolder.addCallback(this);
    }

    /**
     * 被创建后自动开启预览
     *
     * @param holder
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // 例如在拥有前后摄像头的手机设备上，其返回结果是2，
        // 则第一个摄像头的cameraId是0，通常对应手机背后那个大摄像头；
        // 第二个摄像头的cameraId是1，通常对应着手机的前置自拍摄像头；
        int cameras = Camera.getNumberOfCameras();
        if (cameras >= 0) {
            mCamera.open();
            setCamera(mCamera);
        }

        Log.d(TAG, "Start preview display[SURFACE-CREATED]");
        startPreviewDisplay(holder);
    }

    /**
     * 尺寸被改变时重置预览
     *
     * @param holder
     * @param format
     * @param width
     * @param height
     */
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (mSurfaceHolder.getSurface() == null) {
            return;
        }
        followScreenOrientation(getContext(), mCamera);
        Log.d(TAG, "Restart preview display[SURFACE-CHANGED]");
        stopPreviewDisplay();
        startPreviewDisplay(mSurfaceHolder);

    }

    /**
     * 被销毁时关闭预览
     *
     * @param holder
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d(TAG, "Stop preview display[SURFACE-DESTROYED]");
        stopPreviewDisplay();
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


    private void stopPreviewDisplay() {
        checkCamera();
        try {
            mCamera.stopPreview();
        } catch (Exception e) {
            Log.e(TAG, "Error while STOP preview for camera", e);
        }
    }

    private void checkCamera() {
        if (mCamera == null) {
            throw new IllegalStateException("Camera must be set when start/stop preview, call <setCamera(Camera)> to set");
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


}
