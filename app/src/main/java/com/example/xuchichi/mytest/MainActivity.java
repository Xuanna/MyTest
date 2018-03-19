package com.example.xuchichi.mytest;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.net.URL;

public class MainActivity extends AppCompatActivity {
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    /**
     * Params:表示参数的类型
     * Progress：表示后台任务的执行进度的类型
     * result：表示后台任务返回结果的类型
     */
    private class DownloadFileTask extends AsyncTask<URL,Integer,Long>{
        //在线程池中执行，此方法用于执行异步任务，params表示异步任务的输入参数，
        //在此方法中可以通过publishProgress方法来更新任务的进度，publishProgress方法会调用
        //onProgressUpdate方法，另外此方法需要返回计算结果给onPostExecute（）方法
        @Override
        protected Long doInBackground(URL... urls) {
            return null;
        }
        //在主线程中执行，在异步任务执行之前，会被调用，一般可以用于做一些准备工作
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        //在主线程中执行，在异步任务执行之后，被调用，其中result参数是后台任务的返回值，即是doInbackground的返回值
        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);
        }
        //在主线程中执性，当后台任务的执行进度发生改变时此方法被调用
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }
}
