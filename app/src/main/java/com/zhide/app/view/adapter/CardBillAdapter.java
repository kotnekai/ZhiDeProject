package com.zhide.app.view.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhide.app.R;
import com.zhide.app.model.CardBillModel;
import com.zhide.app.model.MyBillModel;
import com.zhide.app.utils.ResourceUtils;

import java.util.List;

/**
 * Create by Admin on 2018/8/22
 */
public class CardBillAdapter extends RecyclerView.Adapter<CardBillAdapter.MyViewHolder> {

    private Context context;
    private List<CardBillModel.DataModel> myBillList;
    private LayoutInflater inflater;

    public CardBillAdapter(Context context, List<CardBillModel.DataModel> myBillList) {
        this.context = context;
        this.myBillList = myBillList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.card_bill_item_view,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        CardBillModel.DataModel dataModel = myBillList.get(position);
        if (dataModel == null) {
            return;
        }
        holder.tvTransTime.setText(dataModel.getUSCP_CreateTime());
        holder.tvTransType.setText(dataModel.getUSCP_Type());
        holder.tvTransAmount.setText(dataModel.getUSCP_Money());
        holder.tvRemainAmount.setText(dataModel.getUSCP_Money());

    }

    @Override
    public int getItemCount() {
        return myBillList.size();
    }

    protected class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTransTime;
        TextView tvTransType;
        TextView tvTransAmount;
        TextView tvRemainAmount;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvTransTime = itemView.findViewById(R.id.tvTransTime);
            tvTransType = itemView.findViewById(R.id.tvTransType);
            tvTransAmount = itemView.findViewById(R.id.tvTransAmount);
            tvRemainAmount = itemView.findViewById(R.id.tvRemainAmount);
        }

    }
}
