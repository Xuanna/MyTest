package com.example.xuchichi.mytest.net.httpUrlConnection;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by xuchichi on 2018/3/20.
 */

public class HttpThread extends Thread {
    String url = null;
    String name;
    String pwd;

    /**
     * post请求
     */
    public void doPost(){
        try{
            URL urls=new URL(url);
            HttpURLConnection connection= (HttpURLConnection) urls.openConnection();
            connection.setReadTimeout(500);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            //获得输出流
            OutputStream out=connection.getOutputStream();
            String content="name="+name+"&pwd="+pwd;
            out.write(content.getBytes());
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String str;
            StringBuffer stringBuffer=new StringBuffer();
            while ((str=bufferedReader.readLine())!=null){
                stringBuffer.append(str);
            }
            Log.e("response",stringBuffer.toString());
           }catch (Exception e){

                  e.printStackTrace();
            }

    }

    /**
     * get请求
     * 发送的数据量较小，信息会暴露
     */
    public void doGet() {

        url=url+"?name="+name+"&pwd="+pwd;
        try {
            URL urls = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urls.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(500);

            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String str;
            StringBuffer stringBuffer=new StringBuffer();
            while ((str=bufferedReader.readLine())!=null){
                stringBuffer.append(str);
            }
            Log.e("response",stringBuffer.toString());
        } catch (Exception e) {

            e.printStackTrace();
        }


    }

    @Override
    public void run() {
        super.run();
    }
}
