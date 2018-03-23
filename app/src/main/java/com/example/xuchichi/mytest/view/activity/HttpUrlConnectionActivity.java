package com.example.xuchichi.mytest.view.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.xuchichi.mytest.R;
import com.example.xuchichi.mytest.net.httpUrlConnection.HttpDownImageThread;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HttpUrlConnectionActivity extends AppCompatActivity {

    @BindView(R.id.iv)
    ImageView iv;
    @BindView(R.id.iv2)
    ImageView iv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_url_connection);
        ButterKnife.bind(this);

        Handler handlder = new Handler();

        HttpDownImageThread thread = new HttpDownImageThread("https://pic1.zhimg.com/80/v2-47af1b232ed71db67d9ce7fcabcbf710_hd.jpg", iv, handlder);
        HttpDownImageThread threads = new HttpDownImageThread("https://pic1.zhimg.com/80/v2-47af1b232ed71db67d9ce7fcabcbf710_hd.jpg", iv2, handlder);
        thread.start();
        threads.start();
    }
}
