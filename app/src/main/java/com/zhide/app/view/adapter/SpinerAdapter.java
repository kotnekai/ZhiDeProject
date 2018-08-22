package com.zhide.app.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.zhide.app.R;
import com.zhide.app.model.SpinnerSelectModel;

import java.util.List;

/**
 * Created by hasee on 2017/12/30.
 */

public class SpinerAdapter extends BaseAdapter {
    private List<SpinnerSelectModel> mList;
    private Context mContext;
    private LayoutInflater inflater;

    public SpinerAdapter(Context context, List<SpinnerSelectModel> list) {
        mContext = context;
        mList = list;
        inflater = LayoutInflater.from(mContext);
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
        return mList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.spinner_layout, null);
            viewHolder.tvBdName = (TextView) convertView.findViewById(R.id.tvBdName);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        SpinnerSelectModel spinnerSelectModel = mList.get(position);
        if (spinnerSelectModel != null) {
            viewHolder.tvBdName.setText(spinnerSelectModel.getName());
        }
        return convertView;
    }

    class ViewHolder {
        TextView tvBdName;
    }
}
