package com.example.xuchichi.mytest.view.activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.xuchichi.mytest.R;
import com.example.xuchichi.mytest.db.BankOpenhelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 数据库事物案例
 */
public class SqlTransactionEgActivity extends AppCompatActivity {

    @BindView(R.id.btn)
    Button btn;

    BankOpenhelper bankOpenhelper;
    SQLiteDatabase db;
    @BindView(R.id.btnFind)
    Button btnFind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sql_transaction_eg);
        ButterKnife.bind(this);

        bankOpenhelper = new BankOpenhelper(this);

    }

    @OnClick({R.id.btn, R.id.btnFind})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn:
                db = bankOpenhelper.getReadableDatabase();//创建数据库
                // 　
                db.beginTransaction();
                try {

                    //........实现逻辑
                    db.execSQL("update user set amount=amount-100 where name=?",new Object[]{"张三"});
                    //int i=10/0;
                    db.execSQL("update user set amount=amount+100 where name=?",new Object[]{"李四"});

                    db.setTransactionSuccessful();//给当前事物一个成功标记，如果中间有错误，则会回滚，之前执行的就会取消
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),"服务器正忙",Toast.LENGTH_SHORT).show();
                }finally {
                    db.endTransaction();
                }
                break;
            case R.id.btnFind:
                Cursor cursor=db.query("user",null,null,null,null,null,null);
                if (cursor != null&cursor.getCount()>0){
                    while (cursor.moveToNext()){
                        String name=cursor.getString(cursor.getColumnIndex("name"));
                        Double amount=cursor.getDouble(cursor.getColumnIndex("amount"));
                        System.out.println("name="+name+"---amount="+amount);

                    }
                }
                cursor.close();

                break;
        }
    }

    /**
     * 点击按钮进行转账
     * 逻辑是转账需要同时成功同时失败，需要使用事物
     */

}
