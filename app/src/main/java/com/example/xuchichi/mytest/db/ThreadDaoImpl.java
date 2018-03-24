package com.example.xuchichi.mytest.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.xuchichi.mytest.model.ThreadInfo;
import com.example.xuchichi.mytest.utils.DbManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuchichi on 2018/3/24.
 * 数据访问接口实现
 */

public class ThreadDaoImpl implements ThreadDao {
    private Context context;
    private MySqliteHelper helper;

    public ThreadDaoImpl(Context context) {
        this.context = context;
        helper = DbManager.getInstance(context);
    }


    @Override
    public void insertThread(ThreadInfo info) {
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql="insert into threadInfo (thread_id,thread_url,thread_start,thread_end,thread_finish) values (?,?,?,?,?)";
        db.execSQL(sql, new Object[]{info.getId(), info.getUrl(), info.getStart(), info.getEnd(), info.getFinish()});
        db.close();

    }

    @Override
    public void deleteThread(String url, int threadId) {
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql="delete from threadInfo where thread_url=? and thread_id=?";
        db.execSQL(sql, new Object[]{url, threadId});
        db.close();
    }

    @Override
    public void updateThread(String url, int threadId, int finish) {
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql="update  threadInfo set thread_finish=? where  thread_url=? and thread_id=?";
        db.execSQL(sql, new Object[]{finish, url, threadId});
        db.close();
    }


    @Override
    public List<ThreadInfo> getThread(String url) {
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql="select * from threadInfo where thread_url=? ";
        Cursor cursor = db.rawQuery(sql, new String[]{url});
        List<ThreadInfo> list = new ArrayList<>();
        while (cursor.moveToNext()) {

            int threadId = cursor.getInt(cursor.getColumnIndex("thread_id"));
            String thread_url = cursor.getString(cursor.getColumnIndex("thread_url"));
            int thread_start = cursor.getInt(cursor.getColumnIndex("thread_start"));
            int thread_end = cursor.getInt(cursor.getColumnIndex("thread_end"));
            int thread_finish = cursor.getInt(cursor.getColumnIndex("thread_finish"));
            ThreadInfo info = new ThreadInfo(threadId,thread_url,thread_start,thread_end,thread_finish);
            list.add(info);


        }
        cursor.close();
        db.close();
        return list;
    }

    @Override
    public boolean isExist(String url, int threadId) {
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql="select * from threadInfo where thread_url = ? and thread_id = ? ";
        Cursor cursor = db.rawQuery(sql, new String[]{url,threadId+""});
       boolean isExist= cursor.moveToNext();
        db.close();
        return isExist;
    }
}
