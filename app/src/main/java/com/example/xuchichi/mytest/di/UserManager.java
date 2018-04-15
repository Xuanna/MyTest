package com.example.xuchichi.mytest.di;

import android.util.Log;

/**
 * Created by xuchichi on 2018/4/8.
 */

public class UserManager {

    private ApiService apiService;
    private UserStore userStore;

//    public UserManager(ApiService apiService) {
//        this.apiService = apiService;
//    }

    public void login(){
        Log.e("UserManager","login");
        apiService.login();
    }
    public UserManager(ApiService apiService, UserStore userStore) {
        this.apiService = apiService;
        this.userStore = userStore;
    }


    public void register() {
        apiService.register();
        userStore.register();
    }

}
