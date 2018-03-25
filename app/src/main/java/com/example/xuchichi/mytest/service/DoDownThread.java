package com.example.xuchichi.mytest.service;

import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.example.xuchichi.mytest.model.FileInfo;
import com.example.xuchichi.mytest.model.ThreadInfo;
import com.example.xuchichi.mytest.utils.SharePerferenceUtil;
import com.google.gson.Gson;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by xuchichi on 2018/3/25.
 */

public class DoDownThread extends Thread {
    public static  boolean isPause;
    private ThreadInfo threadInfo;//下载信息
    private FileInfo fileInfo;//文件信息
    Handler handler;

    public DoDownThread(FileInfo fileInfo, Handler handler){
        this.fileInfo=fileInfo;
        this.handler=handler;
    }

    @Override
    public void run() {
            String str= SharePerferenceUtil.getString("getThreadInfo","");
            if (!TextUtils.isEmpty(str)){
                Log.e("str","notnull");
                threadInfo=new Gson().fromJson(str,ThreadInfo.class);
            }else{
                Log.e("str","null");
                threadInfo=new ThreadInfo(fileInfo.getId(),fileInfo.getUrl(),0,fileInfo.getLength(),0);
            }
        int finish=0;
        try {
            URL url = new URL(fileInfo.getUrl());
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setConnectTimeout(5000);
            //获取上次的下载信息
            int start=threadInfo.getStart()+threadInfo.getFinish();
             finish+=threadInfo.getFinish();
            con.setRequestProperty("Range","bytes="+start+"-"+fileInfo.getLength());

            if (con.getResponseCode()==206){
                InputStream is=con.getInputStream();
                File file=new File(GetFileMsgThread.PATH+fileInfo.getFileName());
                RandomAccessFile raf=new RandomAccessFile(file,"rwd");
                byte[] buffer=new byte[1024];
                int len;
                while ((len=is.read(buffer))!=-1){
                    raf.seek(start);
                    finish+=len;
                    //将下载进度传给activity
                    handler.obtainMessage(finish*100/fileInfo.getLength()).sendToTarget();
                    if (isPause){
                        threadInfo=new ThreadInfo(fileInfo.getId(),fileInfo.getUrl(),start,fileInfo.getLength(),finish);
                        SharePerferenceUtil.putString("getThreadInfo",new Gson().toJson(threadInfo));
                        return;
                    }
                }
            }


        } catch (Exception e) {

            e.printStackTrace();
        }

    }
}
