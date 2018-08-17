package com.zhide.app.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.tencent.mm.opensdk.modelpay.PayReq;
import com.zhide.app.R;
import com.zhide.app.common.ApplicationHolder;
import com.zhide.app.utils.UIUtils;
import com.zhide.app.view.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class RechargeActivity extends BaseActivity {

    @BindView(R.id.tvTotalBalance)
    TextView tvTotalBalance;
    @BindView(R.id.tvCaseBalance)
    TextView tvCaseBalance;
    @BindView(R.id.tvGiftBalance)
    TextView tvGiftBalance;
    @BindView(R.id.tvCharge30)
    TextView tvCharge30;
    @BindView(R.id.tvCharge40)
    TextView tvCharge40;
    @BindView(R.id.tvCharge50)
    TextView tvCharge50;
    @BindView(R.id.tvCharge80)
    TextView tvCharge80;
    @BindView(R.id.tvCharge100)
    TextView tvCharge100;
    @BindView(R.id.tvChargeOther)
    TextView tvChargeOther;
    @BindView(R.id.tvIntroActContent)
    TextView tvIntroActContent;
    @BindView(R.id.tvReCharge)
    TextView tvReCharge;

    @Override
    protected int getCenterView() {
        return R.layout.activity_recharge;
    }

    @Override
    protected void initHeader() {
        setHeaderTitle(UIUtils.getValueString(R.string.mine_charge));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private List<TextView> selectTvList;

    private void initView() {
        selectTvList = new ArrayList<>();
        selectTvList.add(tvCharge30);
        selectTvList.add(tvCharge40);
        selectTvList.add(tvCharge50);
        selectTvList.add(tvCharge80);
        selectTvList.add(tvCharge100);
        selectTvList.add(tvChargeOther);
        updateTvState(tvCharge30);
    }

    public static Intent makeIntent(Context context) {
        Intent intent = new Intent(context, RechargeActivity.class);
        return intent;
    }

    @OnClick({R.id.tvCharge30, R.id.tvCharge40, R.id.tvCharge50, R.id.tvCharge80, R.id.tvCharge100, R.id.tvChargeOther, R.id.tvReCharge})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvCharge30:
                updateTvState(tvCharge30);
                break;
            case R.id.tvCharge40:
                updateTvState(tvCharge40);
                break;
            case R.id.tvCharge50:
                updateTvState(tvCharge50);

                break;
            case R.id.tvCharge80:
                updateTvState(tvCharge80);

                break;
            case R.id.tvCharge100:
                updateTvState(tvCharge100);

                break;
            case R.id.tvChargeOther:
                updateTvState(tvChargeOther);

                break;
            case R.id.tvReCharge:
                sendPayRequest();
                break;

        }

    }
    private void sendPayRequest() {

        PayReq request = new PayReq();
        request.appId = "wxd930ea5d5a258f4f";
        request.partnerId = "1900000109";
        request.prepayId = "1101000000140415649af9fc314aa427";
        request.packageValue = "Sign=WXPay";
        request.nonceStr = "1101000000140429eb40476f8896f4c9";
        request.timeStamp = "1398746574";
        request.sign = "8cb66835007a51af5323b29885c2392c";
        ApplicationHolder.getInstance().getMsgApi().sendReq(request);
    }

    private void updateTvState(TextView tv) {
        for (int i = 0; i < selectTvList.size(); i++) {
            if (tv == selectTvList.get(i)) {
                tv.setSelected(true);
            } else {
                selectTvList.get(i).setSelected(false);
            }
        }
    }
}
