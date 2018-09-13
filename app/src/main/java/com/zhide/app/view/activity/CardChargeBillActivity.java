package com.zhide.app.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhide.app.R;
import com.zhide.app.common.CommonParams;
import com.zhide.app.eventBus.CardBillEvent;
import com.zhide.app.logic.BillManager;
import com.zhide.app.model.CardBillModel;
import com.zhide.app.utils.PreferencesUtils;
import com.zhide.app.utils.ToastUtil;
import com.zhide.app.view.adapter.CardBillAdapter;
import com.zhide.app.view.base.BaseActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CardChargeBillActivity extends BaseActivity {

    @BindView(R.id.smartRefresh)
    SmartRefreshLayout smartRefresh;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private List<CardBillModel.DataModel> dataList = new ArrayList<>();
    private CardBillAdapter adapter;

    @Override
    protected int getCenterView() {
        return R.layout.activity_card_charge_bill;
    }

    @Override
    protected SmartRefreshLayout getRefreshView() {
        return null;
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, CardChargeBillActivity.class);
    }

    @Override
    protected void initHeader() {
        setHeaderTitle(getString(R.string.card_bill_title));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        mLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(mLayoutManager);
        adapter = new CardBillAdapter(this, dataList);
        recyclerView.setAdapter(adapter);
        loadData();
    }

    private void loadData() {
        String userId = PreferencesUtils.getString(CommonParams.LOGIN_USER_ID);
        BillManager.getInstance().getCardBillData(userId);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCardBillEvent(CardBillEvent event) {
        CardBillModel cardBillModel = event.getCardBillModel();
        if (cardBillModel == null) {
            return;
        }
        ToastUtil.showShort(cardBillModel.getMsg());
        if (cardBillModel.getCode() == 1) {
            updateUI(cardBillModel);
        }
    }

    private void updateUI(CardBillModel cardBillModel) {
        List<CardBillModel.DataModel> data = cardBillModel.getData();
        if (dataList.size() == 0 || data == null || data.size() == 0) {
            return;
        }
        dataList.clear();
        dataList.addAll(data);
        adapter.notifyDataSetChanged();
    }

}
