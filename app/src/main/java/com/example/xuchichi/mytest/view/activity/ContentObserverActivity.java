package com.example.xuchichi.mytest.view.activity;

import android.content.ContentProvider;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.xuchichi.mytest.R;

/**
 *
 */
public class ContentObserverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_observer);

        Uri uri = Uri.parse("content://com.test.infodabase");
        MyContentObserver contentObserver = new MyContentObserver(handler);
        /**
         * true:前缀满足条件就可以
         * falsee:必须是完整的uri
         */
        getContentResolver().registerContentObserver(uri, true, contentObserver);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    /**
     * 当数据库内容发生改变的时候，回调
     */
    public class MyContentObserver extends ContentObserver {

        public MyContentObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
        }
    }
}
