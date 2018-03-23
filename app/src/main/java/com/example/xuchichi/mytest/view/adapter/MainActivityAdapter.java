package com.example.xuchichi.mytest.view.adapter;

import android.content.Context;
import android.widget.TextView;

import com.example.xuchichi.mytest.R;

import java.util.List;

/**
 * Created by xuchichi on 2018/3/23.
 */

public class MainActivityAdapter extends BaseRecycleViewAdapter<String>{

    public MainActivityAdapter(List<String> mlist, Context context) {
        super(mlist, R.layout.item_main_recycle, context);
    }

    @Override
    public void setData(BaseViewHolder viewHolder, String item) {
        TextView tv= (TextView) viewHolder.getView(R.id.tv);
        tv.setText(item);
    }

}
