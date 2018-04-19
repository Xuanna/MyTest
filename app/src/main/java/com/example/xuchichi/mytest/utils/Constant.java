package com.example.xuchichi.mytest.utils;

/**
 * Created by xuchichi on 2018/3/21.
 */

public class Constant {
    public static final String DATABASE_NAME="info.db";//数据库名称
    public static final int DATABASE_VERSION=2;//数据库版本
    public static final String TABLE_NAME="person";//表名
    public static final String TABLE_DOWNLOAD_NAME="threadInfo";//表名
    public static final String DOWNLOAD_Id="thread_id";
    public static final String DOWNLOAD_URL="thread_url";
    public static final String DOWNLOAD_STRAT="thread_start";
    public static final String DOWNLOAD_END="thread_end";
    public static final String DOWNLOAD_FINISH="thread_finish";
    public static final String ID="id";
    public static final String NAME="name";
    public static final String AGE="age";
    // Message types sent from the BluetoothChatService Handler
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;

    // Key names received from the BluetoothChatService Handler
    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";
}
