package com.zhide.app.view.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhide.app.R;
import com.zhide.app.delegate.ISelectClickListener;
import com.zhide.app.model.SelectModel;

/**
 * Created by hasee on 2018/7/2.
 */

public class SelectItemView extends LinearLayout {
    private Context context;
    private TextView tvSelectItem;
    private SelectModel selectModel;
    private View containView;

    public SelectItemView(Context context) {
        super(context);
        this.context = context;
    }

    public SelectItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initView();
    }

    public SelectModel getSelectModel() {
        return selectModel;
    }

    public void setSelectModel(SelectModel selectModel) {
        if (selectModel == null) {
            return;
        }
        this.selectModel = selectModel;
        if (selectModel.isCheck()) {
            tvSelectItem.setSelected(true);
        } else {
            tvSelectItem.setSelected(false);
        }
        if (selectModel.getName().equals("其他")) {
            tvSelectItem.setText(selectModel.getName());
        } else {
            tvSelectItem.setText(selectModel.getName() + "元");
        }
    }

    public void setOnSelectItemListener(final ISelectClickListener selectClick) {
        tvSelectItem.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                selectModel.setCheck(!selectModel.isCheck());
                if (selectModel.isCheck()) {
                    tvSelectItem.setSelected(true);
                } else {
                    tvSelectItem.setSelected(false);
                }

                selectClick.selectIt(tvSelectItem, selectModel.getName(), selectModel.getId());
            }
        });
    }

    private void initView() {
        tvSelectItem = (TextView) findViewById(R.id.tvSelectItem);
    }

    public void setItemViewBgDrawable(int drawableId) {
        tvSelectItem.setBackground(context.getResources().getDrawable(drawableId));
    }

    public TextView getTvSelectItem() {
        return tvSelectItem;
    }
}
