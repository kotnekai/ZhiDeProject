package com.zhide.app.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.zhide.app.R;
import com.zhide.app.common.ApplicationHolder;
import com.zhide.app.common.ThreadPoolManager;
import com.zhide.app.delegate.IConfirmClickListener;
import com.zhide.app.delegate.IGetAliPayResult;
import com.zhide.app.delegate.SpinerOnItemClickListener;
import com.zhide.app.eventBus.RechargeInfoEvent;
import com.zhide.app.eventBus.PayOrderEvent;
import com.zhide.app.logic.ChargeManager;
import com.zhide.app.model.AliPayParamModel;
import com.zhide.app.model.ReChargeModel;
import com.zhide.app.model.WXPayParamModel;
import com.zhide.app.utils.DialogUtils;
import com.zhide.app.utils.EmptyUtil;
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
    TextView tvChargeOther;
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

    /**
     * 后台返回支付信息，回调到这里
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWxChatEvent(PayOrderEvent event) {
        if (event.isWeChatPay()) {
            WXPayParamModel wxPayParamModel = event.getWxPayParamModel();
            if (wxPayParamModel == null) {
                ToastUtil.showShort(getString(R.string.get_net_data_error));
                return;
            }
            sendWxPayRequest(wxPayParamModel);
        } else {
            AliPayParamModel aliPayParamModel = event.getAliPayParamModel();
            if (aliPayParamModel == null) {
                ToastUtil.showShort(getString(R.string.get_net_data_error));
                return;
            }
            aliPayRequest(aliPayParamModel);
        }

    }


    public static final int aliPayType = 1;
    public static final int wxPayType = 2;
    public float selectAmount = 30;

    @OnClick({R.id.tvCharge30, R.id.tvCharge40, R.id.tvCharge50, R.id.tvCharge80, R.id.tvCharge100, R.id.tvChargeOther, R.id.tvReCharge})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvCharge30:
                updateTvState(tvCharge30);
                selectAmount = 30;
                break;
            case R.id.tvCharge40:
                updateTvState(tvCharge40);
                selectAmount = 40;
                break;
            case R.id.tvCharge50:
                updateTvState(tvCharge50);
                selectAmount = 50;

                break;
            case R.id.tvCharge80:
                updateTvState(tvCharge80);
                selectAmount = 80;

                break;
            case R.id.tvCharge100:
                updateTvState(tvCharge100);
                selectAmount = 100;

                break;
            case R.id.tvChargeOther:
                updateTvState(tvChargeOther);
                DialogUtils.showTipsDialog(this, getString(R.string.charge_other_tip), true, new IConfirmClickListener() {
                    @Override
                    public void confirmClick(String remarks) {
                        if (EmptyUtil.isEmpty(remarks)) {
                            tvChargeOther.setText("其他");
                        } else {
                            selectAmount = Float.parseFloat(remarks);
                            tvChargeOther.setText(remarks + "元");
                        }
                    }
                });
                break;
            case R.id.tvReCharge:
                DialogUtils.showBottomSelectTypePop(this, new SpinerOnItemClickListener() {
                    @Override
                    public void onItemClick(int position, int id) {
                        getPayParams(id);
                    }
                });
                break;
        }
    }

    /**
     * 请求服务端进行支付
     * @param type
     */
    private void getPayParams(int type) {
        if (type == -1 || type == aliPayType) {
            ChargeManager.getInstance().getAliPayParams(selectAmount);
        } else if (type == wxPayType) {
            ChargeManager.getInstance().getWeChatPayParams(selectAmount);
        }
    }

    /**
     * 调起支付宝支付
     * @param aliPayParamModel
     */
    private void aliPayRequest(final AliPayParamModel aliPayParamModel) {
        final String orderInfo = aliPayParamModel.getOrderInfo();
        if (EmptyUtil.isEmpty(orderInfo)) {
            return;
        }
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
    private void sendWxPayRequest(WXPayParamModel paramModel) {

        PayReq request = new PayReq();
        request.appId = paramModel.getAppId();
        request.partnerId = paramModel.getPartnerId();
        request.prepayId = paramModel.getPrepayId();
        request.packageValue = paramModel.getPackageValue();
        request.nonceStr = paramModel.getNoncestr();
        request.timeStamp = paramModel.getTimestamp();
        request.sign = paramModel.getSign();
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
