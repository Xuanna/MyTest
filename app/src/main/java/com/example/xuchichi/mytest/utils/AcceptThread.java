package com.example.xuchichi.mytest.utils;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by xuchichi on 2018/3/28.
 * 作为服务器端
 */

public class AcceptThread extends Thread {
    public final String NAME = "";
    public final UUID NAMY_UUIDME = UUID.fromString("");

    BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    BluetoothServerSocket serverSocket;

    public AcceptThread() {
        BluetoothServerSocket tmp = null;
        try {
            tmp = mBluetoothAdapter.listenUsingInsecureRfcommWithServiceRecord(NAME, NAMY_UUIDME);

        } catch (Exception e) {

            e.printStackTrace();
        }
        serverSocket = tmp;
    }

    @Override
    public void run() {
        BluetoothSocket bluetoothSocket = null;
        try {
            bluetoothSocket = serverSocket.accept();
        } catch (Exception e) {

            e.printStackTrace();
        }
        if (bluetoothSocket != null) {
            // Do work to manage the connection(in a separate thread)
            try {

                serverSocket.close();
            } catch (Exception e) {

                e.printStackTrace();
            }

        }

    }
    public void cancel() {
        try {
            serverSocket.close();
        } catch (IOException e) { }
    }
}
