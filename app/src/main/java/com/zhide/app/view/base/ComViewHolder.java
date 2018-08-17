package com.zhide.app.view.base;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;

/**
 * Created by hasee on 2018/3/17.
 */

public class ComViewHolder {
    private View mConvertView;
    /**
     * 保存所有itemview的集合
     */
    private SparseArray<View> mViews;

    private ComViewHolder(Context context, int layoutId) {
        mConvertView = View.inflate(context, layoutId, null);
        mConvertView.setTag(this);
        mViews = new SparseArray<>();
    }

    public static ComViewHolder newsInstance(View convertView, Context context, int layoutId) {
        if (convertView == null) {
            return new ComViewHolder(context, layoutId);

        } else {
            return (ComViewHolder) convertView.getTag();
        }
    }

    /**
     * 获取根view
     * @return
     */
    public View getConverView()
    {
        return mConvertView;
    }

    /**
     * 获取节点view
     * @param id
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T getItemView(int id)
    {
        View view =  mViews.get(id);
        if (view == null)
        {
            view = mConvertView.findViewById(id);
            mViews.append(id, view);
        }

        return (T) view;
    }

}
