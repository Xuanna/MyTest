package com.example.xuchichi.mytest.service;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.xuchichi.mytest.utils.Constant;
import com.example.xuchichi.mytest.view.activity.MyBluetoothActivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * Created by xuchichi on 2018/4/26.
 */

public class MyBluetoothService {

    private static final String TAG = "MyBluetoothService";

    private static final UUID MY_UUID =
            UUID.fromString("00001000-0000-1000-8000-00805F9B34FB");
    // Name for the SDP record when creating server socket
    private static final String NAME = "BluetoothSecure";

    private Handler mHandler;

    private AcceptThread acceptThread;
    private ConnectedThread mConnectedThread;
    private ConnectThread mConnectThread;
    private BluetoothAdapter mAdapter;

    private int mState;
    private int mNewState;

    // Constants that indicate the current connection state
    public static final int STATE_NONE = 0;       // we're doing nothing
    public static final int STATE_LISTEN = 1;     // now listening for incoming connections
    public static final int STATE_CONNECTING = 2; // now initiating an outgoing connection
    public static final int STATE_CONNECTED = 3;  // now connected to a remote device

    public MyBluetoothService(Handler handler) {
        mAdapter = BluetoothAdapter.getDefaultAdapter();
        mState = STATE_NONE;
        mNewState = mState;
        mHandler = handler;
    }

    //用于 蓝牙连接的Activity onResume()方法
    public synchronized void start() {
        // Cancel any thread attempting to make a connection
        if (mConnectThread != null) {
            mConnectThread.cancel();
            mConnectThread = null;
        }

        if (acceptThread == null) {
            acceptThread = new AcceptThread();
            acceptThread.start();
        }
        setState(STATE_LISTEN);
    }

//    /**
//     * 开启服务端
//     * Start the chat service. Specifically start AcceptThread to begin a
//     * session in listening (server) mode. Called by the Activity onResume()
//     */
//    public synchronized void start() {
//        Log.e(TAG, "start");
//
//        // Cancel any thread attempting to make a connection
//        if (mConnectThread != null) {
//            mConnectThread.cancel();
//            mConnectThread = null;
//        }
//
//        // Cancel any thread currently running a connection
//        if (mConnectedThread != null) {
//            mConnectedThread.cancel();
//            mConnectedThread = null;
//        }
//
//        // Start the thread to listen on a BluetoothServerSocket
//        if (acceptThread == null) {
//            acceptThread = new AcceptThread();
//            acceptThread.start();
//        }
//        // Update UI title
//        updateUserInterfaceTitle();
//    }

    /**
     * 设备连接
     * Start the ConnectThread to initiate a connection to a remote device.
     *
     * @param device The BluetoothDevice to connect
     */
    public synchronized void connect(BluetoothDevice device) {
        Log.e(TAG, "connect to: " + device);
        // Cancel any thread attempting to make a connection
        if (mState == STATE_CONNECTING) {
            if (mConnectThread != null) {
                mConnectThread.cancel();
                mConnectThread = null;
            }
        }

        // Cancel any thread currently running a connection
        if (mConnectedThread != null) {
            mConnectedThread.cancel();
            mConnectedThread = null;
        }

        // Start the thread to connect with the given device
        mConnectThread = new ConnectThread(device);
        mConnectThread.start();
        // Update UI title
        updateUserInterfaceTitle();

    }

    /**
     * Start the ConnectedThread to begin managing a Bluetooth connection
     *
     * @param socket The BluetoothSocket on which the connection was made
     * @param device The BluetoothDevice that has been connected
     */
    public synchronized void connected(BluetoothSocket socket, BluetoothDevice device) {
        Log.e(TAG, "connected, Socket Type:");
        // Cancel the thread that completed the connection
        if (mConnectThread != null) {
            mConnectThread.cancel();
            mConnectThread = null;
        }

        // Cancel any thread currently running a connection
        if (mConnectedThread != null) {
            mConnectedThread.cancel();
            mConnectedThread = null;
        }

        // Cancel the accept thread because we only want to connect to one device
        if (acceptThread != null) {
            acceptThread.cancel();
            acceptThread = null;
        }
        // Start the thread to manage the connection and perform transmissions
        mConnectedThread = new ConnectedThread(socket);
        mConnectedThread.start();

        // Send the name of the connected device back to the UI Activity
        Message msg = mHandler.obtainMessage(Constant.MESSAGE_DEVICE_NAME);
        Bundle bundle = new Bundle();
        bundle.putString(Constant.DEVICE_NAME, device.getName());
        msg.setData(bundle);
        mHandler.sendMessage(msg);
        // Update UI title
        updateUserInterfaceTitle();
    }

