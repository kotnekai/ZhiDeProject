package com.zhide.app.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhide.app.R;
import com.zhide.app.model.GuideModel;
import com.zhide.app.view.base.WebViewActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Create by Admin on 2018/9/18
 */
public class GuideAdapter extends RecyclerView.Adapter<GuideAdapter.MyViewHolder> {

    private Context mContext;
    private List<GuideModel.GuideData> dataList;
    private LayoutInflater inflater;

    public GuideAdapter(Context context, List<GuideModel.GuideData> dataList) {
        mContext = context;
        this.dataList = dataList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.guide_item_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final GuideModel.GuideData guideData = dataList.get(position);
        if (guideData == null) {
            return;
        }
        holder.tvGuideItem.setText(guideData.getNI_Title());

        holder.llGuideTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ni_url = guideData.getNI_Url();
                if (ni_url == null) {
                    return;
                }
                mContext.startActivity(WebViewActivity.makeIntent(mContext, "", ni_url));
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.llGuideTitle)
        LinearLayout llGuideTitle;

        @BindView(R.id.tvGuideItem)
        TextView tvGuideItem;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
        }

    }
}
