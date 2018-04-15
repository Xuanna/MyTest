package com.example.xuchichi.mytest.di.component;

import com.example.xuchichi.mytest.annotation.ActivityScope;
import com.example.xuchichi.mytest.di.module.HttpModule;
import com.example.xuchichi.mytest.di.module.UserModule;
import com.example.xuchichi.mytest.view.activity.LoginActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by xuchichi on 2018/4/8.
 */
@ActivityScope
@Component(modules = {UserModule.class},dependencies = AppComponent.class)
public interface LoginComponent {

    void inject(LoginActivity activity);

}