    /**
     * 启动服务器端的线程
     * 手机相当于服务器
     */
    public class AcceptThread extends Thread {


        private BluetoothServerSocket serverSocket;

        public AcceptThread() {
            BluetoothServerSocket tmp = null;

            try {

                tmp = mAdapter.listenUsingRfcommWithServiceRecord(NAME, MY_UUID);
            } catch (Exception e) {
                Log.e(TAG, "listen failed", e);
                e.printStackTrace();
            }
            serverSocket = tmp;
            Log.e(TAG, "server start");

        }

        @Override
        public void run() {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Log.e(TAG, "Socket Type: " +
                            "BEGIN mAcceptThread" + this);
                    setName("AcceptThread");

                    BluetoothSocket socket;
                    while (mState != STATE_CONNECTED) {
                        try {
                            // This is a blocking call and will only return on a
                            // successful connection or an exception
                            socket = serverSocket.accept();
                        } catch (IOException e) {
                            Log.e(TAG, "Socket Type: " + "accept() failed", e);
                            break;
                        }

                        // If a connection was accepted
                        if (socket != null) {
                            Log.e(TAG, "server socket not null");
                            synchronized (MyBluetoothService.this) {
                                switch (mState) {
                                    case STATE_LISTEN:
                                    case STATE_CONNECTING:
                                        // Situation normal. Start the connected thread.
                                        connected(socket, socket.getRemoteDevice());
                                        break;
                                    case STATE_NONE:
                                    case STATE_CONNECTED:
                                        // Either not ready or already connected. Terminate new socket.
                                        try {
                                            socket.close();
                                        } catch (IOException e) {
                                            Log.e(TAG, "Could not close unwanted socket", e);
                                        }
                                        break;
                                }
                            }
                        }
                    }
                    Log.i(TAG, "END mAcceptThread, socket Type: ");
                }
            }).start();
