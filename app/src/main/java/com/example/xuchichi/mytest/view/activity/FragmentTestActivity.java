package com.example.xuchichi.mytest.view.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.xuchichi.mytest.R;
import com.example.xuchichi.mytest.view.fragment.ArticleFragment;
import com.example.xuchichi.mytest.view.fragment.HeadlinesFragment;

public class FragmentTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_test);


        //根据横竖屏幕动态添加Fragment
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (height > width) {//竖屏加载一个
            fragmentTransaction.add(android.R.id.content,new HeadlinesFragment());
        } else {
            fragmentTransaction.add(android.R.id.content,new ArticleFragment());
        }
        fragmentTransaction.commit();
    }
}
