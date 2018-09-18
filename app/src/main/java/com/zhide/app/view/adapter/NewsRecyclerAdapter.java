package com.zhide.app.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhide.app.R;
import com.zhide.app.model.NewsModel;
import com.zhide.app.view.base.WebViewActivity;

import java.util.List;


/**
 * 用户成长计划时间轴 填充类
 * Created by tim.hu on 2016/10/27.
 */
public class NewsRecyclerAdapter extends RecyclerView.Adapter<NewsRecyclerAdapter.MyViewHolder> {

    private View mHeaderView;
    private List<NewsModel.NewsData> mDatas;
    private Context mContext;
    private LayoutInflater inflater;

    public NewsRecyclerAdapter(Context context, List<NewsModel.NewsData> datas) {
        this.mContext = context;
        this.mDatas = datas;
        inflater = LayoutInflater.from(mContext);
    }


    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return mHeaderView == null ? position : position - 1;
    }

    @Override
    public int getItemCount() {
        return mHeaderView == null ? mDatas.size() : mDatas.size() + 1;
    }

    //填充onCreateViewHolder方法返回的holder中的控件
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final NewsModel.NewsData newsData = mDatas.get(getRealPosition(holder));
        holder.tvNewsTitle.setText(newsData.getNI_Title());
        holder.tvNewsDate.setText(newsData.getNI_UpdateTime());
        holder.tvNewsDesc.setText(newsData.getNI_Summary());
        holder.llItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ni_url = newsData.getNI_Url();
                if (ni_url == null) {
                    return;
                }
                mContext.startActivity(WebViewActivity.makeIntent(mContext, "",ni_url ));
            }
        });

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.item_news, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvNewsTitle, tvNewsDate, tvNewsDesc;
        LinearLayout llItemLayout;

        public MyViewHolder(View view) {
            super(view);
            if (itemView == mHeaderView) {
                return;
            }
            tvNewsTitle = (TextView) view.findViewById(R.id.tvNewsTitle);
            tvNewsDate = (TextView) view.findViewById(R.id.tvNewsDate);
            tvNewsDesc = (TextView) view.findViewById(R.id.tvNewsDesc);
            llItemLayout = (LinearLayout) view.findViewById(R.id.llItemLayout);
        }

    }
}