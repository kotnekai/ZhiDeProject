package com.zhide.app.view.adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhide.app.R;
import com.zhide.app.model.MyBillModel;
import com.zhide.app.utils.ResourceUtils;
import com.zhide.app.utils.UIUtils;

import java.util.List;

/**
 * Create by Admin on 2018/8/22
 */
public class MyBillAdapter extends RecyclerView.Adapter<MyBillAdapter.MyViewHolder> {

    private Context context;
    private List<MyBillModel.BillData> myBillList;
    private LayoutInflater inflater;

    public MyBillAdapter(Context context, List<MyBillModel.BillData> myBillList) {
        this.context = context;
        this.myBillList = myBillList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.my_bill_item_view, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MyBillModel.BillData myBillModel = myBillList.get(position);
        if (myBillModel == null) {
            return;
        }
        holder.tvTypeContent.setText(myBillModel.getUSB_ChangeThing());
        holder.tvTimeData.setText(myBillModel.getUSB_CreateTime());
        if (myBillModel.getUSB_ChangeType().equals("支出")) {
            holder.tvTypeContent.setTextColor(ResourceUtils.getInstance().getColor(R.color.light_text_blue_color));
            holder.tvTransMoney.setTextColor(ResourceUtils.getInstance().getColor(R.color.normal_text_money_red_color));
            holder.tvTransMoney.setText("-" + myBillModel.getUSB_Money());
        } else {
            holder.tvTypeContent.setTextColor(ResourceUtils.getInstance().getColor(R.color.main_blue_color));
            holder.tvTransMoney.setTextColor(ResourceUtils.getInstance().getColor(R.color.normal_text_money_green_color));
            holder.tvTransMoney.setText("+" + myBillModel.getUSB_Money());
        }

    }

    @Override
    public int getItemCount() {
        return myBillList.size();
    }

    protected class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTypeContent;
        TextView tvTimeData;
        TextView tvTransMoney;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvTypeContent = itemView.findViewById(R.id.tvTypeContent);
            tvTimeData = itemView.findViewById(R.id.tvTimeData);
            tvTransMoney = itemView.findViewById(R.id.tvTransMoney);
        }

    }
}
