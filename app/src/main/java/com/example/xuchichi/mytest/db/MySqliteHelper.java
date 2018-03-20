package com.example.xuchichi.mytest.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.xuchichi.mytest.utils.Constant;

/**
 * Created by xuchichi on 2018/3/21.
 * 提供创建更新数据库的方法（回调函数）
 * 提供获取数据库对象
 */

public class MySqliteHelper extends SQLiteOpenHelper {
    /**
     *
     * @param context 上下文对象
     * @param name 数据库名称
     * @param factory
     * @param version 当前版本 >=1
     */
    public MySqliteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public MySqliteHelper(Context context){
        super(context, Constant.DATABASE_NAME,null,Constant.DATABASE_VERSION);
    }

    /**
     * 数据库创建时回调的函数，db数据库对象
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    /**
     * 数据库有版本更新时回调
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * 数据库打开
     * @param db
     */
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }
}
