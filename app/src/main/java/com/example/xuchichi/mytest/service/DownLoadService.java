package com.example.xuchichi.mytest.service;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.xuchichi.mytest.model.FileInfo;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by xuchichi on 2018/3/24.
 */

public class DownLoadService extends Service {
    public static final String DOWNLOAD_PATH= Environment.getExternalStorageDirectory().getAbsolutePath()+"/download";
    public static final String ACTION_START="ACTION_START";
    public static final String ACTION_STOP="ACTION_STOP";
    public static final String ACTION_UPDATE="ACTION_UPDATE";
    public static final int MSG_INIT=0;
    private DownLoadTask mTask;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //获得activity传来的参数
        Log.e("getAction",intent.getAction());
        if (intent.getAction().equals(ACTION_START)){
          FileInfo fileInfo= (FileInfo) intent.getSerializableExtra("fileInfo");
            //初始化线程
            Log.e("ACTION_START",fileInfo.toString());
            new InitThread(fileInfo).start();
        }

        if (ACTION_STOP.equals(intent.getAction())){
            FileInfo fileInfo= (FileInfo) intent.getSerializableExtra("fileInfo");
            Log.e("ACTION_STOP",fileInfo.toString());
            if (mTask!=null){
                mTask.isPause=true;
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
           switch (msg.what){
               case MSG_INIT:
                  FileInfo info= (FileInfo) msg.obj;
                   mTask=new DownLoadTask(DownLoadService.this,info);
                   mTask.downLoad();
                  Log.e("handleMessage",info.toString());
                   break;
           }
        }
    };
    /**
     * 初始化子线程
     */
    class InitThread extends Thread{
        FileInfo fileInfo;

        public InitThread(FileInfo fileInfo) {
            this.fileInfo = fileInfo;
        }

        @Override
        public void run() {
            HttpURLConnection connection=null;
            RandomAccessFile raf=null;
          try{
              //1.连接网络文件,获得文件长度
              //2本地创建文件，设置文件长度
              URL url=new URL(fileInfo.getUrl());
              connection= (HttpURLConnection) url.openConnection();
              connection.setConnectTimeout(3000);
              connection.setRequestMethod("GET");
              int length;
              if (connection.getResponseCode()==200){
                length=connection.getContentLength();
                if (length<=0){
                    return;
                }else{
                    File dir=new File(DOWNLOAD_PATH);
                    if (!dir.exists()){
                        dir.mkdir();
                    }
                    File file=new File(dir,fileInfo.getFileName());
                    //特殊的输出流 可以在文件任意位置进行写入操作 rwd:可读可写可删除
                     raf=new RandomAccessFile(file,"rwd");
                    raf.setLength(length);
                  fileInfo.setLength(length);
                  handler.obtainMessage(MSG_INIT,fileInfo).sendToTarget();
                }
              }


             }catch (Exception e){

                    e.printStackTrace();
              }finally {
                 if (connection!=null){
                     connection.disconnect();
                 }

                 try{
                     if (raf!=null){
                         raf.close();
                     }
                    }catch (Exception e){

                           e.printStackTrace();
                     }

          }
        }
    }
}
