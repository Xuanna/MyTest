package com.example.xuchichi.mytest.net.httpUrlConnection;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by xuchichi on 2018/3/20.
 */

public class UploadThread extends Thread {
    private String url;
    private String fileName;

    @Override
    public void run() {
        String boundary=null;
        String prefix="--";
        String end="\r\n";
        try{
            URL urls=new URL(url);
            HttpURLConnection connection= (HttpURLConnection) urls.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setReadTimeout(500);
            connection.setRequestProperty("Content-Type","multipart/form-data;boundary="+boundary);

            DataOutputStream out=new DataOutputStream(connection.getOutputStream());
            out.writeBytes(prefix+boundary+ end);
//            out.writeBytes("Content-Disposition:form-data;);

            FileInputStream fileInputStream=new FileInputStream(new File(fileName));
            byte[] bytes=new byte[1024*4];
            int len;
            while ((len=fileInputStream.read(bytes))!=-1){
                out.write(bytes,0,len);
            }
            out.writeBytes(end);
            out.writeBytes(prefix+boundary+prefix+end);
            out.flush();

           }catch (Exception e){

                  e.printStackTrace();
            }

    }
}