//            BluetoothSocket socket = null;
//
//            while (true) {
//                try {
//                    socket = serverSocket.accept();
//
//                } catch (Exception e) {
//                    Log.e(TAG, "accept failed", e);
//                    e.printStackTrace();
//                }
//                if (socket != null) {
//
//                    //此时可以新建一个数据交换线程，把此socket传进去
//                    connected(socket, socket.getRemoteDevice());
//
//                }
//            }

        }

        /**
         * 取消监听
         */
        public void cancel() {
            try {
                serverSocket.close();

            } catch (Exception e) {

                e.printStackTrace();
            }
        }

    }

    /**
     * 另一个设备去连接本季，相当于客户端
     */
    public class ConnectThread extends Thread {

        private BluetoothDevice mBluetoothDevice;
        private BluetoothSocket socket;

        public ConnectThread(BluetoothDevice mBluetoothDevice) {
            this.mBluetoothDevice = mBluetoothDevice;

            BluetoothSocket tmp = null;
            try {
                tmp = mBluetoothDevice.createRfcommSocketToServiceRecord(MY_UUID);

            } catch (Exception e) {
                Log.e(TAG, "create failed", e);
                e.printStackTrace();
            }
            socket = tmp;
        }

        @Override
        public void run() {
            setName("ConnectThread");
            // Always cancel discovery because it will slow down a connection
            mAdapter.cancelDiscovery();
            // Make a connection to the BluetoothSocket
            try {
                Method m = mBluetoothDevice.getClass().getMethod("createRfcommSocket", new Class[]{int.class});
                socket = (BluetoothSocket) m.invoke(mBluetoothDevice, 1);
                socket.connect();
            } catch (Exception e) {

                e.printStackTrace();
            }
//            try {
//                socket.connect();
//                Log.e("socket", "Connected");
//            } catch (IOException e) {
//                Log.e("socketIOException", e.getMessage());
//                try {
//                    Log.e("", "trying fallback...");
//
//                    socket = (BluetoothSocket) mBluetoothDevice.getClass().getMethod("createRfcommSocket", new Class[]{int.class})
//                            .invoke(mBluetoothDevice, 1);
//                    socket.connect();
//
//                    Log.e("Connected", "Connected");
//                } catch (Exception e2) {
//                    Log.e("e2", "Couldn't establish Bluetooth connection!");
//                    connectionFailed(mBluetoothDevice);
//                }
//                MyBluetoothService.this.start();// 引用来说明要调用的是外部类的方法 run
//                //连接失败
//                return;
//            }
//            try {
//                // This is a blocking call and will only return on a
//                // successful connection or an exception
//                if (socket != null) {
//                    Log.e(TAG, "connect is not null");
//                    socket.connect();
//                } else {
//                    Log.e(TAG, "connect is null");
//                }
//
//            } catch (Exception e) {
//                Log.e(TAG, "connect failed", e);
//
//                // Close the socket
//                try {
//                    socket.close();
//
//                } catch (Exception es) {
//
//                    es.printStackTrace();
//                }
//                MyBluetoothService.this.start();// 引用来说明要调用的是外部类的方法 run
//                //连接失败
//                return;
//            }
            synchronized (MyBluetoothService.this) {// Reset the ConnectThread because we're done
                mConnectThread = null;
            }
            //此时可以新建一个数据交换线程，把此socket传进去
            connected(socket, mBluetoothDevice);// Start the connected thread


        }

        /**
         * 取消监听
         */
        public void cancel() {
            try {
                socket.close();

            } catch (Exception e) {
                Log.e(TAG, "close of connect socket failed");

                e.printStackTrace();
            }
        }
    }

    /**
     * 建立数据通信线程，进行读取数据
     */
    public class ConnectedThread extends Thread {
        private BluetoothSocket socket;
        private OutputStream outputStream;
        private InputStream inputStream;

        public ConnectedThread(BluetoothSocket socket) {
            this.socket = socket;

            try {
                inputStream = socket.getInputStream();
                outputStream = socket.getOutputStream();
            } catch (Exception e) {

                e.printStackTrace();
            }

        }

        @Override
        public void run() {
            byte[] buff = new byte[1024];
            int bytes;
            //读取数据需要不断监听，写数据则不需要
            while (true) {
                try {
                    bytes = inputStream.read(buff);
                    //len
                    mHandler.obtainMessage(Constant.MESSAGE_READ, bytes, -1, buff)
                            .sendToTarget();

                } catch (Exception e) {

                    e.printStackTrace();
                }

            }
        }

        public void wirte(byte[] buffer) {
            try {
                outputStream.write(buffer);
                // Share the sent message back to the UI Activity
                mHandler.obtainMessage(Constant.MESSAGE_WRITE, -1, -1, buffer)
                        .sendToTarget();
            } catch (Exception e) {

                Log.e(TAG, "Exception during write", e);
            }

        }

        public void cancel() {
            try {
                socket.close();
            } catch (IOException e) {
                Log.e(TAG, "close() of connect socket failed", e);
            }
        }
    }


    /**
     * Update UI title according to the current state of the chat connection
     */
    private synchronized void updateUserInterfaceTitle() {
        mState = getState();
        Log.e(TAG, "updateUserInterfaceTitle() " + mNewState + " -> " + mState);
        mNewState = mState;

        // Give the new state to the Handler so the UI Activity can update
        mHandler.obtainMessage(Constant.MESSAGE_STATE_CHANGE, mNewState, -1).sendToTarget();
    }

    /**
     * Return the current connection state.
     */
    public synchronized int getState() {
        return mState;
    }

    private synchronized void setState(int state) {
        mState = state;
        // Give the new state to the Handler so the UI Activity can update
        mHandler.obtainMessage(MyBluetoothActivity.MESSAGE_STATE_CHANGE, state, -1).sendToTarget();
    }

    public synchronized void stop() {
        if (mConnectedThread != null) {
            mConnectedThread.cancel();
            mConnectedThread = null;
        }
        if (acceptThread != null) {
            acceptThread.cancel();
            acceptThread = null;
        }
        setState(STATE_NONE);
    }

    //连接失败
    private void connectionFailed(BluetoothDevice device) {
        setState(STATE_LISTEN);
        // Send a failure message back to the Activity
        Message msg = mHandler.obtainMessage(MyBluetoothActivity.MESSAGE_TOAST);
        Bundle bundle = new Bundle();
        bundle.putString("toast", "未能连接设备" + device.getName());
        bundle.putInt("device_id", 1);
        msg.setData(bundle);
        mHandler.sendMessage(msg);
    }


}
