package com.zhide.app.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhide.app.R;
import com.zhide.app.common.CommonParams;
import com.zhide.app.eventBus.WaterPreBillEvent;
import com.zhide.app.view.base.BaseActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

/**
 * Create by Admin on 2018/09/25
 *
 * @author Admin
 */
public class ShowerCompletedActivity extends BaseActivity implements View.OnClickListener {

    Context mContext;
    @BindView(R.id.ivShower)
    ImageView ivShower;
    @BindView(R.id.tvCompletedTime)
    TextView tvCompletedTime;
    @BindView(R.id.tvPerSave)
    TextView tvPerSave;
    @BindView(R.id.tvPayMoney)
    TextView tvPayMoney;
    @BindView(R.id.tvReturnMoney)
    TextView tvReturnMoney;
    @BindView(R.id.tvBalance)
    TextView tvBalance;
    @BindView(R.id.tvReturnBack)
    TextView tvReturnBack;

    @Override
    protected int getCenterView() {
        return R.layout.activity_shower_completed;
    }

    @Override
    protected SmartRefreshLayout getRefreshView() {
        return null;
    }

    @Override
    protected void initHeader() {
        setHeaderTitle(getString(R.string.consume_title));
    }


    public static Intent makeIntent(Context context, long time, float deducting, float payMoney, float returnMoney, float mainBalance) {
        Intent intent = new Intent(context, QRCodeActivity.class);
        intent.putExtra(CommonParams.COMPLETED_TIME, time);
        intent.putExtra(CommonParams.SI_DEDUCTING, deducting);
        intent.putExtra(CommonParams.PAY_MONEY, payMoney);
        intent.putExtra(CommonParams.RETURN_MONEY, returnMoney);
        intent.putExtra(CommonParams.USI_MAINBALANCE, mainBalance);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = ShowerCompletedActivity.this;
        initData();
    }

    /**
     * 加载数据
     */
    private void initData() {
        Intent intent = getIntent();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(WaterPreBillEvent event) {
        //服务端返回学生用水预扣费接口，可以执行下发费率
        if (event.getWaterPreBillModel() != null) {
        } else {

        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
