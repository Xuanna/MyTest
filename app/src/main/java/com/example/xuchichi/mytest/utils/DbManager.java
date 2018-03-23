package com.example.xuchichi.mytest.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.example.xuchichi.mytest.db.MySqliteHelper;
import com.example.xuchichi.mytest.model.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuchichi on 2018/3/21.
 * 数据库工具类
 */

public class DbManager {
    public static MySqliteHelper sqliteHelper;

    public static MySqliteHelper getInstance(Context context) {
        if (sqliteHelper == null) {
            sqliteHelper = new MySqliteHelper(context);
        }
        return sqliteHelper;

    }

    public static void execSql(SQLiteDatabase db, String sql) {
        if (db != null && !TextUtils.isEmpty(sql)) {
            db.execSQL(sql);
        }

    }

    /**
     * @param db
     * @param sql
     * @param selectionArgs 查询条件的占位符
     * @return
     */
    public static Cursor selectDataBySql(SQLiteDatabase db, String sql, String[] selectionArgs) {
        Cursor cursor = null;
        if (db != null && !TextUtils.isEmpty(sql)) {
            cursor = db.rawQuery(sql, selectionArgs);
        }
        return cursor;

    }

    /**
     * Cursor转换成list集合
     *
     * @param cursor
     * @return
     */
    public static List<Person> cursorList(Cursor cursor) {
        List<Person> list = new ArrayList<>();
        while (cursor.moveToNext()) {//true表示还有数据 false表示读取完毕
//            cursor.getColumnIndex("id")根据字段名称获取字段的下标
//            cursor.getInt 根据下标获取对应的值
            int id = cursor.getInt(cursor.getColumnIndex(Constant.ID));
            String name = cursor.getString(cursor.getColumnIndex(Constant.NAME));
            int age = cursor.getInt(cursor.getColumnIndex(Constant.AGE));
            Person person = new Person(id, name, age);
            list.add(person);
        }
        return list;
    }
}
