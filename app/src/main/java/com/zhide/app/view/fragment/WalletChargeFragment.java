package com.zhide.app.view.fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.zhide.app.R;
import com.zhide.app.common.ApplicationHolder;
import com.zhide.app.common.CommonParams;
import com.zhide.app.delegate.IConfirmClickListener;
import com.zhide.app.delegate.IGetAliPayResult;
import com.zhide.app.delegate.ISelectClickListener;
import com.zhide.app.eventBus.PayOrderEvent;
import com.zhide.app.eventBus.PayResultEvent;
import com.zhide.app.eventBus.UserInfoEvent;
import com.zhide.app.logic.PayManager;
import com.zhide.app.logic.UserManager;
import com.zhide.app.model.AliPayParamModel;
import com.zhide.app.model.SelectModel;
import com.zhide.app.model.UserData;
import com.zhide.app.model.WXPayParamModel;
import com.zhide.app.utils.ClientInstallUtils;
import com.zhide.app.utils.DialogUtils;
import com.zhide.app.utils.EmptyUtil;
import com.zhide.app.utils.PreferencesUtils;
import com.zhide.app.utils.ResourceUtils;
import com.zhide.app.utils.ToastUtil;
import com.zhide.app.view.base.BaseFragment;
import com.zhide.app.view.views.FlowLayout;
import com.zhide.app.view.views.SelectItemView;

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

    @BindView(R.id.flSelectAmount)
    FlowLayout flSelectAmount;

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
        userData.setSI_RechargeMoney("30|50|80|100|其他");

        String rechargeMoney = userData.getSI_RechargeMoney();
        //SI_RechargeMoney":"30|50|80|100",
        if (EmptyUtil.isEmpty(rechargeMoney)) {
            return;
        }
        String[] splitData = rechargeMoney.split("\\|");
        for (int i = 0; i < splitData.length; i++) {
            SelectModel selectModel = new SelectModel();
            selectModel.setName(splitData[i]);
            selectModel.setId(i);
            SelectItemView selectItemView = (SelectItemView) LayoutInflater.from(getActivity()).inflate(R.layout.select_item_view, null);
            if (i == 0) {
                selectModel.setCheck(true);
            }
            selectItemView.setSelectModel(selectModel);
            flSelectAmount.addView(selectItemView);
            selectItemView.setOnSelectItemListener(new ISelectClickListener() {
                @Override
                public void selectIt(TextView view, String selectName, long selectId) {
                    ToastUtil.showShort(selectName);
                    updateState(flSelectAmount, selectId);
                    selectClick(selectName,view);
                }
            });
        }

    }

    private void selectClick(String selectName, final TextView tvSelect) {
        if(selectName.equals("其他")){
            DialogUtils.showTipsDialog(getActivity(), getString(R.string.input_other_tip), getString(R.string.input_other_money_tip), true, new IConfirmClickListener() {
                @Override
                public void confirmClick(String remarks) {
                    if (EmptyUtil.isEmpty(remarks)) {
                        tvSelect.setText(getString(R.string.charge_other_tip));
                        selectAmount = 0;
                    } else {
                        selectAmount = Float.parseFloat(remarks);
                        if (selectAmount < 100 || selectAmount > 300) {
                            ToastUtil.showShort(getString(R.string.input_other_tip));
                            tvSelect.setText(getString(R.string.charge_other_tip));
                            return;
                        }
                        tvSelect.setText(remarks);
                    }
                }
            });
        }else {
            selectAmount = Float.parseFloat(selectName);
        }
    }

    /**
     * 点击之后更新所有按钮的状态
     *
     * @param flowLayout
     * @param selectId
     */
    public void updateState(FlowLayout flowLayout, long selectId) {
        if (flowLayout == null || flowLayout.getChildCount() == 0) {
            return;
        }
        for (int i = 0; i < flowLayout.getChildCount(); i++) {
            SelectItemView selectItemView = (SelectItemView) flowLayout.getChildAt(i);
            SelectModel selectModel = selectItemView.getSelectModel();
            if (selectModel.getId() == selectId) {
                selectItemView.setSelected(true);
                selectModel.setCheck(true);
            } else {
                selectItemView.setSelected(false);
                selectModel.setCheck(false);
            }
            selectItemView.setSelectModel(selectModel);
        }
    }


    public static final int aliPayType = 1;
    public static final int wxPayType = 2;
    public float selectAmount = 0;

    @OnClick({R.id.tvReCharge})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvReCharge:
                if(selectAmount==0){
                    ToastUtil.showShort(getString(R.string.select_amount));
                    return;
                }
                if (!cbSelectWxPay.isChecked() && !cbSelectAliPay.isChecked()) {
                    ToastUtil.showShort(ResourceUtils.getInstance().getString(R.string.select_pay_type));
                    return;
                }
                if (cbSelectWxPay.isChecked()) {
                    PayManager.getInstance().getWxPayParams(selectAmount, userId);
                } else if (cbSelectAliPay.isChecked()) {
                    boolean isInstallAliPay = ClientInstallUtils.checkAliPayInstalled(getActivity());
                    if (!isInstallAliPay) {
                        ToastUtil.showShort(getString(R.string.please_install_aliPay));
                        return;
                    }
                    PayManager.getInstance().getAliPayParams(selectAmount, userId);
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
                ToastUtil.showShort(getString(R.string.please_install_weChat));
                return;
            }
            PayManager.getInstance().sendWxPayRequest(paramData);

        } else {
            AliPayParamModel aliPayParamModel = event.getAliPayParamModel();
            if (aliPayParamModel == null) {
                ToastUtil.showShort(getString(R.string.get_net_data_error));
                return;
            }
            String orderInfo = aliPayParamModel.getData();
            if (orderInfo == null || orderInfo.isEmpty()) {
                ToastUtil.showShort(getString(R.string.get_net_data_error));
            }
            PayManager.getInstance().sendAliPayRequest(getActivity(), orderInfo, alipayResult);
        }
    }

}
