package com.example.xuchichi.mytest.service;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.xuchichi.mytest.db.ThreadDao;
import com.example.xuchichi.mytest.db.ThreadDaoImpl;
import com.example.xuchichi.mytest.model.FileInfo;
import com.example.xuchichi.mytest.model.ThreadInfo;
import com.example.xuchichi.mytest.utils.ToastUtils;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by xuchichi on 2018/3/24.
 * 下载文件类
 */

public class DownLoadTask {
    private Context context;
    private FileInfo fileInfo;
    private ThreadDao threadDao;
    public boolean isPause;

    public DownLoadTask(Context context, FileInfo fileInfo) {
        this.context = context;
        this.fileInfo = fileInfo;
        threadDao = new ThreadDaoImpl(context);
    }
    public void downLoad(){
        //读取数据库线程信息然后再启动线程下载
        List<ThreadInfo> threadInfos=threadDao.getThread(fileInfo.getUrl());
        ThreadInfo threadInfo;
        if (threadInfos.size()==0){
            threadInfo=new ThreadInfo(0,fileInfo.getUrl(),0,fileInfo.getLength(),0);
        }else{
            threadInfo=threadInfos.get(0);
        }
        new DownLoadThread(threadInfo).start();

    }
    /**
     * 下载线程
     */
    class DownLoadThread extends Thread {
        ThreadInfo threadInfo = null;

        public DownLoadThread(ThreadInfo threadInfo) {
            this.threadInfo = threadInfo;
        }

        @Override
        public void run() {

            // 向数据库插入线程信息】
            if (threadDao.isExist(threadInfo.getUrl(),threadInfo.getId())){
                threadDao.insertThread(threadInfo);
            }
            HttpURLConnection connection=null;
            Intent intent;
            int finished=0;//下载进度
            RandomAccessFile raf=null;
            InputStream is=null;

            try{
                URL url=new URL(threadInfo.getUrl());
                connection= (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(3000);
                connection.setRequestMethod("GET");
                // 设置下载位置
                int start=threadInfo.getStart()+threadInfo.getFinish();

                connection.setRequestProperty("Range","bytes="+start+"-"+threadInfo.getEnd());//开始的字节数到结束的字节数

                // 设置文件的写入位置
                File file=new File(DownLoadService.DOWNLOAD_PATH+fileInfo.getFileName());
                 raf=new RandomAccessFile(file,"rwd");
                //seek();在读写的时候跳过设置好的字节数，从下一个字节数开始读写 例如seek（200），则跳过200字节
                raf.seek(start);
                intent=new Intent(DownLoadService.ACTION_UPDATE);

                finished+=threadInfo.getFinish();
                Log.e("code",connection.getResponseCode()+"");
                //开始下载
                if (connection.getResponseCode()==206){
                    Log.e("if","if");
                     is=connection.getInputStream();
                  byte bytes[]=new byte[1024];
                  int len;
                  long time=System.currentTimeMillis();
                    //写入文件
                  while ((len=is.read(bytes))!=-1){
                      raf.write(bytes,0,len);//本地数据的写入
                      //把下载进度发送给Activity
                      finished+=len;
                      if (System.currentTimeMillis()-time>500){
                          time=System.currentTimeMillis();
                          intent.putExtra("finished",finished*100/fileInfo.getLength());
                          context.sendBroadcast(intent);
                      }
                      //下载暂停时，保存下载进度
                      if (isPause){
                          threadDao.updateThread(threadInfo.getUrl(),threadInfo.getId(),finished);
                          return;
                      }
                  }
                }else{
                    Log.e("else","else");
                }
            //下载完成，删除线程信息
                threadDao.deleteThread(threadInfo.getUrl(), threadInfo.getId());

                //下载的进度发送广播给Activity

               }catch (Exception e){

                      e.printStackTrace();
                }finally {
                   connection.disconnect();
                   try{
                       raf.close();
                       is.close();

                      }catch (Exception e){

                             e.printStackTrace();
                       }


            }



        }
    }
}
