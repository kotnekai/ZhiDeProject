package com.zhide.app.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.zhide.app.R;
import com.zhide.app.common.ApplicationHolder;
import com.zhide.app.delegate.IGetAliPayResult;
import com.zhide.app.delegate.SpinerOnItemClickListener;
import com.zhide.app.eventBus.RechargeInfoEvent;
import com.zhide.app.model.ReChargeModel;
import com.zhide.app.model.SpinnerSelectModel;
import com.zhide.app.utils.DialogUtils;
import com.zhide.app.utils.ToastUtil;
import com.zhide.app.utils.UIUtils;
import com.zhide.app.view.base.BaseActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class RechargeActivity extends BaseActivity {

    @BindView(R.id.tvTotalBalance)
    TextView tvTotalBalance;
    @BindView(R.id.tvCashBalance)
    TextView tvCashBalance;
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
    EditText tvChargeOther;
    @BindView(R.id.tvIntroActContent)
    TextView tvIntroActContent;
    @BindView(R.id.tvIntroContent)
    TextView tvIntroContent;
    @BindView(R.id.tvReCharge)
    TextView tvReCharge;
    private IGetAliPayResult alipayResult;

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
        alipayResult = new IGetAliPayResult() {
            @Override
            public void getResult(Map<String, String> result) {
                Log.d("xyc", "getResult: result=" + result);
            }
        };
    }

    public static Intent makeIntent(Context context) {
        Intent intent = new Intent(context, RechargeActivity.class);
        return intent;
    }

    /**
     * 第一次进来，，更新界面数据
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReChargeEvent(RechargeInfoEvent event) {
        ReChargeModel chargeModel = event.getChargeModel();
        if (chargeModel == null) {
            ToastUtil.showShort(getString(R.string.get_net_data_error));
            return;
        }
        updateUI(chargeModel);
    }

    /**
     * 更新界面数据
     *
     * @param chargeModel
     */
    private void updateUI(ReChargeModel chargeModel) {
        tvTotalBalance.setText(String.valueOf(chargeModel.getTotalBalance()) + "元");
        tvCashBalance.setText(String.valueOf(chargeModel.getCashBalance()) + "元");
        tvGiftBalance.setText(String.valueOf(chargeModel.getGiftBalance()) + "元");
        tvIntroActContent.setText(chargeModel.getChargeActTitle());
        tvIntroContent.setText(chargeModel.getChargeTitle());
    }

    public static final int aliPayType = 1;
    public static final int wxPayType = 2;

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
                DialogUtils.showBottomSelectTypePop(this, new SpinerOnItemClickListener() {
                    @Override
                    public void onItemClick(int position, int id) {
                        if (id == -1 || id == aliPayType) {
                            aliPayRequest();
                        } else if (id == wxPayType) {
                            sendWxPayRequest();
                        }
                    }
                });
                break;
        }
    }

    private void aliPayRequest() {
        //订单信息
        final String orderInfo = "";
        //异步处理
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                //新建任务
                PayTask alipay = new PayTask(RechargeActivity.this);
                String version = alipay.getVersion();
                //获取支付结果
                Map<String, String> result = alipay.payV2(orderInfo, true);
                alipayResult.getResult(result);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * 微信支付
     */
    private void sendWxPayRequest() {

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
