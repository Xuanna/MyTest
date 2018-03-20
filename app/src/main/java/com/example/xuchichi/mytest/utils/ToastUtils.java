package com.example.xuchichi.mytest.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by xuchichi on 2018/3/19.
 */

public class ToastUtils {


    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    public static void showToast(Context context, int res) {
        Toast.makeText(context, res, Toast.LENGTH_LONG).show();
    }
}
