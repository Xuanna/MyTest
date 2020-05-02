package com.example.xuchichi.mytest.utils;

import android.content.Context;
import android.hardware.Camera;
import android.view.WindowManager;

import com.example.xuchichi.mytest.MyApplication;

/**
 * Created by Administrator on 2018/4/24 0024.
 */

public class CommonUtil {
    /**
     * A safe way to get an instance of the Camera object.
     */
    public static Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
            return c;
        } catch (Exception e) {
            // Camera is not available (in use or does not exist)
        }
        return null;
        // returns null if camera is unavailable
    }

    /**
     * 获取状态栏的高度
     *
     * @return
     */
    public static int getStatusBarHeight() {
        int resourceId = MyApplication.myApplication.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            return MyApplication.myApplication.getResources().getDimensionPixelSize(resourceId);
        } else {
            return 0;
        }
    }
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }

    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getHeight();
    }
}
