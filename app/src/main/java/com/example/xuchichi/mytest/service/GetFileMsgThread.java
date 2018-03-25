package com.example.xuchichi.mytest.service;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;

import com.example.xuchichi.mytest.model.FileInfo;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by xuchichi on 2018/3/25.
 */

public class GetFileMsgThread extends Thread {
    public static final String PATH=Environment.getExternalStorageDirectory() + "/mydownload";
    FileInfo fileInfo;


    public GetFileMsgThread(FileInfo fileInfo,Handler handler) {
        this.fileInfo = fileInfo;
        this.handler=handler;
    }
    Handler handler;

    @Override
    public void run() {
        try {
            URL url = new URL(fileInfo.getUrl());
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setConnectTimeout(5000);

            if (con.getResponseCode() == 200) {
                int length = con.getContentLength();
                if (length <= 0) {
                    return;
                } else {
                    File mdr = new File(PATH);
                    if (!mdr.exists()) {
                        mdr.mkdirs();
                    }
                    File file = new File(mdr, fileInfo.getFileName());
                    RandomAccessFile asf = new RandomAccessFile(file, "rwd");
                    asf.setLength(length);
                    fileInfo.setLength(length);

                    DoDownThread thread=new DoDownThread(fileInfo,handler);
                    thread.start();
                }

            }


        } catch (Exception e) {

            e.printStackTrace();
        }

    }

}
