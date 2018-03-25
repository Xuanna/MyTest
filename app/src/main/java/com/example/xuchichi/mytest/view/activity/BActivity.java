package com.example.xuchichi.mytest.view.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.xuchichi.mytest.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BActivity extends AppCompatActivity {

    @BindView(R.id.button)
    Button button;
    @BindView(R.id.button2)
    Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);
        ButterKnife.bind(this);
        Log.e("BonCreate", "BonCreate");
        /**
         * 创建子线程Handler
         */
        HandlerThread handlerThread=new HandlerThread("HandlerThread");
        handlerThread.start();
        threadHander=new Handler(handlerThread.getLooper()){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Message message=new Message();
                //向主线程发送消息
                handler.sendMessageDelayed(message,1000);
                Log.e("threadHander","threadHander");
            }
        };
    }

    /**
     * 创建主线程handler
     */
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Message message=new Message();
            //向子线程发送消息
            threadHander.sendMessageDelayed(message,1000);
            Log.e("main","MainThread");
        }
    };

    Handler threadHander;

    @OnClick({R.id.button, R.id.button2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button:
                handler.sendEmptyMessage(1);
                break;
            case R.id.button2:
                handler.removeMessages(1);
                break;
        }
    }

    /**
     * 更新ui的方式
     */
    Handler handlers2=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    public void aa(){
        new Thread(){
            @Override
            public void run() {
                try{

                    Thread.sleep(500);
//                    handler1();
//                    handlers2
//                    handler3();
//                    handler4()
                   }catch (Exception e){

                          e.printStackTrace();
                    }

            }
        }.start();
    }
    //方式1
    public void handler1(){
        handlers2.post(new Runnable() {
            @Override
            public void run() {
                button.setText("134");
            }
        });
    }
    //方式2
    public void handler2(){
        handlers2.sendEmptyMessage(1);
    }
    //方式3
    public void handler3(){
       runOnUiThread(new Runnable() {
           @Override
           public void run() {
               button.setText("134");
           }
       });
    }
    //方式4
    public void handler4(){
        button.post(new Runnable() {
            @Override
            public void run() {
                button.setText("134");
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("BonStart", "BonStart");
    }

    /**
     * 创建ViewRootImpl，（更新u时，会检测当前线程是否是ui线程）
     * 所以在oncreat的子线程是可以更新ui的
     */
    @Override
    protected void onResume() {
        super.onResume();
        Log.e("BonResume", "BonResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("BonRestart", "BonRestart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("BonPause", "BonPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("BonStop", "BonStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("BonDestroy", "BonDestroy");
    }

}
