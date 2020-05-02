package com.example.xuchichi.mytest.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class BankOpenhelper extends SQLiteOpenHelper {
    public BankOpenhelper(@Nullable Context context) {
        super(context, "bank.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table user(_id integer primary key autoincrement,name varchar(20),amount varchar(50))");
        db.execSQL("insert into user('name','amount') values ('张三','2000')");
        db.execSQL("insert into user('name','amount') values ('李四','5000')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
