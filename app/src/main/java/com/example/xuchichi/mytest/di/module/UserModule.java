package com.example.xuchichi.mytest.di.module;

import android.content.Context;
import android.util.Log;

import com.example.xuchichi.mytest.annotation.ReleaseAnnotation;
import com.example.xuchichi.mytest.annotation.TestAnnotation;
import com.example.xuchichi.mytest.di.ApiService;
import com.example.xuchichi.mytest.di.UserManager;
import com.example.xuchichi.mytest.di.UserStore;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

/**
 * Created by xuchichi on 2018/4/8.
 */

@Module //(includes = HttpModule.class)//引用HttpClient
public class UserModule {


    private Context context;

    public UserModule(Context context) {
        this.context = context;
    }


    //    //提供依赖
    @Provides
//    @TestAnnotation
//    @ReleaseAnnotation//只需要修改这里就可以
//    @Named("dev")
    public ApiService provideApiService(OkHttpClient okHttpClient) {

        return new ApiService(okHttpClient);
    }

    @Provides
    public UserStore provideUserStore() {

        return new UserStore(context);
    }

    /**
     * //1 provideApiService提供 ApiService apiService对象
     * 2。在Apiservice  @Inject public ApiService() {}
     *
     * @param apiService
     * @return
     */
    @Provides
    public UserManager provideUserManager(ApiService apiService, UserStore userStore) {

        return new UserManager(apiService, userStore);

    }

}
