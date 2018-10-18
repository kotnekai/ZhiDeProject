package com.zhide.app.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhide.app.R;
import com.zhide.app.common.CommonParams;
import com.zhide.app.eventBus.WaterPreBillEvent;
import com.zhide.app.utils.DateUtils;
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

    long completeTime;
    float deducting,
            consumeMoney,
            returnMoney,
            balance;

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
        setLeftIconVisibility(View.GONE);
        setHeaderTitle(getString(R.string.consume_title));
    }


    public static Intent makeIntent(Context context, long time, float deducting, float payMoney, float returnMoney, float mainBalance) {
        Intent intent = new Intent(context, ShowerCompletedActivity.class);
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
        completeTime = intent.getLongExtra(CommonParams.COMPLETED_TIME, 0);
        deducting = intent.getFloatExtra(CommonParams.SI_DEDUCTING, 0);
        consumeMoney = intent.getFloatExtra(CommonParams.PAY_MONEY, 0);
        returnMoney = intent.getFloatExtra(CommonParams.RETURN_MONEY, 0);
        balance = intent.getFloatExtra(CommonParams.USI_MAINBALANCE, 0);


        tvCompletedTime.setText(DateUtils.getCompletedTime(String.valueOf(completeTime)));
        tvPerSave.setText(String.format(getString(R.string.shower_money_unit), deducting + ""));
        tvPayMoney.setText(String.format(getString(R.string.shower_money_unit), consumeMoney + ""));
        tvReturnMoney.setText(String.format(getString(R.string.shower_money_unit), returnMoney + ""));
        tvBalance.setText(String.format(getString(R.string.shower_money_unit), balance + ""));

        tvReturnBack.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvReturnBack:
                setResult(CommonParams.FINISH_CODE);
                finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);//继续执行父类其他点击事件
    }
}
