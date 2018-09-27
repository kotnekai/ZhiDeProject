package com.zhide.app.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.zhide.app.R;
import com.zhide.app.common.CommonParams;
import com.zhide.app.eventBus.MineAccountEvent;
import com.zhide.app.eventBus.SaveInfoEvent;
import com.zhide.app.eventBus.SchoolInfoEvent;
import com.zhide.app.eventBus.UserInfoEvent;
import com.zhide.app.logic.MainManager;
import com.zhide.app.logic.UserManager;
import com.zhide.app.model.AccountInfoModel;
import com.zhide.app.model.ResponseModel;
import com.zhide.app.model.SchoolInfoModel;
import com.zhide.app.model.UserData;
import com.zhide.app.utils.DialogUtils;
import com.zhide.app.utils.PreferencesUtils;
import com.zhide.app.utils.ToastUtil;
import com.zhide.app.utils.UIUtils;
import com.zhide.app.view.activity.LoginActivity;
import com.zhide.app.view.activity.MyBillActivity;
import com.zhide.app.view.activity.QRCodeActivity;
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
    @BindView(R.id.tvSchoolName)
    TextView tvSchoolName;
    @BindView(R.id.edtGender)
    EditText edtGender;
    @BindView(R.id.tvBindSchool)
    TextView tvBindSchool;

    @BindView(R.id.edtRoomAddress)
    EditText edtRoomAddress;
    @BindView(R.id.edtStuId)
    EditText edtStuId;
    @BindView(R.id.edtIdCard)
    EditText edtIdCard;

    @BindView(R.id.edtWaterCardId)
    EditText edtWaterCardId;

    @BindView(R.id.tvResetPsw)
    TextView tvResetPsw;
    @BindView(R.id.tvLoginOut)
    TextView tvLoginOut;

    @BindView(R.id.tvBaseBalance)
    TextView tvBaseBalance;
    @BindView(R.id.tvGiftBalance)
    TextView tvGiftBalance;



    private float totalMoney;
    private long userId;

    @Override
    protected void initData() {
        userId = PreferencesUtils.getLong(CommonParams.LOGIN_USER_ID);

        UserManager.getInstance().getUserInfoById(userId, CommonParams.PAGE_MINE_FRAG_TYPE);
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
        tvSaveInfo.setEnabled(false);

        edtUserName.addTextChangedListener(this);
        tvSchoolName.addTextChangedListener(this);
        edtGender.addTextChangedListener(this);
        edtStuId.addTextChangedListener(this);
        edtIdCard.addTextChangedListener(this);
        edtWaterCardId.addTextChangedListener(this);
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

    }

    /**
     * 学校信息
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSchoolInfoEvent(SchoolInfoEvent event) {
        SchoolInfoModel schoolInfoModel = event.getSchoolInfoModel();
        if (schoolInfoModel.getCode() != 1) {
            return;
        }
        SchoolInfoModel.SchoolModel data = schoolInfoModel.getData();
        if (data == null) {
            return;
        }
        updateSchoolInfo(data);

    }

    private void updateSchoolInfo(SchoolInfoModel.SchoolModel data) {
        String si_name = data.getSI_Name();
        tvSchoolName.setText(si_name);
    }

    /**
     * 用户信息
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUserInfoEvent(UserInfoEvent event) {
        if (event.getUpdatePage() != 3) {
            return;
        }
        UserData userData = event.getUserData();
        if (userData == null) {
            return;
        }
        updateUserInfoUI(userData);
    }

    private void updateUserInfoUI(UserData userData) {
        totalMoney = userData.getUSI_TotalBalance();
        tvTotalMoney.setText(UIUtils.getFloatData(userData.getUSI_TotalBalance()));
        tvBaseBalance.setText(UIUtils.getFloatData(userData.getUSI_MainBalance()));
        tvGiftBalance.setText(UIUtils.getFloatData(userData.getUSI_GiftBalance()));
        edtUserName.setText(userData.getUSI_TrueName());
        edtRoomAddress.setText(userData.getUSI_SchoolRoomNo());
        edtGender.setText(userData.getUSI_Sex());
        edtStuId.setText(userData.getUSI_SchoolNo());
        edtIdCard.setText(userData.getUSI_IDCard());
        edtWaterCardId.setText(userData.getUSI_Card_SN_PIN());
        if(userData.getSI_Code()==null){
            llSchool.setVisibility(View.GONE);
            tvBindSchool.setVisibility(View.VISIBLE);
        }else {
            guidStr = userData.getSI_Code();
            llSchool.setVisibility(View.VISIBLE);
            tvBindSchool.setVisibility(View.GONE);
            tvSchoolName.setText(userData.getSI_Name());
        }
    }

    /**
     * 保存用户修改资料信息回调
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSaveInfoEvent(SaveInfoEvent event) {
        ResponseModel responseModel = event.getResponseModel();
        if (responseModel == null) {
            return;
        }
        if (responseModel.getCode() == 1) {
            tvSaveInfo.setSelected(false);
            tvSaveInfo.setEnabled(false);
        }
        ToastUtil.showShort(responseModel.getMsg());
    }


    @OnClick({R.id.tvBindSchool, R.id.tvSaveInfo, R.id.tvRecharge, R.id.tvWithdraw, R.id.tvMyBill, R.id.llSchool, R.id.tvResetPsw, R.id.tvLoginOut})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvRecharge:
                startActivity(RechargeActivity.makeIntent(getActivity()));
                break;
            case R.id.tvWithdraw:
                startActivity(WithdrawActivity.makeIntent(getActivity(), totalMoney));
                break;
            case R.id.tvMyBill:
                startActivity(MyBillActivity.makeIntent(getActivity()));
                break;
            case R.id.llSchool:
                if (guidStr == null) {
                    ToastUtil.showShort(getString(R.string.please_bind_school_tip));
                    return;
                }
                String schoolName = tvSchoolName.getText().toString();

                startActivity(QRCodeActivity.makeIntent(getActivity(), guidStr, schoolName));
                break;

            case R.id.tvResetPsw:
                startActivity(ResetPswActivity.makeIntent(getActivity()));
                break;
            case R.id.tvLoginOut:

                DialogUtils.showConfirmDialog(getActivity(), getString(R.string.login_out), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       PreferencesUtils.putLong(CommonParams.LOGIN_USER_ID, 0);
                        startActivity(LoginActivity.makeIntent(getActivity()));
                        getActivity().finish();
                    }
                });
                break;
            case R.id.tvSaveInfo:
                submitPersonInfo();
                break;
            case R.id.tvBindSchool:
                Intent intent = new Intent(getActivity(), CaptureActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                break;
        }
    }

    private String guidStr;
    private final int REQUEST_CODE = 101;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("admin", "onActivityResult: data=" + data);
        /**
         * 处理二维码扫描结果
         */
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    ToastUtil.showShort(UIUtils.getValueString(R.string.scan_code));

                    if (result != null) {
                        guidStr = result;
                        ToastUtil.showShort(result);
                        tvBindSchool.setVisibility(View.GONE);
                        llSchool.setVisibility(View.VISIBLE);
                        UserManager.getInstance().getUserSchoolInfo(guidStr);
                        submitPersonInfo();
                    } else {
                        tvBindSchool.setVisibility(View.VISIBLE);
                        llSchool.setVisibility(View.GONE);
                    }
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    ToastUtil.showShort(UIUtils.getValueString(R.string.scan_code));
                }
            }
        }
    }

    private void submitPersonInfo() {
        long userId = PreferencesUtils.getLong(CommonParams.LOGIN_USER_ID);

        UserData userData = new UserData();
        userData.setUSI_Id(userId);
        userData.setUSI_TrueName(edtUserName.getText().toString());
        userData.setUSI_SchoolNo(edtStuId.getText().toString());
        // 房间号
        userData.setUSI_Sex(edtGender.getText().toString());
        userData.setUSI_IDCard(edtIdCard.getText().toString());
        userData.setUSI_Card_SN_PIN(edtWaterCardId.getText().toString());
        if (guidStr != null) {
            userData.setSI_Code(guidStr);
        }
        MainManager.getInstance().savePersonInfo(userData);

    }

    private String beforeText;

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        beforeText = s.toString();
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        if (beforeText.equals(s.toString())) {
            tvSaveInfo.setSelected(false);
            tvSaveInfo.setEnabled(false);
            return;
        }
        tvSaveInfo.setSelected(true);
        tvSaveInfo.setEnabled(true);

    }
}
