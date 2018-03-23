package com.example.xuchichi.mytest.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by xuchichi on 2018/3/23.
 */

public abstract class BaseRecycleViewAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {
    private List<T> mlist;
    private int layoutId;
    private Context context;
    private LayoutInflater inflater;

    public BaseRecycleViewAdapter(List<T> mlist, int layoutId, Context context) {
        this.mlist = mlist;
        this.layoutId = layoutId;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(layoutId, null);
        return new BaseViewHolder(view,onItemClickListener);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        T item = mlist.get(position);
        setData(holder, item);
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public abstract void setData(BaseViewHolder viewHolder, T item);

    public  OnItemClickListener onItemClickListener;

    public interface OnItemClickListener{
        void onItemClickListener(View v,int position);
    }

    public void setOnItemClickListener( OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;

    }


}
