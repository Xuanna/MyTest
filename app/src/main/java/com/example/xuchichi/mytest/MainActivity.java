package com.example.xuchichi.mytest;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.xuchichi.mytest.db.MySqliteHelper;
import com.example.xuchichi.mytest.net.httpUrlConnection.HttpDownImageThread;
import com.example.xuchichi.mytest.utils.DbManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv)
    ImageView iv;
    @BindView(R.id.iv2)
    ImageView iv2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //标题必须在support之前设置
        //调用supportActionBar之后，menu就没用了 toolbar.inflateMenu(R.menu.menu_main);失效
        //推荐和actionBar一起使用
        toolbar.setTitle("Title");
        toolbar.setSubtitle("SubTitle");
        toolbar.setNavigationIcon(R.mipmap.ic_launcher);
        toolbar.inflateMenu(R.menu.menu_main);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.addFriend:
                        Log.e("Log", "addFriend");
                        break;
                    case R.id.scan:
                        Log.e("Log", "scan");
                        break;
                }
                return true;
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Log", "onclick");
            }
        });

        Handler handlder = new Handler();
        HttpDownImageThread thread = new HttpDownImageThread("https://pic1.zhimg.com/80/v2-47af1b232ed71db67d9ce7fcabcbf710_hd.jpg", iv, handlder);
        HttpDownImageThread threads = new HttpDownImageThread("https://pic1.zhimg.com/80/v2-47af1b232ed71db67d9ce7fcabcbf710_hd.jpg", iv2, handlder);
        thread.start();
        threads.start();

    }
    public void onCreadDb(){
        //getReadableDatabase getWritableDatabase 创建或打开数据库
        //如果数据库存在直接打开，不存在则创建

       MySqliteHelper sqliteHelper=DbManager.getInstance(this);
       SQLiteDatabase database=sqliteHelper.getWritableDatabase();

    }

}
