package com.example.xuchichi.mytest.utils;

import android.content.Context;

import com.example.xuchichi.mytest.db.MySqliteHelper;

/**
 * Created by xuchichi on 2018/3/21.
 * 数据库工具类
 */

public class DbManager {
    public static MySqliteHelper sqliteHelper;
    public static MySqliteHelper getInstance(Context context){
        if (sqliteHelper==null){
            sqliteHelper=new MySqliteHelper(context);
        }
        return sqliteHelper;

    }

}
