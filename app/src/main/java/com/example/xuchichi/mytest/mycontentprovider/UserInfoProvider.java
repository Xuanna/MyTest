package com.example.xuchichi.mytest.mycontentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.xuchichi.mytest.sqlite.MyOpenHelper;

public class UserInfoProvider extends ContentProvider {
    /**
     * url
     *
     *
     * uri
     */

    // Creates a UriMatcher object.
    /**
     * 1定义路径匹配器
     */
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static int QUERYSUCCESS = 1;
    static int INSERTUCCESS = 2;
    static int UPDATESUCCESS = 3;
    static int DELETESUCCESS = 4;

    /**
     * 2定义静态代码块，添加匹配规则
     */
    static {
        /*
         * authority:要和清单文件中定义的要一样
         * path：随便定义
         * code：随意写
         * uri就变成了com.test.infodabase/query
         */
        uriMatcher.addURI("com.test.infodabase", "query", QUERYSUCCESS);
        uriMatcher.addURI("com.test.infodabase", "delete", DELETESUCCESS);
        uriMatcher.addURI("com.test.infodabase", "update", UPDATESUCCESS);
        uriMatcher.addURI("com.test.infodabase", "insert", INSERTUCCESS);
    }

    MyOpenHelper myOpenHelper;

    @Override
    public boolean onCreate() {
        myOpenHelper = new MyOpenHelper(getContext());

        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        int code = uriMatcher.match(uri);
        if (code == QUERYSUCCESS) {
            //路径匹配成功 ，把query方法实现，对数据库进行查询
            //获得数据库对象
            SQLiteDatabase db = myOpenHelper.getWritableDatabase();
            Cursor cursor = db.query("info", projection, selection, selectionArgs, null, null, sortOrder);
            getContext().getContentResolver().notifyChange(uri,null);
            return cursor;

        } else {
            throw new IllegalArgumentException("您的路径不匹配，请检查路径");

        }

    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        int code = uriMatcher.match(uri);
        Log.e("code====",code+"");
        if (code == INSERTUCCESS) {
            SQLiteDatabase db = myOpenHelper.getWritableDatabase();
            long insertPos = db.insert("info", null, values);
            Uri uri2 = Uri.parse("com.test.infodabase/" + insertPos);

            getContext().getContentResolver().notifyChange(uri,null);
            return uri2;

        }else {
            throw new IllegalArgumentException("您的路径不匹配，请检查路径");

        }

    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int code = uriMatcher.match(uri);
        Log.e("code====",code+"");
        if (code == DELETESUCCESS) {
            SQLiteDatabase db = myOpenHelper.getWritableDatabase();
            int deletePos = db.delete("info", selection, selectionArgs);
            getContext().getContentResolver().notifyChange(uri,null);
            return deletePos;

        }
        else {
            throw new IllegalArgumentException("您的路径不匹配，请检查路径");

        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {

        int code = uriMatcher.match(uri);
        Log.e("code====",code+"");
        if (code == UPDATESUCCESS) {
            SQLiteDatabase db = myOpenHelper.getWritableDatabase();
            int updatePos = db.update("info", values, selection, selectionArgs);
            getContext().getContentResolver().notifyChange(uri,null);
            return updatePos;

        }
        else {
            throw new IllegalArgumentException("您的路径不匹配，请检查路径");

        }
    }
}
