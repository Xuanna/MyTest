package com.example.xuchichi.mytest.view.activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.xuchichi.mytest.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyBluetoothActivity extends AppCompatActivity {

    @BindView(R.id.btnScan)
    Button btnScan;
    @BindView(R.id.tvDeviceInfo)
    TextView tvDeviceInfo;
    @BindView(R.id.btnStop)
    Button btnStop;
    @BindView(R.id.btnConnect)
    Button btnConnect;
    BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    @BindView(R.id.tvBluetoothInfo)
    TextView tvBluetoothInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
        ButterKnife.bind(this);
        init();
    }

    public final int ACTION_REQUEST_ENABLE = 1;

    public void init() {
        if (bluetoothAdapter == null) {
            tvDeviceInfo.setText("该设备无蓝牙功能");
        }
        tvBluetoothInfo.setText(bluetoothAdapter.getName());
        if (!bluetoothAdapter.isEnabled()) {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, ACTION_REQUEST_ENABLE);
        }
        IntentFilter filter = new IntentFilter();
        //发现设备
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        //设备连接状态改变
        filter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        //蓝牙设备状态改变
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);

        registerReceiver(receiver, filter);
    }

    public void scanDevice() {
        if (bluetoothAdapter.isEnabled()) {
            Log.e("isEnabled", "isEnabled");
            bluetoothAdapter.startDiscovery();//扫描设备，系统会发送广播
        } else {
            Log.e("else", "else");
        }
    }

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case BluetoothDevice.ACTION_FOUND:
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    tvDeviceInfo.setText(device.getName());
                    break;
                case BluetoothDevice.ACTION_BOND_STATE_CHANGED:
                    break;
                case BluetoothAdapter.ACTION_STATE_CHANGED:
                    break;

            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ACTION_REQUEST_ENABLE:
                if (resultCode == RESULT_OK) {
                    tvBluetoothInfo.setText("蓝牙开启成功");
                }
                break;
        }
    }

    @OnClick({R.id.btnScan, R.id.btnStop, R.id.btnConnect})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnScan:
                scanDevice();
                break;
            case R.id.btnStop:
                bluetoothAdapter.cancelDiscovery();
                break;
            case R.id.btnConnect:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}
