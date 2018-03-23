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
        return new BaseViewHolder(view, onItemClickListener);
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

    /**
     * 添加数据
     *
     * @param list
     */
    public void addData(List<T> list) {
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                mlist.add(list.get(i));
            }
            notifyItemRangeInserted(0, list.size());
        }

    }

    /**
     * 添加更多
     *
     * @param list
     */
    public void addMore(List<T> list) {
        int begin = mlist.size();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                mlist.add(list.get(i));
                notifyItemInserted(i + begin);
            }

        }
    }

    /**
     * 刷新数据
     */
    public void refreshData(List<T> list) {
        if (list != null && list.size() > 0) {
            clearData();
            for (int i = 0; i < list.size(); i++) {
                mlist.add(list.get(i));
                notifyItemInserted(i);
            }

        }
    }

    /**
     * 清除数据
     */
    public void clearData() {
        mlist.clear();
        notifyItemRangeRemoved(0, mlist.size());
    }


    public OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClickListener(View v, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;

    }


}
