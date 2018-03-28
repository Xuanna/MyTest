package com.example.xuchichi.mytest.utils;

import android.content.Context;
import android.widget.Toast;

import com.example.xuchichi.mytest.MyApplication;

/**
 * Created by xuchichi on 2018/3/19.
 */

public class ToastUtils {

    private static Toast mToast = null;

    public synchronized static void showText(int stringId){
        String string = null;
        try {
            string = MyApplication.myApplication.getString(stringId);
        }catch (Exception e){
            string =""+stringId;
        }
        showText(string);
    }
    public synchronized static void showText(String string) {
        synchronized(ToastUtils.class){
            if (mToast==null){
                mToast = new Toast(MyApplication.myApplication);
            }
        }
        mToast.cancel();
        mToast.makeText(MyApplication.myApplication, ""+string, Toast.LENGTH_LONG).show();
    }
    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    public static void showToast(Context context, int res) {
        Toast.makeText(context, res, Toast.LENGTH_LONG).show();
    }
}
