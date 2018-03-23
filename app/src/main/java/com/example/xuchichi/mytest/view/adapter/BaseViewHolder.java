package com.example.xuchichi.mytest.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

/**
 * Created by xuchichi on 2018/3/23.
 */

public class BaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

   private SparseArray<View> views;
    private BaseRecycleViewAdapter.OnItemClickListener onItemClickListener;


    public BaseViewHolder(View itemView,BaseRecycleViewAdapter.OnItemClickListener onItemClickListener) {
        super(itemView);
        this.views = new SparseArray<>();
        itemView.setOnClickListener(this);
        this.onItemClickListener=onItemClickListener;
    }

    @Override
    public void onClick(View v) {
        if (onItemClickListener!=null){
            onItemClickListener.onItemClickListener(v,getLayoutPosition());
        }
    }

    public View getView(int id){
        return retrieveView(id);
    }
    protected <T extends View> T retrieveView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;

    }
}
