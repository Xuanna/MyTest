package com.example.xuchichi.mytest.view.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.example.xuchichi.mytest.R;
import com.example.xuchichi.mytest.utils.ToastUtils;

import java.lang.reflect.Field;

import butterknife.ButterKnife;

/**
 * Created by xuchichi on 2018/3/19.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //保持竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(initLayout());
        ButterKnife.bind(this);
        initView();
    }

    /**
     * 初始化布局
     *
     * @return
     */
    public abstract int initLayout();

    /**
     * 初始化View
     */
    public abstract void initView();

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
    }

    public void openActivity(Class classz) {
        Intent intent = new Intent(this, classz);
        startActivity(intent);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    /**
     * 获取状态栏高度
     *
     * @return
     */
    public int getStatusBarHeight() {
        try {
            //通过反射获取类
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            //创建类对象
            Object o = clazz.newInstance();
            //获取类属性
            Field status_bar_height = clazz.getField("status_bar_height");
            //获取值
            Object o1 = status_bar_height.get(o);

            int height = Integer.parseInt(o1.toString());

            return getResources().getDimensionPixelSize(height);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private ViewGroup barLayout;

    /**
     * 系统版本4.4或以上才可以设置
     * 设置沉浸是状态栏
     * 1，设置状态栏透明 2，获取状态栏高度 3，重新设置bar的高度
     */
    public void setStatus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //设置状态栏透明
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 设置导航栏透明
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

            //barLayout findViewById();

            //获取控件
            final int barheight = getStatusBarHeight();

            barLayout.post(new Runnable() {//刷新本身
                @Override
                public void run() {
                    int height = barLayout.getHeight();
                    ViewGroup.LayoutParams params = barLayout.getLayoutParams();
                    params.height = barheight + height;
                    barLayout.setLayoutParams(params);

                }
            });

        }

    }
    String permissions[]={
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
    int requestCode=1;
    /**
     * 申请权限 Sd卡的读取
     *
     */
    public void verifyPermission(Activity activity){
        //1.检测权限
        int permission=  ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission!= PermissionChecker.PERMISSION_GRANTED){
            //2.没有权限，需要申请权限，弹出对话框
            ActivityCompat.requestPermissions(activity,permissions,requestCode);
        }

    }

    /**
     * 请求回掉
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0]==PermissionChecker.PERMISSION_GRANTED){
            ToastUtils.showToast(this,"授权成功");
        }else{
            ToastUtils.showToast(this,"授权失败");
        }
    }

}
