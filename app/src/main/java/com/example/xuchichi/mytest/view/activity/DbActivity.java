package com.example.xuchichi.mytest.view.activity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;

import com.example.xuchichi.mytest.R;
import com.example.xuchichi.mytest.db.MySqliteHelper;
import com.example.xuchichi.mytest.model.Person;
import com.example.xuchichi.mytest.utils.Constant;
import com.example.xuchichi.mytest.utils.DbManager;

import java.sql.Array;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DbActivity extends AppCompatActivity {

    @BindView(R.id.btnInsert)
    Button btnInsert;
    @BindView(R.id.btnDelete)
    Button btnDelete;
    @BindView(R.id.btnUpdate)
    Button btnUpdate;
    @BindView(R.id.btnQuery)
    Button btnQuery;

    MySqliteHelper sqliteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db);
        ButterKnife.bind(this);
        sqliteHelper = DbManager.getInstance(this);
    }
    public void onCreadDb() {
        //getReadableDatabase getWritableDatabase 创建或打开数据库
        //如果数据库存在直接打开，不存在则创建

        MySqliteHelper sqliteHelper = DbManager.getInstance(this);
        SQLiteDatabase database = sqliteHelper.getWritableDatabase();

    }
    @OnClick({R.id.btnInsert, R.id.btnDelete, R.id.btnUpdate, R.id.btnQuery})
    public void onViewClicked(View view) {
        SQLiteDatabase db;
        switch (view.getId()) {
            case R.id.btnLotInsert://批量操作 每执行一次语句相当于开启／关闭一个事物 批量操作使用事物
                /**提交效率，保持数据一致性
                 *1.开启事物
                 * 2。提交事物
                 * 3。关闭事物
                 */
               db=sqliteHelper.getWritableDatabase();
               db.beginTransaction();
                for (int i=4;i<100;i++){
                    String sql="insert into "+Constant.TABLE_NAME+" values("+i+",'小木'"+i+",18)";
                    db.execSQL(sql);
                  }
                db.setTransactionSuccessful();
                db.endTransaction();
                  db.close();
                break;
            case R.id.btnInsert:
                db = sqliteHelper.getWritableDatabase();
                DbManager.execSql(db, "insert into " + Constant.TABLE_NAME + " values(3,'lily',18)");
                db.close();
                break;
            case R.id.btnDelete:
                break;
            case R.id.btnUpdate:
                break;
            case R.id.btnQuery:
                db = sqliteHelper.getReadableDatabase();
                //SQL语句查询
//                Cursor cursor = DbManager.selectDataBySql(db, "select * from " + Constant.TABLE_NAME, null);
//                List<Person> list = DbManager.cursorList(cursor);
//                for (Person p : list) {
//                    Log.e("p", p.toString());
//                }
//                APi查询
//          String table, 查询的表名
//          String[] columns,查询的列名 null表示查询所有
//          String selection,查询条件 where
//          String[] selectionArgs,表示查询条件占位符的取值
//          String groupBy,分组条件
//          String having,筛选条件
//          String orderBy 顺序排序 desc 倒叙
//
                Cursor cursor = db.query(Constant.TABLE_NAME, null, Constant.ID + ">?", new String[]{"1"}, null, null, Constant.ID + " desc");
                List<Person> list = DbManager.cursorList(cursor);
                for (Person p : list) {
                    Log.e("p", p.toString());
                }
//                Context context,
//                  int layout, 适配器item的layout
//                  Cursor c,cursor数据源
//                  String[] from,表示cursor表中数据表中的数组
//                  int[] to 表示字段对应的资源id
//                int flags 设置适配器的标志 SimpleCursorAdapter list.setAdapter() 主键必须是_id;
//                通过cursor直接展示数据到列表
                SimpleCursorAdapter simpleAdapter=new SimpleCursorAdapter(this,R.layout.activity_a,cursor,new String[]{Constant.ID,Constant.NAME,Constant.AGE}
                ,new int[]{},SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
                db.close();



                break;
        }

    }

    /**
     *通过cursor直接展示数据到列表
     */
    public class MyCursorAdapter extends CursorAdapter{
        public MyCursorAdapter(Context context, Cursor c) {
            super(context, c);
        }

        /**
         * 表示创建的适配器中每个item对应的view对象
         * @param context
         * @param cursor 数据源对象
         * @param parent item的父布局
         * @return 每个item对应的view对象
         */
        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            return LayoutInflater.from(context).inflate(R.layout.activity_a,parent);
        }

        /**
         * 绑定数据
         * @param view
         * @param context
         * @param cursor
         */
        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            /**
             * 1.找id 2。赋值
             */
        }
    }
}
