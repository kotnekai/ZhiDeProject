package com.zhide.app.view.fragment;

import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhide.app.R;
import com.zhide.app.common.CommonParams;
import com.zhide.app.eventBus.MineAccountEvent;
import com.zhide.app.eventBus.SaveInfoEvent;
import com.zhide.app.eventBus.UserInfoEvent;
import com.zhide.app.logic.MainManager;
import com.zhide.app.logic.UserManager;
import com.zhide.app.model.AccountInfoModel;
import com.zhide.app.model.ResponseModel;
import com.zhide.app.model.UserData;
import com.zhide.app.model.UserInfoModel;
import com.zhide.app.utils.DialogUtils;
import com.zhide.app.utils.PreferencesUtils;
import com.zhide.app.utils.ToastUtil;
import com.zhide.app.view.activity.LoginActivity;
import com.zhide.app.view.activity.MyBillActivity;
import com.zhide.app.view.activity.RechargeActivity;
import com.zhide.app.view.activity.ResetPswActivity;
import com.zhide.app.view.activity.WithdrawActivity;
import com.zhide.app.view.base.BaseFragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

public class MineFragment extends BaseFragment implements TextWatcher {

    @BindView(R.id.tvRecharge)
    TextView tvRecharge;
    @BindView(R.id.tvWithdraw)
    TextView tvWithdraw;
    @BindView(R.id.tvDetailMoney)
    TextView tvDetailMoney;

    @BindView(R.id.tvTotalMoney)
    TextView tvTotalMoney;
    @BindView(R.id.tvSaveInfo)
    TextView tvSaveInfo;

    @BindView(R.id.tvMyBill)
    TextView tvMyBill;
    @BindView(R.id.edtUserName)
    TextView edtUserName;
    @BindView(R.id.llSchool)
    LinearLayout llSchool;
    @BindView(R.id.edtSchoolName)
    EditText edtSchoolName;
    @BindView(R.id.edtGender)
    EditText edtGender;
    @BindView(R.id.edtStuId)
    EditText edtStuId;
    @BindView(R.id.edtIdCard)
    EditText edtIdCard;
    @BindView(R.id.tvResetPsw)
    TextView tvResetPsw;
    @BindView(R.id.tvLoginOut)
    TextView tvLoginOut;
    private float totalMoney;
    private long userId;

    @Override
    protected void initData() {
        userId = PreferencesUtils.getLong(CommonParams.LOGIN_USER_ID);

        UserManager.getInstance().getUserInfoById(userId);
    }

    @Override
    protected void reLoadData() {

    }

    @Override
    protected int setFrgContainView() {
        return R.layout.fragment_mine;

    }

    @Override
    protected void initView() {
        tvSaveInfo.setSelected(false);
        edtUserName.addTextChangedListener(this);
        edtSchoolName.addTextChangedListener(this);
        edtGender.addTextChangedListener(this);
        edtStuId.addTextChangedListener(this);
        edtIdCard.addTextChangedListener(this);

        tvDetailMoney.setText(Html.fromHtml("（基本余额：" + "<font color='#4a86ba'>" + 89.98 + "</font>元," + "赠送余额：" + "<font color='#f06101'>"
                + 189.98 + "</font>元）"));
    }

    /**
     * 账户信息
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMineAccountEvent(MineAccountEvent event) {
        AccountInfoModel infoModel = event.getInfoModel();
        if (infoModel == null) {
            ToastUtil.showShort(getString(R.string.get_net_data_error));
            return;
        }
        updateUI(infoModel);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUserInfoEvent(UserInfoEvent event) {
        UserData userData = event.getUserData();
        if (userData == null) {
            return;
        }
        updateInfoUI(userData);
    }

    private void updateInfoUI(UserData userData) {
        edtUserName.setText(userData.getUSI_TrueName());
        edtSchoolName.setText("");
        edtGender.setText(userData.getUSI_Sex());
        edtStuId.setText(userData.getUSI_SchoolNo());
        edtIdCard.setText(userData.getUSI_IDCard());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSaveInfoEvent(SaveInfoEvent event) {
        ResponseModel responseModel = event.getResponseModel();
        if (responseModel == null) {
            return;
        }
        if (responseModel.getCode() == 1) {
            tvSaveInfo.setSelected(false);
        }
        ToastUtil.showShort(responseModel.getMsg());
    }

    private void updateUI(AccountInfoModel infoModel) {
        totalMoney = infoModel.getTotalMoney();
        tvTotalMoney.setText(String.valueOf(totalMoney));

        tvDetailMoney.setText("（基本余额：" + "<font color='#4a86ba'>" + infoModel.getBaseMoney() + "</font>元," + "赠送余额：" + "<font color='#f06101'>"
                + infoModel.getGiftMoney() + "</font>元）");
    }


    @OnClick({R.id.tvSaveInfo, R.id.tvRecharge, R.id.tvWithdraw, R.id.tvMyBill, R.id.llSchool, R.id.tvResetPsw, R.id.tvLoginOut})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvRecharge:
                tvSaveInfo.setSelected(true);
                startActivity(RechargeActivity.makeIntent(getActivity()));
                break;
            case R.id.tvWithdraw:
                tvSaveInfo.setSelected(false);
                startActivity(WithdrawActivity.makeIntent(getActivity(), totalMoney));
                break;
            case R.id.tvMyBill:
                startActivity(MyBillActivity.makeIntent(getActivity()));
                break;
            case R.id.llSchool:
                break;

            case R.id.tvResetPsw:
                startActivity(ResetPswActivity.makeIntent(getActivity()));
                break;
            case R.id.tvLoginOut:
                DialogUtils.showConfirmDialog(getActivity(), "退出登录", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(LoginActivity.makeIntent(getActivity()));
                        getActivity().finish();
                    }
                });
                break;
            case R.id.tvSaveInfo:
                submitPersonInfo();
                break;
        }
    }

    private void submitPersonInfo() {
        String gUid = "";
        long userId = PreferencesUtils.getLong(CommonParams.LOGIN_USER_ID);

        UserData userData = new UserData();
        userData.setUSI_Id(userId);
        userData.setUSI_TrueName(edtUserName.getText().toString());
        userData.setUSI_SchoolNo(edtStuId.getText().toString());
        // 房间号
        userData.setUSI_Sex(edtGender.getText().toString());
        userData.setUSI_IDCard(edtIdCard.getText().toString());
        userData.setSI_Code(gUid);
        MainManager.getInstance().savePersonInfo(userData);

    }


    private String beforeText;

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        Log.d("admin", "beforeTextChanged: s1=" + s.toString());
        beforeText = s.toString();
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        Log.d("admin", "beforeTextChanged: s2=" + s.toString());

        if (beforeText.isEmpty() || beforeText.equals(s.toString())) {
            tvSaveInfo.setSelected(false);
            return;
        }
        tvSaveInfo.setSelected(true);
        beforeText = "";
    }
}
