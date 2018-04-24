package com.example.xuchichi.mytest.view.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import com.example.xuchichi.mytest.utils.CamParaUtil;
import com.example.xuchichi.mytest.utils.CommonUtil;
import com.example.xuchichi.mytest.utils.FileUtil;

import java.io.IOException;
import java.util.List;

import static android.hardware.Camera.Parameters.FOCUS_MODE_AUTO;

public class MyCameraSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Camera.AutoFocusCallback, View.OnClickListener {

    protected static final int[] VIDEO_320 = {320, 240};
    protected static final int[] VIDEO_480 = {640, 480};
    protected static final int[] VIDEO_720 = {1280, 720};
    protected static final int[] VIDEO_1080 = {1920, 1080};
    private int screenOritation = Configuration.ORIENTATION_PORTRAIT;
    private boolean mOpenBackCamera = true;
    private SurfaceHolder mSurfaceHolder;
    private SurfaceTexture mSurfaceTexture;
    private boolean mRunInBackground = false;
    boolean isAttachedWindow = false;
    private Camera mCamera;
    private Camera.Parameters mParam;
    private byte[] previewBuffer;
    private int mCameraId;
    protected int previewformat = ImageFormat.NV21;
    Context context;

    public MyCameraSurfaceView(Context context) {
        super(context);
        init(context);
    }

