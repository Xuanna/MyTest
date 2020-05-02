package com.example.xuchichi.mytest.view.activity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xuchichi.mytest.R;
import com.example.xuchichi.mytest.sqlite.MyOpenHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SqliteActivity extends AppCompatActivity {

    @BindView(R.id.btnAdd)
    Button btnAdd;
    @BindView(R.id.btnDelete)
    Button btnDelete;
    @BindView(R.id.btnUpdate)
    Button btnUpdate;
    @BindView(R.id.btnCheck)
    Button btnCheck;
    @BindView(R.id.tvContent)
    TextView tvContent;


    MyOpenHelper myOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);
        ButterKnife.bind(this);

        myOpenHelper = new MyOpenHelper(this);

        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
    }

    @OnClick({R.id.btnAdd, R.id.btnDelete, R.id.btnUpdate, R.id.btnCheck})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnAdd:
                SQLiteDatabase db = myOpenHelper.getWritableDatabase();
                ContentValues values=new ContentValues();
                values.put("name","里斯");
                values.put("phone","12345678");
                //the row ID of the newly inserted row, or -1 if an error occurred 返回新行的id
               long  insert=db.insert("info",null,values);
               if (insert>0){
                   Toast.makeText(this,"添加失败",Toast.LENGTH_SHORT).show();
               }else{
                   Toast.makeText(this,"添加成功",Toast.LENGTH_SHORT).show();
               }

//                    db.execSQL("insert into info (name,phone) values (?,?)", new Object[]{"张三", "1243567"});//占位符

                db.close();//数据库用完需要关闭，但是频繁的打开关闭效率会低
                break;
            case R.id.btnDelete:
                SQLiteDatabase db2 = myOpenHelper.getWritableDatabase();
                // 影响的行数，0表示失败
               int delete= db2.delete("info","name=?",new String[]{" 张三"});
                Toast.makeText(this,"删除了"+delete+"行",Toast.LENGTH_SHORT).show();

//                db2.execSQL("delete from info where name=?", new Object[]{"张三"});
                break;
            case R.id.btnUpdate:
                SQLiteDatabase db3 = myOpenHelper.getWritableDatabase();
                ContentValues values3=new ContentValues();
                values3.put("phone","110110110");
                // 影响的行数
              int update=  db3.update("info",values3,"name=?",new String[]{"张三"});
                Toast.makeText(this,"更新了"+update+"行",Toast.LENGTH_SHORT).show();
//                db3.execSQL("update  info set phone=? where name=?", new Object[]{"110110110", "张三"});
                break;
            case R.id.btnCheck:
                SQLiteDatabase db4 = myOpenHelper.getWritableDatabase();
//                db4.execSQL("");//返回void查不出数据
                //null 表示查询所有，selection： 查询的条件 args：占位的值。groupBy:分组 having：过滤条件 orderby：排序
//                Cursor cursor =  db4.query("info",new String[]{"phone"},"name=?",new String[]{"张三"},null,null,null);

//                Cursor cursor = db4.rawQuery("select * from info", null);
                Cursor cursor = db4.query("info",null,null,null,null,null,null);


                if (cursor != null && cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
                        int id = cursor.getInt(cursor.getColumnIndex("_id"));
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String phone = cursor.getString(cursor.getColumnIndex("phone"));
//                        tvContent.setText("内容："+ phone);
                        System.out.println("内容：" + id + "----" + name + "----" + phone);
//                        System.out.println("内容："  + phone);
                    }


                } else {
                    tvContent.setText("内容为空");
                }
                break;
        }
    }
}
