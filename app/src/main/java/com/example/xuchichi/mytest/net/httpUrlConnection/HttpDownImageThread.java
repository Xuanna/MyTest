package com.example.xuchichi.mytest.net.httpUrlConnection;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import com.example.xuchichi.mytest.utils.MD5Util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by xuchichi on 2018/3/20.
 * 下载图片并保存sd卡，做缓存
 */

public class HttpDownImageThread extends Thread {

    public String url;
    public Handler handler;
    private ImageView imageView;

    public HttpDownImageThread(String url, ImageView imageView, Handler handler) {
        this.url = url;
        this.imageView = imageView;
        this.handler = handler;
    }

    @Override
    public void run() {
        HttpURLConnection connection = null;
        InputStream is = null;
        FileOutputStream fos = null;
        File file= null;
        try {

            URL httpUrl = new URL(url);
            //访问https 返回 HttpsURLConnection 获取图片
            connection = (HttpURLConnection) httpUrl.openConnection();
            connection.setReadTimeout(5000);
            connection.setRequestMethod("GET");
            connection.getContent();

            if (connection.getResponseCode() == 200) {

                is = connection.getInputStream();

                //1。保存文件sd卡再加载图片
                //1.创建文件路径 2。创建文件输出流 3。向文件中写入数据（先入去inputStream中的数据再进行写入）
                String time = String.valueOf(System.currentTimeMillis());//文件名即图片名
                //sd卡状态
                String state = Environment.getExternalStorageState();
                //sd卡目录
                File sdParent = Environment.getExternalStorageDirectory();

                if (state.equals(Environment.MEDIA_MOUNTED)) {//存在sd卡
                    //在sd卡目录中创建文件夹
                    File fileMdirs=new File(sdParent,"/textImageFile");
                    if (!fileMdirs.exists()){
                        fileMdirs.mkdirs();
                    }
                    //创建图片文件，如果图片已存在则不添加，图片存在则添加
                    file = new File(fileMdirs, MD5Util.string2MD5(url));
                    if (file.exists()){//文件存在
                        //从文件中获取数据流创建Bitmap
                        final Bitmap bitmap=   BitmapFactory.decodeFile(file.getAbsolutePath());

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                imageView.setImageBitmap(bitmap);
                            }
                        });
                    }else{//文件不存在 获取数据流的大小，再写入文件，
                        fos = new FileOutputStream(file);
                        /**
                         * 读取InputStream数据流并写入FileOutPutFile文件
                         * 考虑多个ImageView加载同一图的情况（加同步锁，防止多个线程同时创建文件）
                         */
                        int len;
                        byte[] bytes = new byte[2 * 1024];
                        //获取网络文件大小即下载总量
                        int  size = connection.getContentLength();
                        int downSize=0;//下载进度

                        while ((len = is.read(bytes)) != -1) {
                            if (fos!=null){
                                fos.write(bytes, 0, len);
                                downSize+=len;
                            }
                        }
                        if (size!=downSize){//是否下载完成(未完成)比如断网情况，可能导致下载一半，最终图片也只有一部分
                            file.delete();
                        }

                    }

                }

                //从文件中获取数据流创建Bitmap
               final Bitmap bitmap=   BitmapFactory.decodeFile(file.getAbsolutePath());

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        imageView.setImageBitmap(bitmap);
                    }
                });

//                //2根据数据流创建bitmap位图
//              final   Bitmap bitmap= BitmapFactory.decodeStream(is);
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        imageView.setImageBitmap(bitmap);
//                    }
//                });

            } else {
                Log.e("error", connection.getResponseCode() + "==" + connection.getResponseMessage());
            }
        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            try {
                if (is != null) {
                    is.close();
                }
                if (fos != null) {
                    fos.close();
                }

            } catch (Exception e) {

                e.printStackTrace();
            }

        }


    }
}
