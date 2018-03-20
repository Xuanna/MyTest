package com.example.xuchichi.mytest.net.httpUrlConnection;

import com.example.xuchichi.mytest.model.Girl;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuchichi on 2018/3/20.
 */

public class XmlAnalysis extends Thread{
    String url;

    /**
     * 解析xml
     * <girls>
     *     <girl>
     *         <name>张三</name>
     *         <age>32</age>
     *         <school>北大</school>
     *     </girl>
     *
     * </girls>
     */
    @Override
    public void run() {
        try {
            URL urls = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urls.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(500);

            InputStream is=connection.getInputStream();
            XmlPullParserFactory factory=XmlPullParserFactory.newInstance();
            XmlPullParser parser=factory.newPullParser();
            parser.setInput(is,"utf-8");

            List<Girl> list=new ArrayList<>();

            Girl girl=null;

            int eventType=parser.getEventType();

            while (eventType!=XmlPullParser.END_DOCUMENT){

                String data=parser.getName();//获取标签名字

                switch (eventType){
                    case XmlPullParser.START_TAG:
                        if ("girl".equals(data)){
                            girl=new Girl();
                        }
                        if ("age".equals(data)){
                            girl.setAge(Integer.parseInt(parser.nextText()));
                        }
                        if ("name".equals(data)){
                            girl.setName(parser.nextText());
                        }
                        if ("school".equals(data)){
                            girl.setSchool(parser.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if ("girls".equals(data)&&girl!=null){
                            list.add(girl);
                        }
                        break;
                }
                eventType=parser.next();
            }


        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}
