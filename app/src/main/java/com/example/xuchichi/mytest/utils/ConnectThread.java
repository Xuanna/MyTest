package com.example.xuchichi.mytest.utils;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by xuchichi on 2018/3/28.
 * 连接为客户端
 * 1。使用 BluetoothDevice，通过调用 createRfcommSocketToServiceRecord(UUID) 获取 BluetoothSocket。
 * 2。connect连接
 */

public class ConnectThread extends Thread {
    public final String NAME = "";
    public final UUID NAMY_UUIDME = UUID.fromString("");
    BluetoothSocket mBluetoothSocket;
    BluetoothDevice mBluetoothDevice;
    BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    public ConnectThread(BluetoothDevice mBluetoothDevice) {
        this.mBluetoothDevice = mBluetoothDevice;
        BluetoothSocket socket = null;
        try {
            socket = mBluetoothDevice.createRfcommSocketToServiceRecord(NAMY_UUIDME);
        } catch (Exception e) {

            e.printStackTrace();
        }
        if (socket != null) {
            mBluetoothSocket = socket;
        }

    }

    @Override
    public void run() {
        // Cancel discovery because it will slow down the connection
        // 注：在调用 connect() 时，应始终确保设备未在执行设备发现。
        // 如果正在进行发现操作，则会大幅降低连接尝试的速度，并增加连接失败的可能性。?
        mBluetoothAdapter.cancelDiscovery();

        try {
            mBluetoothSocket.connect();

        } catch (Exception e) {

            // Unable to connect; close the socket and get out
            try {
                mBluetoothSocket.close();
            } catch (IOException closeException) {
            }
            return;
        }

        // Do work to manage the connection (in a separate thread)
        //manageConnectedSocket(mmSocket);
    }

    /**
     * Will cancel an in-progress connection, and close the socket
     */
    public void cancel() {
        try {
            mBluetoothSocket.close();
        } catch (IOException e) {
        }
    }
}
