package com.zhide.app.view.base;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;


/**
 * Created by hasee on 2018/3/17.
 */

public abstract class ComBaseAdapter<T> extends BaseAdapter {

    private Context context;
    private List<T> mList;

    public ComBaseAdapter(List<T> datas) {
        mList = datas;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ComViewHolder holder = ComViewHolder
                .newsInstance(convertView, parent.getContext(), getLayoutId());

        setUI(holder, position, parent.getContext());

        return holder.getConverView();

    }

    protected abstract void setUI(ComViewHolder holder, int position, Context context);

    protected abstract int getLayoutId();

}
