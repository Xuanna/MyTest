package com.example.xuchichi.mytest.view.activity;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.xuchichi.mytest.R;
import com.example.xuchichi.mytest.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LesBlueToothActivity extends AppCompatActivity {

    @BindView(R.id.tvDeviceInfo)
    TextView tvDeviceInfo;
    @BindView(R.id.btnStop)
    Button btnStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blue_tooth);
        ButterKnife.bind(this);
        //targetSdkVersion大于等于23（6.0）时必须添加
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Android M Permission check
            if (this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_COARSE_LOCATION);
            }
        }
        initBlueTooth();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_COARSE_LOCATION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // TODO request success
                    Log.e("permissionResult", "授权成功");
                    initBlueTooth();
                }
                break;
        }
    }

    /**
     * 蓝牙扫描操作容易消耗手机的能量，不能一直开着
     * 必须设置一段时间后关闭蓝牙扫描
     *
     * @param view
     */
    @OnClick({R.id.btnScan, R.id.btnStop, R.id.btnConnect})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnScan:
                //扫描设备,一旦发现设备会不断回调callback  外围设备开启蓝牙后，会广播发出一些信息
                bluetoothAdapter.startLeScan(callback);
                break;
            case R.id.btnStop:
                //停止扫描
                bluetoothAdapter.stopLeScan(callback);
                break;
            case R.id.btnConnect:
                //连接
                BluetoothDevice device=deviceList.get(0);
                connectDevice(getApplicationContext(),device);
                break;
        }
    }

    public final int REQUEST_ENABLE_BT = 1;
    public final int PERMISSION_REQUEST_COARSE_LOCATION = 2;
    public final int SCAN_PERIOD = 5000;
    BluetoothAdapter bluetoothAdapter;
    BluetoothAdapter.LeScanCallback callback;
    List<BluetoothDevice> deviceList;

    public void initBlueTooth() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Log.e("myToothName:",bluetoothAdapter.getName());//本地蓝牙名称
        //        检测是否有蓝牙并是否开启,调用isEnabled()方法来检查蓝牙目前是否可用,未开启则尝试开启蓝牙
        if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }

        //扫描全部蓝牙设备。
        //bluetoothAdapter.startLeScan(BluetoothAdapter.LeScanCallback callback)
        //只扫描含有特定的UUID Service 的蓝牙设备
        // bluetoothAdapter.startLeScan(final UUID[] serviceUuids, final BluetoothAdapter.LeScanCallback callback);
        callback = new BluetoothAdapter.LeScanCallback() {
            // BluetoothDevice device 蓝牙设备的类
            // rssi 蓝牙的信号强弱指标 大概计算出蓝牙设备离手机的距离。
            // 计算公式为：d = 10^((abs(RSSI) - A) / (10 * n))
            // scanRecord 蓝牙广播出来的广告数据。
            @Override
            public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
               deviceList = new ArrayList<>();
                if (device != null) {
                    deviceList.add(device);
                }
                if (deviceList.size() > 0) {
                    for (BluetoothDevice devices : deviceList) {
                        tvDeviceInfo.setText("设备名称：" + devices.getName());
                    }
                } else {
                    tvDeviceInfo.setText("未连接到设备");
                }


            }
        };

    }

    boolean mScanning;
    Handler mHandler = new Handler();

    private void scanLeDevice(final boolean enable) {
        if (enable) {
            // Stops scanning after a pre-defined scan period.
            // 预先定义停止蓝牙扫描的时间（因为蓝牙扫描需要消耗较多的电量）
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mScanning = false;
                    bluetoothAdapter.stopLeScan(callback);
                }
            }, SCAN_PERIOD);
            mScanning = true;

            // bluetoothAdapter
            bluetoothAdapter.startLeScan(callback);
        } else {
            mScanning = false;
            bluetoothAdapter.stopLeScan(callback);
        }
    }

    /**
     * 连接设备
     */
    public void connectDevice(Context context, BluetoothDevice device) {
        //Context context,
        // boolean autoConnect,true:设备断开了，会不断的尝试自动连接 false:只进行一次尝试连接
        //BluetoothGattCallback callback
        // 连接后进行的一系列操作的回调：连接和断开连接 读写数据。。。
        device.connectGatt(context, true, new BluetoothGattCallback() {
            // BluetoothGatt gatt 蓝牙设备的 Gatt 服务连接类。

            // status 代表是否成功执行了连接操作 BluetoothGatt.GATT_SUCCESS=0  第三个参数才有效
            //status == 133 这是因为 Android 最多支持连接 6 到 7 个左右的蓝牙设备，
            // 如果超出了这个数量就无法再连接了。所以当我们断开蓝牙设备的连接时，
            // 还必须调用 BluetoothGatt#close 方法释放连接资源。
            // 否则，在多次尝试连接蓝牙设备之后很快就会超出这一个限制，导致出现这一个错误再也无法连接蓝牙设备。

            // newState 代表当前设备的连接状态，如果 newState == BluetoothProfile.STATE_CONNECTED=2 说明设备已经连接

            /**
             * 成功回调且表示成功连接之后调用 BluetoothGatt#discoverService 这一个方法
             * @param gatt
             * @param status
             * @param newState
             */
            @Override
            public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
                Log.e("Connection","status!!!="+status+"=newState="+newState);
            }

            /**
             * 被回调，手机设备和蓝牙设备才算是真正建立了可通信的连接
             * @param gatt
             * @param status
             */
            @Override
            public void onServicesDiscovered(BluetoothGatt gatt, int status) {
                Log.e("onServicesDiscovered","yeah!!!");
            }

            @Override
            public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
                super.onCharacteristicRead(gatt, characteristic, status);
            }

            @Override
            public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
                super.onCharacteristicWrite(gatt, characteristic, status);
            }
        });
    }
}
