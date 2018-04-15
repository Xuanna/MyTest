package com.example.xuchichi.mytest.di.component;

import com.example.xuchichi.mytest.annotation.ActivityScope;
import com.example.xuchichi.mytest.di.module.HttpModule;
import com.example.xuchichi.mytest.di.module.UserModule;
import com.example.xuchichi.mytest.view.activity.MyDaggerActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by xuchichi on 2018/4/8.
 * 连接器 container通过Component 获取UserModule(提供实例)
 * 或者(modules = {UserModule.class,,HttpModule.class}, dependencies = HttpCompnent.class)
 */
@ActivityScope
@Component(modules = {UserModule.class},dependencies = AppComponent.class)
public interface UserComponent {

    void inject(MyDaggerActivity activity);//MyDaggerActivity activity不能使用父类

}
