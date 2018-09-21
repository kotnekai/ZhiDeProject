package com.zhide.app.view.fragment;

import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.zhide.app.R;
import com.zhide.app.common.CommonParams;
import com.zhide.app.delegate.IConfirmClickListener;
import com.zhide.app.delegate.IGetAliPayResult;
import com.zhide.app.eventBus.PayOrderEvent;
import com.zhide.app.eventBus.RechargeInfoEvent;
import com.zhide.app.logic.PayManager;
import com.zhide.app.model.AliPayParamModel;
import com.zhide.app.model.ReChargeModel;
import com.zhide.app.model.WXPayParamModel;
import com.zhide.app.utils.DialogUtils;
import com.zhide.app.utils.EmptyUtil;
import com.zhide.app.utils.PreferencesUtils;
import com.zhide.app.utils.ResourceUtils;
import com.zhide.app.utils.ToastUtil;
import com.zhide.app.view.base.BaseFragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Create by Admin on 2018/9/5
 */
public class WalletChargeFragment extends BaseFragment {
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

    @BindView(R.id.cbSelectWxPay)
    CheckBox cbSelectWxPay;
    @BindView(R.id.cbSelectAliPay)
    CheckBox cbSelectAliPay;

    private IGetAliPayResult alipayResult;
    private List<TextView> selectTvList;
    private long userId;

    @Override
    protected void initView() {
        cbSelectWxPay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cbSelectAliPay.setChecked(false);
                }
            }
        });

        cbSelectAliPay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cbSelectWxPay.setChecked(false);
                }
            }
        });

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

    @Override
    protected void initData() {
        selectTvList = new ArrayList<>();
        selectTvList.add(tvCharge30);
        selectTvList.add(tvCharge40);
        selectTvList.add(tvCharge50);
        selectTvList.add(tvCharge80);
        selectTvList.add(tvCharge100);
        selectTvList.add(tvChargeOther);
        updateTvState(tvCharge30);
        userId = PreferencesUtils.getLong(CommonParams.LOGIN_USER_ID);

        alipayResult = new IGetAliPayResult() {
            @Override
            public void getResult(Map<String, String> result) {
                Log.d("xyc", "getResult: result=" + result);
            }
        };
    }

    @Override
    protected void reLoadData() {

    }

    @Override
    protected int setFrgContainView() {
        return R.layout.wallet_charge_frag_layout;
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
                DialogUtils.showTipsDialog(getActivity(), getString(R.string.charge_other_tip), true, new IConfirmClickListener() {
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
                if (!cbSelectWxPay.isChecked() && !cbSelectAliPay.isChecked()) {
                    ToastUtil.showShort(ResourceUtils.getInstance().getString(R.string.select_pay_type));
                    return;
                }
                if (cbSelectWxPay.isChecked()) {
                    PayManager.getInstance().getWxPayParams(selectAmount,userId);
                } else if (cbSelectAliPay.isChecked()) {
                    PayManager.getInstance().getAliPayParams(selectAmount);
                }
                break;
        }
    }

    /**
     * 后台返回支付信息，回调到这里
     *
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
            PayManager.getInstance().sendWxPayRequest(wxPayParamModel);
        } else {
            AliPayParamModel aliPayParamModel = event.getAliPayParamModel();
            if (aliPayParamModel == null) {
                ToastUtil.showShort(getString(R.string.get_net_data_error));
                return;
            }
            PayManager.getInstance().sendAliPayRequest(getActivity(), aliPayParamModel, alipayResult);
        }
    }

}
