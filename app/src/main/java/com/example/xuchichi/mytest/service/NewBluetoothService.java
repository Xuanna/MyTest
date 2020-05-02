package com.example.xuchichi.mytest.service;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * Created by xuchichi on 2018/4/30.
 */

public class NewBluetoothService {
    public String NAME = "BluetoothService";

    public UUID MY_UUID = UUID.fromString("00000000-0000-1000-8000-00805F9B34FB");

    BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    public String TAG = "tag";

    private AcceptThread mAcceptThread;
    private ConnectThread mConnectThread;

    //用于 蓝牙连接的Activity onResume()方法
    public synchronized void start() {

        if (mConnectThread != null) {
            mConnectThread.cancel();
            mConnectThread = null;
        }

        if (mAcceptThread == null) {
            mAcceptThread = new AcceptThread();
            mAcceptThread.start();
        }
    }

    /**
     * 服务端的实现
     */
    public class AcceptThread extends Thread {
        BluetoothServerSocket mmServerSocket;

        public AcceptThread() {
            BluetoothServerSocket tmp = null;
//            Class btClass = bluetoothAdapter.getClass();
//
//            try {
//                Method listenMethod = btClass.getMethod("listenUsingRfcommOn", new Class[]{int.class});
//                tmp = (BluetoothServerSocket) listenMethod.invoke(bluetoothAdapter, new Object[]{29});
//
//            } catch (Exception e) {
//
//                e.printStackTrace();
//            }
            try {
                tmp = bluetoothAdapter.listenUsingRfcommWithServiceRecord(NAME, MY_UUID);
            } catch (IOException e) {
                Log.e(TAG, "listen failed", e);
                e.printStackTrace();
            }
            mmServerSocket = tmp;

        }

        public void run() {
            BluetoothSocket socket = null;
            // Keep listening until exception occurs or a socket is returned
            while (true) {
                try {
                    socket = mmServerSocket.accept();
                } catch (IOException e) {
                    break;
                }
                // If a connection was accepted
                if (socket != null) {
                    // Do work to manage the connection (in a separate thread)
//                    manageConnectedSocket(socket);
                    cancel();
                    break;
                }
            }
        }

        public void cancel() {
            try {
                mmServerSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void connect(BluetoothDevice mmDevice) {
        ConnectThread connectThread = new ConnectThread(mmDevice);
        connectThread.start();
    }

    /**
     * 客户端
     */
    public class ConnectThread extends Thread {

        BluetoothDevice mmDevice;
        BluetoothSocket mmSocket;

        public ConnectThread(BluetoothDevice mmDevice) {
            this.mmDevice = mmDevice;

            BluetoothSocket tmp = null;
//            try {
//                Method m = mmDevice.getClass().getMethod("createRfcommSocket", new Class[]{int.class});
//                tmp = (BluetoothSocket) m.invoke(mmDevice, 1);
//
//            } catch (Exception e) {
//
//                e.printStackTrace();
//            }
            try {
                // MY_UUID is the app's UUID string, also used by the server code
                tmp = mmDevice.createRfcommSocketToServiceRecord(MY_UUID);
            } catch (IOException e) {
                Log.e(TAG, "create failed", e);
                e.printStackTrace();
            }
            mmSocket = tmp;
        }

        public void run() {
            bluetoothAdapter.cancelDiscovery();
            try {
                // Connect the device through the socket. This will block
                // until it succeeds or throws an exception
                mmSocket.connect();
            } catch (IOException connectException) {
                // Unable to connect; close the socket and get out
                try {
                    mmSocket.close();
                } catch (IOException closeException) {
                }
                return;
            }
//            try {
//                mmSocket.connect();
//            } catch (IOException e) {
            // Make a connection to the BluetoothSocket
//            try {
//
//                mmSocket.connect();
//            } catch (Exception e1) {
//                Log.e("e1", "Couldn't establish Bluetooth connection!");
//                e1.printStackTrace();
//                try {
//                    mmSocket.close();
//                } catch (IOException e2) {
//                    Log.e("e2", "Couldn't establish Bluetooth connection!");
//                    e1.printStackTrace();
//                }
//                return;
//            }

//            }
            Log.e(TAG, "connect success");
            //此时可以新建一个数据交换线程，把此socket传进去
//            connected(socket, mBluetoothDevice);// Start the connected thread
        }

        /**
         * 取消监听
         */
        public void cancel() {
            try {
                mmSocket.close();

            } catch (Exception e) {
                Log.e(TAG, "close of connect socket failed");

                e.printStackTrace();
            }
        }

    }

}
