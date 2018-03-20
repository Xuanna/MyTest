package com.example.xuchichi.mytest.utils;

import com.example.xuchichi.mytest.model.UserInfo;
import com.google.gson.Gson;

/**
 * Created by xuchichi on 2018/3/19.
 */

public class UserUtils {

    public void saveUserInfo(UserInfo userInfo) {

        if (userInfo != null) {
            SharePerferenceUtil.putString("saveUser", new Gson().toJson(userInfo));
        }

    }

    public UserInfo getUserInfo() {

         String userStr= SharePerferenceUtil.getString("saveUser",null);

         return new Gson().fromJson(userStr,UserInfo.class);

    }
}
