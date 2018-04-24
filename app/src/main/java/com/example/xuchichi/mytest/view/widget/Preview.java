package com.example.xuchichi.mytest.view.widget;

import android.content.Context;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/24 0024.
 */

public class Preview extends ViewGroup implements SurfaceHolder.Callback {

    SurfaceView mSurfaceView;
    SurfaceHolder mHolder;
    Camera mCamera;

    public Preview(Context context) {
        super(context, null);
    }

    public Preview(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public Preview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mSurfaceView = new SurfaceView(context);
        addView(mSurfaceView);

        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        mHolder = mSurfaceView.getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    private boolean safeCameraOpen(int id) {
        boolean qOpened = false;

        try {
            releaseCameraAndPreview();
            mCamera = Camera.open(id);
            qOpened = (mCamera != null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return qOpened;
    }

    private void releaseCameraAndPreview() {
        setCamera(null);
        if (mCamera != null) {
            mCamera.release();
            mCamera = null;
        }
    }

    List<Camera.Size> mSupportedPreviewSizes = new ArrayList<>();

    public void setCamera(Camera camera) {
        if (mCamera == camera) {
            return;
        }

        stopPreviewAndFreeCamera();

        mCamera = camera;

        if (mCamera != null) {
            List<Camera.Size> localSizes = mCamera.getParameters().getSupportedPreviewSizes();
            mSupportedPreviewSizes = localSizes;
            requestLayout();

            try {
                mCamera.setPreviewDisplay(mHolder);
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Important: Call startPreview() to start updating the preview
            // surface. Preview must be started before you can take a picture.
            mCamera.startPreview();
        }
    }


    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        // Now that the size is known, set up the camera parameters and begin
        // the preview.
        Camera.Parameters parameters = mCamera.getParameters();
//        parameters.setPreviewSize(mPreviewSize.width, mPreviewSize.height);
        requestLayout();
        mCamera.setParameters(parameters);
        // Important: Call startPreview() to start updating the preview surface.
        // Preview must be started before you can take a picture.
        mCamera.startPreview();

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        // Surface will be destroyed when we return, so stop the preview.
        if (mCamera != null) {
            // Call stopPreview() to stop updating the preview surface.
            mCamera.stopPreview();
        }
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {

    }

    /**
     * When this function returns, mCamera will be null.
     */
    private void stopPreviewAndFreeCamera() {

        if (mCamera != null) {
            // Call stopPreview() to stop updating the preview surface.
            mCamera.stopPreview();

            // Important: Call release() to release the camera for use by other
            // applications. Applications should release the camera immediately
            // during onPause() and re-open() it during onResume()).
            mCamera.release();

            mCamera = null;
        }
    }
}
