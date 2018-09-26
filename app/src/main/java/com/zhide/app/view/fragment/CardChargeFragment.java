package com.zhide.app.view.fragment;

import android.view.View;
import android.widget.TextView;

import com.zhide.app.R;
import com.zhide.app.common.CommonParams;
import com.zhide.app.delegate.IConfirmClickListener;
import com.zhide.app.eventBus.OkResponseEvent;
import com.zhide.app.eventBus.UserInfoEvent;
import com.zhide.app.logic.ChargeManager;
import com.zhide.app.logic.UserManager;
import com.zhide.app.model.ResponseModel;
import com.zhide.app.model.UserData;
import com.zhide.app.utils.DialogUtils;
import com.zhide.app.utils.EmptyUtil;
import com.zhide.app.utils.PreferencesUtils;
import com.zhide.app.utils.ProgressUtils;
import com.zhide.app.utils.ToastUtil;
import com.zhide.app.view.activity.CardChargeBillActivity;
import com.zhide.app.view.base.BaseFragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

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
    @BindView(R.id.tvCardMoney)
    TextView tvCardMoney;
    @BindView(R.id.tvReCharge)
    TextView tvReCharge;
    @BindView(R.id.tvCardBill)
    TextView tvCardBill;
    @BindView(R.id.tvIntroCard)
    TextView tvIntroCard;

    private List<TextView> selectTvList;
    private long userId;

    @Override
    protected void initView() {

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
        UserManager.getInstance().getUserInfoById(userId,CommonParams.PAGE_CARD_CHARGE_FRAG_TYPE);
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
        if(updatePage!=2){
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
        ResponseModel responseModel = event.getResponseModel();
        if (responseModel == null) {
            return;
        }
        ToastUtil.showShort(responseModel.getMsg());
        if(responseModel.getCode()==1){
            UserManager.getInstance().getUserInfoById(userId,CommonParams.PAGE_CARD_CHARGE_FRAG_TYPE);
        }
    }

    public static final int aliPayType = 1;
    public static final int wxPayType = 2;
    public float selectAmount = 30;

    @OnClick({R.id.tvCharge30, R.id.tvCharge40, R.id.tvCharge50, R.id.tvCharge80, R.id.tvCharge100, R.id.tvChargeOther, R.id.tvReCharge, R.id.tvCardBill})
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
                ProgressUtils.getIntance().setProgressDialog(getString(R.string.charge_loading), getActivity());
                ChargeManager.getInstance().payToCard(userId, selectAmount);
                break;
            case R.id.tvCardBill:
                startActivity(CardChargeBillActivity.makeIntent(getActivity()));
                break;
        }
    }


}
