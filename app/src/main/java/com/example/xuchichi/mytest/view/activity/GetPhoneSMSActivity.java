package com.example.xuchichi.mytest.view.activity;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.xuchichi.mytest.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GetPhoneSMSActivity extends AppCompatActivity {

    @BindView(R.id.btnQuery)
    Button btnQuery;
    @BindView(R.id.btnCopy)
    Button btnCopy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_phone_sms);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btnQuery, R.id.btnCopy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnQuery:
                /**
                 * 查询短信数据库内容，然后进行备份
                 */
                Uri uri = Uri.parse("content://sms");//content://sms查询所有，inbox收件箱
                Cursor cursor = getContentResolver().query(uri, new String[]{"address", "date", "body"}, null, null, null, null);
                if (cursor != null && cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
                        String address = cursor.getString(cursor.getColumnIndex("address"));
                        String date = cursor.getString(cursor.getColumnIndex("date"));
                        String body = cursor.getString(cursor.getColumnIndex("body"));
                        System.out.println("address:"+address+"---date:"+date+"---body:"+body);
                    }
                }
                break;
            case R.id.btnCopy:
                break;

        }
    }
}
