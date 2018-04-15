package com.example.xuchichi.mytest.di;

import android.content.Context;
import android.util.Log;

/**
 * Created by xuchichi on 2018/4/8.
 */

public class UserStore {
    Context context;

    public UserStore(Context context) {
        this.context = context;
    }

    public void register() {
        Log.e("UserStoreRegister", "UserStoreRegister");
    }
}
