package com.example.xuchichi.mytest.utils;

import android.bluetooth.BluetoothAdapter;

/**
 * Created by xuchichi on 2018/3/28.
 */

public class BluetoothUtil {

    BluetoothUtil instance;

    public BluetoothUtil getInstance(){
        if (instance==null){
            instance=new BluetoothUtil();
        }
        return instance;

    }

    public void isBluetoothOpen(){
        BluetoothAdapter bluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter==null){
            ToastUtils.showText("该设备无蓝牙功能");
            return;
        }
    }
}
