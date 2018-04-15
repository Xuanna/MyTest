package com.example.xuchichi.mytest.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.xuchichi.mytest.MyApplication;
import com.example.xuchichi.mytest.R;
import com.example.xuchichi.mytest.di.UserManager;
import com.example.xuchichi.mytest.di.component.DaggerLoginComponent;
import com.example.xuchichi.mytest.di.module.HttpModule;
import com.example.xuchichi.mytest.di.module.UserModule;

import javax.inject.Inject;

public class LoginActivity extends AppCompatActivity {

    @Inject
    UserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        DaggerLoginComponent.builder().userModule(new UserModule(this))
                .appComponent(MyApplication.getAppComponent())
                .userModule(new UserModule(this))
                .build()
                .inject(this);

      /*  DaggerLoginComponent.builder()
                .userModule(new UserModule(this))
                .httpModule(new HttpModule())
                .build()
                .inject(this);
*/
        userManager.login();
    }
}
