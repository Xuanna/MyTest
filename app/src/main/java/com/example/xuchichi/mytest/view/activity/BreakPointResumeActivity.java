package com.example.xuchichi.mytest.view.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.xuchichi.mytest.R;
import com.example.xuchichi.mytest.model.FileInfo;
import com.example.xuchichi.mytest.service.DoDownThread;
import com.example.xuchichi.mytest.service.DownLoadService;
import com.example.xuchichi.mytest.service.GetFileMsgThread;
import com.example.xuchichi.mytest.utils.SharePerferenceUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 断点续传
 */
public class BreakPointResumeActivity extends AppCompatActivity {

    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.btStop)
    Button btStop;
    @BindView(R.id.btStart)
    Button btStart;
    @BindView(R.id.down_progressBar)
    ProgressBar downProgressBar;
    @BindView(R.id.down)
    Button down;
    @BindView(R.id.downPause)
    Button downPause;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_break_point_resume);
        ButterKnife.bind(this);
        init();
        /**
         * 注册广播接收器
         *
         */
        IntentFilter intentFilter = new IntentFilter(DownLoadService.ACTION_UPDATE);
        registerReceiver(broadcastReceiver, intentFilter);

    }

    FileInfo fileInfo;

    public void init() {
        progressBar.setMax(100);
        //
        fileInfo = new FileInfo(0, "Kugou_mac_2.0.2.dmg",
                "http://sw.bos.baidu.com/sw-search-sp/software/b3282eadef1fd/Kugou_mac_2.0.2.dmg", 0, 0);

    }

    @OnClick({R.id.btStop, R.id.btStart, R.id.down, R.id.downPause})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {//通过intent传递fileInfo给service
            case R.id.btStop:
                intent = new Intent(BreakPointResumeActivity.this, DownLoadService.class);
                intent.setAction(DownLoadService.ACTION_STOP);
                intent.putExtra("fileInfo", fileInfo);
                startService(intent);
                break;
            case R.id.btStart:
                intent = new Intent(BreakPointResumeActivity.this, DownLoadService.class);
                intent.setAction(DownLoadService.ACTION_START);
                intent.putExtra("fileInfo", fileInfo);
                startService(intent);
                break;
            case R.id.down:
                GetFileMsgThread loadThread = new GetFileMsgThread(fileInfo,handler);
                loadThread.start();
                break;
            case R.id.downPause:
                DoDownThread.isPause = true;
                break;
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int p = msg.what;
            downProgressBar.setProgress(p);

        }
    };

    /**
     * 更新ui的广播接收器
     */
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() == DownLoadService.ACTION_UPDATE) {
                int finish = intent.getIntExtra("finished", 0);
                progressBar.setProgress(finish);
            }

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

}
