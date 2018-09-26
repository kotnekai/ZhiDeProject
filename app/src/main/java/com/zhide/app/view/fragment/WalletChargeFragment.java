package com.zhide.app.view.fragment;

import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.zhide.app.R;
import com.zhide.app.common.ApplicationHolder;
import com.zhide.app.common.CommonParams;
import com.zhide.app.delegate.IConfirmClickListener;
import com.zhide.app.delegate.IGetAliPayResult;
import com.zhide.app.eventBus.PayOrderEvent;
import com.zhide.app.eventBus.PayResultEvent;
import com.zhide.app.eventBus.UserInfoEvent;
import com.zhide.app.logic.PayManager;
import com.zhide.app.logic.UserManager;
import com.zhide.app.model.AliPayParamModel;
import com.zhide.app.model.UserData;
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
        UserManager.getInstance().getUserInfoById(userId, CommonParams.PAGE_WALLET_FRAG_TYPE);

        alipayResult = new IGetAliPayResult() {
            @Override
            public void getResult(Map<String, String> result) {
                Log.d("xyc", "getResult: result=" + result);
                String resultStatus = result.get("resultStatus");
                if (resultStatus.equals("9000")) {
                    ToastUtil.showShort(getString(R.string.pay_success));
                    UserManager.getInstance().getUserInfoById(userId, CommonParams.PAGE_WALLET_FRAG_TYPE);
                }
            }
        };

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
    public void onUserInfoEvent(UserInfoEvent event) {
        int updatePage = event.getUpdatePage();
        if (updatePage != CommonParams.PAGE_WALLET_FRAG_TYPE) {
            return;
        }
        UserData userData = event.getUserData();

        if (userData == null) {
            ToastUtil.showShort(getString(R.string.get_net_data_error));
            return;
        }
        updateUI(userData);
    }

    /**
     * 更新界面数据
     *
     * @param
     */
    private void updateUI(UserData userData) {
        tvTotalBalance.setText(String.valueOf(userData.getUSI_TotalBalance()) + "元");
        tvCashBalance.setText(String.valueOf(userData.getUSI_MainBalance()) + "元");
        tvGiftBalance.setText(String.valueOf(userData.getUSI_GiftBalance()) + "元");
        tvIntroActContent.setText(userData.getSI_Recharge_Desc());

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
                            tvChargeOther.setText(getString(R.string.charge_other_tip));
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
                Log.d("admin", "onClick: cbSelectWxPay.isChecked()=" + cbSelectWxPay.isChecked());
                if (cbSelectWxPay.isChecked()) {
                    PayManager.getInstance().getWxPayParams(selectAmount, userId);
                } else if (cbSelectAliPay.isChecked()) {
                    PayManager.getInstance().getAliPayParams(selectAmount);
                   /* AliPayParamModel aliPayParamModel = new AliPayParamModel();
                    aliPayParamModel.setOrderInfo("app_id=2018072860793651&biz_content=%7b%22body%22%3a%22%e6%99%ba%e5%be%97%e8%83%bd%e6%ba%90APP%e5%85%85%e5%80%bc%22%2c%22out_trade_no%22%3a%22B1809261810283130001%22%2c%22product_code%22%3a%22QUICK_MSECURITY_PAY%22%2c%22subject%22%3a%22%e6%99%ba%e5%be%97%e8%83%bd%e6%ba%90APP%e5%85%85%e5%80%bc%22%2c%22timeout_express%22%3a%2230m%22%2c%22total_amount%22%3a%220.2%22%7d&charset=UTF-8&format=json&method=alipay.trade.app.pay&notify_url=http%3a%2f%2fzdtest.zlan-ai.com%2fPayCallback%2fAlipayResultNotifyProcess&sign_type=RSA2&timestamp=2018-09-26+18%3a10%3a28&version=1.0&sign=u0%2fa6g5pUFfdKN9lnxIcAzX3Md2pl6Kp68dEC%2fv%2bc4GSYgcznNsv5HsjiPQKFfmg3lisl3%2bhVtyIGIat6q2%2fZ8HbwAFofnWG5icKIskKIqFQ5bEqZ6PEqybd%2fW5Rr8%2fZ3SNdykCpZlZHrnWzRsEgsdvf0ZPE9DXr1pjFLgIwI2U%2bmwQDPnzH6EVa5etq0PshsdM45tf%2bdcTiHSRWyK04Sh0DCoIdu6jc3RypSF1gQD9DoqqFCxvJrqF7JZ%2fsfkcE7DhkgiJfxHjxJynmD2%2bAsBm%2fL8WljfThgO9zikDAX%2fOxQrVMRWtnypM%2fQpcxRksEdcDtq7nzmIGYbG4QGln7FQ%3d%3d");
*/
                    //PayManager.getInstance().sendAliPayRequest(getActivity(), aliPayParamModel, alipayResult);

                }
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPayResultEvent(PayResultEvent event) {
        int code = event.getCode();
        if (code == 0) {
            UserManager.getInstance().getUserInfoById(userId, CommonParams.PAGE_WALLET_FRAG_TYPE);
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
            WXPayParamModel.WxpayParamsData paramData = wxPayParamModel.getData();
            if (paramData == null) {
                return;
            }
            if (!ApplicationHolder.getInstance().getMsgApi().isWXAppInstalled()) {
                ToastUtil.showShort("请您先安装微信客户端！");
                return;
            }
            PayManager.getInstance().sendWxPayRequest(paramData);

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
