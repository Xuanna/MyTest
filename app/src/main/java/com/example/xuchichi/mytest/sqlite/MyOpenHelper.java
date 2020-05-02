package com.example.xuchichi.mytest.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

import java.sql.ResultSet;

public class MyOpenHelper extends SQLiteOpenHelper {

    public MyOpenHelper(@Nullable Context context) {
        super(context, "mytest.db", null, 4);
    }


    /**
     * 数据库第一次创建时调用
     * 特别适合做表结构的初始化
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e("ee","onCreate");
        db.execSQL("create table info(_id integer primary key autoincrement,name varchar(20))");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.e("ee","onUpgrade:"+newVersion);
        db.execSQL("alter table info add phone varchar(20)");
    }
}
