package com.zhide.app.view.fragment;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zhide.app.R;
import com.zhide.app.common.CommonParams;
import com.zhide.app.eventBus.OkResponseEvent;
import com.zhide.app.eventBus.UserInfoEvent;
import com.zhide.app.logic.ChargeManager;
import com.zhide.app.logic.UserManager;
import com.zhide.app.model.ResponseModel;
import com.zhide.app.model.UserData;
import com.zhide.app.utils.PreferencesUtils;
import com.zhide.app.utils.ProgressUtils;
import com.zhide.app.utils.ToastUtil;
import com.zhide.app.view.activity.CardChargeBillActivity;
import com.zhide.app.view.base.BaseFragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Create by Admin on 2018/9/5
 */
public class CardChargeFragment extends BaseFragment {
    @BindView(R.id.tvTotalBalance)
    TextView tvTotalBalance;
    @BindView(R.id.tvCashBalance)
    TextView tvCashBalance;
    @BindView(R.id.tvGiftBalance)
    TextView tvGiftBalance;

    @BindView(R.id.tvCardMoney)
    TextView tvCardMoney;
    @BindView(R.id.tvReCharge)
    TextView tvReCharge;
    @BindView(R.id.tvCardBill)
    TextView tvCardBill;
    @BindView(R.id.tvIntroCard)
    TextView tvIntroCard;
    @BindView(R.id.edtInputMoney)
    EditText edtInputMoney;

    private long userId;

    @Override
    protected void initView() {

    }


    @Override
    protected void initData() {

        userId = PreferencesUtils.getLong(CommonParams.LOGIN_USER_ID);
        UserManager.getInstance().getUserInfoById(userId, CommonParams.PAGE_CARD_CHARGE_FRAG_TYPE);
    }


    @Override
    protected void reLoadData() {

    }

    @Override
    protected int setFrgContainView() {
        return R.layout.card_charge_frag_layout;
    }

    /**
     * 第一次进来，，更新界面数据
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUserInfoEvent(UserInfoEvent event) {
        int updatePage = event.getUpdatePage();
        if (updatePage != 2) {
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
     * @param userData
     */
    private void updateUI(UserData userData) {
        tvTotalBalance.setText(String.valueOf(userData.getUSI_TotalBalance()) + "元");
        tvCashBalance.setText(String.valueOf(userData.getUSI_MainBalance()) + "元");
        tvGiftBalance.setText(String.valueOf(userData.getUSI_GiftBalance()) + "元");
        tvCardMoney.setText(String.valueOf(userData.getUSI_MainCardBalance()) + "元");
        tvIntroCard.setText(userData.getSI_Card_Recharge_Desc());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onOkResponseEvent(OkResponseEvent event) {
        ProgressUtils.getIntance().dismissProgress();
        ResponseModel responseModel = event.getResponseModel();
        if (responseModel == null) {
            return;
        }
        ToastUtil.showShort(responseModel.getMsg());
        if (responseModel.getCode() == 1) {
            UserManager.getInstance().getUserInfoById(userId, CommonParams.PAGE_CARD_CHARGE_FRAG_TYPE);
        }
    }

    @OnClick({R.id.tvReCharge, R.id.tvCardBill})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvReCharge:
                String selectAmount = edtInputMoney.getText().toString();
                if (selectAmount.isEmpty()) {
                    ToastUtil.showShort(getString(R.string.input_card_money));
                    return;
                }
                float amount = Float.parseFloat(selectAmount);
                ProgressUtils.getIntance().setProgressDialog(getString(R.string.charge_loading), getActivity());
                ChargeManager.getInstance().payToCard(userId, amount);
                break;
            case R.id.tvCardBill:
                startActivity(CardChargeBillActivity.makeIntent(getActivity()));
                break;
        }
    }


}