    public MyCameraSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyCameraSurfaceView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        cameraState = CameraState.START;
        if (cameraStateListener != null) {
            cameraStateListener.onCameraStateChange(cameraState);
        }
        openCamera();
        if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            screenOritation = Configuration.ORIENTATION_LANDSCAPE;
        }
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
        mSurfaceTexture = new SurfaceTexture(10);
        setOnClickListener(this);
        post(new Runnable() {
            @Override
            public void run() {
                if (!isAttachedWindow) {
                    mRunInBackground = true;
                    startPreview();
                }
            }
        });
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        isAttachedWindow = true;
    }

    private void openCamera() {
        if (mOpenBackCamera) {
            mCameraId = findCamera(false);
        } else {
            mCameraId = findCamera(true);
        }
        if (mCameraId == -1) {
            mCameraId = 0;
        }
        try {
            mCamera = Camera.open(mCameraId);
        } catch (Exception ee) {
            mCamera = null;
            cameraState = CameraState.ERROR;
            if (cameraStateListener != null) {
                cameraStateListener.onCameraStateChange(cameraState);
            }
        }
        if (mCamera == null) {
            Toast.makeText(context, "打开摄像头失败", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    private int findCamera(boolean front) {
        int cameraCount;
        try {
            Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
            cameraCount = Camera.getNumberOfCameras();
            for (int camIdx = 0; camIdx < cameraCount; camIdx++) {
                Camera.getCameraInfo(camIdx, cameraInfo);
                int facing = front ? 1 : 0;
                if (cameraInfo.facing == facing) {
                    return camIdx;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean setDefaultCamera(boolean backCamera) {
        if (mOpenBackCamera == backCamera) return false;
        if (isRecording) {
            Toast.makeText(context, "请先结束录像", Toast.LENGTH_SHORT).show();
            return false;
        }
        mOpenBackCamera = backCamera;
        if (mCamera != null) {
            closeCamera();
            openCamera();
            startPreview();
        }
        return true;
    }


    public void closeCamera() {
        stopRecord();
        stopPreview();
        releaseCamera();
    }

    private void releaseCamera() {
        try {
            if (mCamera != null) {
                mCamera.setPreviewCallback(null);
                mCamera.setPreviewCallbackWithBuffer(null);
                mCamera.stopPreview();
                mCamera.release();
                mCamera = null;
            }
        } catch (Exception ee) {
        }
    }

    private boolean isSupportCameraLight() {
        boolean mIsSupportCameraLight = false;
        try {
            if (mCamera != null) {
                Camera.Parameters parameter = mCamera.getParameters();
                Object a = parameter.getSupportedFlashModes();
                if (a == null) {
                    mIsSupportCameraLight = false;
                } else {
                    mIsSupportCameraLight = true;
                }
            }
        } catch (Exception e) {
            mIsSupportCameraLight = false;
            e.printStackTrace();
        }
        return mIsSupportCameraLight;
    }


    private Camera.PreviewCallback previewCallback = new Camera.PreviewCallback() {
        public synchronized void onPreviewFrame(byte[] data, Camera camera) {
            if (data == null) {
                releaseCamera();
                return;
            }
            //you can code media here
            if (cameraState != CameraState.PREVIEW) {
                cameraState = CameraState.PREVIEW;
                if (cameraStateListener != null) {
                    cameraStateListener.onCameraStateChange(cameraState);
                }
            }
            mCamera.addCallbackBuffer(previewBuffer);
        }
    };

    /**
     * 通过对比得到与宽高比最接近的预览尺寸（如果有相同尺寸，优先选择）
     *
     * @param isPortrait    是否竖屏
     * @param surfaceWidth  需要被进行对比的原宽
     * @param surfaceHeight 需要被进行对比的原高
     * @param preSizeList   需要对比的预览尺寸列表
     * @return 得到与原宽高比例最接近的尺寸
     */
    public static Camera.Size getCloselyPreSize(boolean isPortrait, int surfaceWidth, int surfaceHeight, List<Camera.Size> preSizeList) {
        int reqTmpWidth;
        int reqTmpHeight;
        // 当屏幕为垂直的时候需要把宽高值进行调换，保证宽大于高
        if (isPortrait) {
            reqTmpWidth = surfaceHeight;
            reqTmpHeight = surfaceWidth;
        } else {
            reqTmpWidth = surfaceWidth;
            reqTmpHeight = surfaceHeight;
        }
        //先查找preview中是否存在与surfaceview相同宽高的尺寸
        for (Camera.Size size : preSizeList) {
            if ((size.width == reqTmpWidth) && (size.height == reqTmpHeight)) {
                return size;
            }
        }

        // 得到与传入的宽高比最接近的size
        float reqRatio = ((float) reqTmpWidth) / reqTmpHeight;
        float curRatio, deltaRatio;
        float deltaRatioMin = Float.MAX_VALUE;
        Camera.Size retSize = null;
        for (Camera.Size size : preSizeList) {
            curRatio = ((float) size.width) / size.height;
            deltaRatio = Math.abs(reqRatio - curRatio);
            if (deltaRatio < deltaRatioMin) {
                deltaRatioMin = deltaRatio;
                retSize = size;
            }
        }

        return retSize;
    }

    //设置Camera各项参数
    private void startPreview() {
        if (mCamera == null) return;
        try {
            mParam = mCamera.getParameters();
//            mParam.setPictureFormat(PixelFormat.JPEG); // 设置图片格式
            mParam.setPreviewFormat(previewformat);
            mParam.setFocusMode(FOCUS_MODE_AUTO);
            if (screenOritation != Configuration.ORIENTATION_LANDSCAPE) {
                mParam.set("orientation", "portrait");
                mCamera.setDisplayOrientation(90);
            } else {
                mParam.set("orientation", "landscape");
                mCamera.setDisplayOrientation(0);
            }
            Camera.Size preSize = getCloselyPreSize(true, CommonUtil.getScreenWidth(context), CommonUtil.getScreenHeight(context), mParam.getSupportedPreviewSizes());
            mParam.setPreviewSize(preSize.width, preSize.height);
            mParam.setPictureSize(preSize.width, preSize.height);
            if (mRunInBackground) {
                mCamera.setPreviewTexture(mSurfaceTexture);
                mCamera.addCallbackBuffer(previewBuffer);
                mCamera.setPreviewCallbackWithBuffer(previewCallback);//设置摄像头预览帧回调
            } else {
                mCamera.setPreviewDisplay(mSurfaceHolder);
                mCamera.setPreviewCallback(previewCallback);//设置摄像头预览帧回调
            }
            mCamera.setParameters(mParam);
            mCamera.startPreview();
            if (cameraState != CameraState.START) {
                cameraState = CameraState.START;
                if (cameraStateListener != null) {
                    cameraStateListener.onCameraStateChange(cameraState);
                }
            }
        } catch (Exception e) {
            releaseCamera();
            return;
        }
        try {
            String mode = mCamera.getParameters().getFocusMode();
            if (("auto".equals(mode)) || ("macro".equals(mode))) {
                mCamera.autoFocus(null);
            }
        } catch (Exception e) {
        }
    }

    private void stopPreview() {
        if (mCamera == null) return;
        try {
            if (mRunInBackground) {
                mCamera.setPreviewCallbackWithBuffer(null);
                mCamera.stopPreview();
            } else {
                mCamera.setPreviewCallback(null);
                mCamera.stopPreview();
            }
            if (cameraState != CameraState.STOP) {
                cameraState = CameraState.STOP;
                if (cameraStateListener != null) {
                    cameraStateListener.onCameraStateChange(cameraState);
                }
            }
        } catch (Exception ee) {
        }
    }

    @Override
    public void onClick(View v) {
        if (mCamera != null) {
            mCamera.autoFocus(null);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        stopPreview();
        startPreview();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (mCamera != null) {
            mCamera.autoFocus(null);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        stopPreview();
        if (mRunInBackground)
            startPreview();
    }

    protected CameraState cameraState;
    private CameraStateListener cameraStateListener;

    public enum CameraState {
        START, PREVIEW, STOP, ERROR
    }

    public void setOnCameraStateListener(CameraStateListener listener) {
        this.cameraStateListener = listener;
    }

    public interface CameraStateListener {
        void onCameraStateChange(CameraState paramCameraState);
    }

    /**
     * ___________________________________前/后台运行______________________________________
     **/
    public void setRunBack(boolean b) {
        if (mCamera == null) return;
        if (b == mRunInBackground) return;
        if (!b && !isAttachedWindow) {
            Toast.makeText(context, "Vew未依附在Window,无法显示", Toast.LENGTH_SHORT).show();
            return;
        }
        mRunInBackground = b;
        if (b)
            setVisibility(View.GONE);
        else
            setVisibility(View.VISIBLE);
    }

    /**
     * ___________________________________开关闪光灯______________________________________
     **/
    public void switchLight(boolean open) {
        if (mCamera == null) return;
        try {
            if (mCamera != null) {
                if (open) {
                    Camera.Parameters parameter = mCamera.getParameters();
                    if (parameter.getFlashMode().equals("off")) {
                        parameter.setFlashMode("torch");
                        mCamera.setParameters(parameter);
                    } else {
                        parameter.setFlashMode("off");
                        mCamera.setParameters(parameter);
                    }
                } else {
                    Camera.Parameters parameter = mCamera.getParameters();
                    if ((parameter.getFlashMode() != null) &&
                            (parameter.getFlashMode().equals("torch"))) {
                        parameter.setFlashMode("off");
                        mCamera.setParameters(parameter);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ___________________________________以下为拍照模块______________________________________
     **/
    public void capture() {
        if (mCamera == null) return;
        if (!FileUtil.isExternalStorageWritable()) {
            Toast.makeText(context, "请插入存储卡", Toast.LENGTH_SHORT).show();
            return;
        }
        mCamera.autoFocus(this);
    }

    @Override
    public void onAutoFocus(boolean success, Camera camera) {
        if (success) {
            try {
                mCamera.takePicture(null, null, new BitmapCallback() {
                    @Override
                    public void onPictureTaken(Bitmap bitmap) {
                        if (pictureTokenCallback != null) {
                            pictureTokenCallback.onPictureTaken(bitmap);
                        }
                    }
                });
            } catch (Exception e) {
                if (isRecording) {
                    Toast.makeText(context, "请先结束录像", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    /**
     * 相机拍照图片的回调
     */
    public MyCameraSurfaceView.OnPictureTokenCallback pictureTokenCallback;

    public void setOnPictureTokenCallback(MyCameraSurfaceView.OnPictureTokenCallback pictureTokenCallback) {
        this.pictureTokenCallback = pictureTokenCallback;
    }

    public interface OnPictureTokenCallback {
        void onPictureTaken(Bitmap bitmap);
    }

    /**
     * 相机拍照的回调
     */
    public abstract class BitmapCallback implements Camera.PictureCallback {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            Matrix matrix = new Matrix();
            matrix.setRotate(90);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            onPictureTaken(bitmap);
        }

        public abstract void onPictureTaken(Bitmap bitmap);
    }

    /**
     * ___________________________________以下为视频录制模块______________________________________
     **/
    MediaRecorder mediaRecorder = new MediaRecorder();
    private boolean isRecording = false;

    public boolean isRecording() {
        return isRecording;
    }

    public boolean startRecord() {
        return startRecord(-1, null);
    }

    public boolean startRecord(int maxDurationMs, MediaRecorder.OnInfoListener onInfoListener) {
        if (mCamera == null) return false;
        if (!FileUtil.isExternalStorageWritable()) {
            Toast.makeText(context, "请插入存储卡", Toast.LENGTH_SHORT).show();
            return false;
        }
        mCamera.unlock();
        mediaRecorder.reset();
        mediaRecorder.setCamera(mCamera);
        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        Camera.Size videoSize = CamParaUtil.getSize(mParam.getSupportedVideoSizes(), 1200,
                mCamera.new Size(VIDEO_1080[0], VIDEO_1080[1]));
        mediaRecorder.setVideoSize(videoSize.width, videoSize.height);
        mediaRecorder.setVideoEncodingBitRate(5 * 1024 * 1024);
        mediaRecorder.setPreviewDisplay(mSurfaceHolder.getSurface());//设置录制预览surface
        if (mOpenBackCamera) {
            mediaRecorder.setOrientationHint(90);
        } else {
            if (screenOritation == Configuration.ORIENTATION_LANDSCAPE)
                mediaRecorder.setOrientationHint(90);
            else
                mediaRecorder.setOrientationHint(270);
        }
        if (maxDurationMs != -1) {
            mediaRecorder.setMaxDuration(maxDurationMs);
            mediaRecorder.setOnInfoListener(onInfoListener);
        }

        mediaRecorder.setOutputFile(FileUtil.getMediaOutputPath());
        try {
            mediaRecorder.prepare();
            mediaRecorder.start();
            isRecording = true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void stopRecord() {
        if (!isRecording) return;
        mediaRecorder.setPreviewDisplay(null);
        try {
            mediaRecorder.stop();
            isRecording = false;
            Toast.makeText(context, "视频已保存在根目录", Toast.LENGTH_SHORT).show();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    /**_________________________________________________________________________________________**/


}
