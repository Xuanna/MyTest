package com.example.xuchichi.mytest.view.adapter;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.xuchichi.mytest.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xuchichi on 2018/4/18.
 */

public class MyBluetoothAdapter extends BaseAdapter {
    List<BluetoothDevice> list;
    Context context;


    public MyBluetoothAdapter(List<BluetoothDevice> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_bluetooth, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        BluetoothDevice device = list.get(position);
        holder.tvName.setText(device.getName());
        holder.tvValues.setText(device.getAddress());
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvValues)
        TextView tvValues;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
