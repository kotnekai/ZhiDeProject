package com.zhide.app.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import com.zhide.app.utils.EmptyUtil;
import com.zhide.app.utils.PreferencesUtils;
import com.zhide.app.utils.ToastUtil;
import com.zhide.app.utils.UIUtils;
import com.zhide.app.view.activity.ChangeLoginPswActivity;
import com.zhide.app.view.activity.LoginActivity;
import com.zhide.app.view.activity.MyBillActivity;
import com.zhide.app.view.activity.QRCodeActivity;
import com.zhide.app.view.activity.RechargeActivity;
import com.zhide.app.view.activity.ScannerQrActivity;
import com.zhide.app.view.activity.WithdrawActivity;
import com.zhide.app.view.base.BaseFragment;
import com.zhide.app.view.views.SpinerPopWindow;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MineFragment extends BaseFragment implements TextWatcher, AdapterView.OnItemClickListener {

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
    @BindView(R.id.tvGender)
    TextView tvGender;
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

    @BindView(R.id.rlCardId)
    RelativeLayout rlCardId;
    @BindView(R.id.view9)
    View view9;


    private float mainMoney;
    private long userId;
    private UserData userData;
    private String selectSex;
    private SpinerPopWindow mSpinerPopWindow;

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

    private List<String> genderList;

    @Override
    protected void initView() {
        tvSaveInfo.setSelected(false);
        tvSaveInfo.setEnabled(false);

        edtUserName.addTextChangedListener(this);
        tvSchoolName.addTextChangedListener(this);

        edtStuId.addTextChangedListener(this);
        edtIdCard.addTextChangedListener(this);
        edtWaterCardId.addTextChangedListener(this);
        genderList = new ArrayList<>();
        genderList.add("男");
        genderList.add("女");
        if (mSpinerPopWindow == null) {
            mSpinerPopWindow = new SpinerPopWindow<>(getActivity(), genderList, this);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (mSpinerPopWindow != null && mSpinerPopWindow.isShowing()) {
            mSpinerPopWindow.dismiss();
        }
       tvGender.setText(genderList.get(position));
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
        userData = event.getUserData();
        if (userData == null) {
            return;
        }
        updateUserInfoUI();
    }

    private void updateUserInfoUI() {

        mainMoney = userData.getUSI_MainBalance();
        String IsRefund = userData.getSI_IsRefund();
        if (IsRefund.equals(getString(R.string.yes_tip))) {
            tvWithdraw.setVisibility(View.VISIBLE);
        } else {
            tvWithdraw.setVisibility(View.GONE);
        }
        if (userData.getSI_UseMode().equals("单蓝牙")) {
            rlCardId.setVisibility(View.GONE);
            view9.setVisibility(View.GONE);
        } else {
            edtWaterCardId.setVisibility(View.GONE);
            rlCardId.setVisibility(View.VISIBLE);
            view9.setVisibility(View.VISIBLE);
        }
        tvTotalMoney.setText(UIUtils.getFloatData(userData.getUSI_TotalBalance()));
        tvBaseBalance.setText(UIUtils.getFloatData(userData.getUSI_MainBalance()));
        tvGiftBalance.setText(UIUtils.getFloatData(userData.getUSI_GiftBalance()));
        edtUserName.setText(userData.getUSI_TrueName());
        edtRoomAddress.setText(userData.getUSI_SchoolRoomNo());

        tvGender.setText(userData.getUSI_Sex());
        edtStuId.setText(userData.getUSI_SchoolNo());
        edtIdCard.setText(userData.getUSI_IDCard());
        edtWaterCardId.setText(userData.getUSI_Card_SN_PIN());

        if (userData.getSI_Code() == null) { // 表示未绑定学校
            llSchool.setVisibility(View.GONE);
            tvBindSchool.setVisibility(View.VISIBLE);

            edtRoomAddress.setEnabled(false);
            edtStuId.setEnabled(false);
            edtWaterCardId.setEnabled(false);

            edtRoomAddress.setHint(getString(R.string.no_bind_school));
            edtStuId.setHint(getString(R.string.no_bind_school));
            edtWaterCardId.setHint(getString(R.string.no_bind_school));
        } else {
            guidStr = userData.getSI_Code();
            llSchool.setVisibility(View.VISIBLE);
            tvBindSchool.setVisibility(View.GONE);
            tvSchoolName.setText(userData.getSI_Name());

            edtRoomAddress.setEnabled(true);
            edtRoomAddress.setHint(R.string.input_address_tip);

            edtStuId.setEnabled(true);
            edtStuId.setHint(R.string.input_stu_id);

            edtWaterCardId.setEnabled(true);
            edtWaterCardId.setHint("请输入热水卡号");
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


    @OnClick({R.id.tvGender, R.id.tvBindSchool, R.id.tvSaveInfo, R.id.tvRecharge, R.id.tvWithdraw, R.id.tvMyBill, R.id.llSchool, R.id.tvResetPsw, R.id.tvLoginOut})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvRecharge:
                if (userData == null) {
                    return;
                }
                boolean completeInfo1 = EmptyUtil.isCompleteInfo(userData);
                if (!completeInfo1) {
                    ToastUtil.showShort(getString(R.string.complete_info_tip));
                    return;
                }
                startActivity(RechargeActivity.makeIntent(getActivity()));
                break;
            case R.id.tvWithdraw:
                if (userData == null) {
                    return;
                }
                boolean completeInfo2 = EmptyUtil.isCompleteInfo(userData);
                if (!completeInfo2) {
                    ToastUtil.showShort(getString(R.string.complete_info_tip));
                    return;
                }
                startActivity(WithdrawActivity.makeIntent(getActivity(), mainMoney));
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
                startActivity(ChangeLoginPswActivity.makeIntent(getActivity()));
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
                Intent intent = new Intent(getActivity(), ScannerQrActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.tvGender:
                mSpinerPopWindow.setWidth(tvGender.getWidth());
                mSpinerPopWindow.showAsDropDown(tvGender, 0, 0, Gravity.CENTER_HORIZONTAL);
                break;
        }
    }

    private String guidStr;
    private final int REQUEST_CODE = 101;
    public static final String QR_DATA = "data";
    public static final int RESULT_CODE = 200;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("admin", "onActivityResult: data=" + data);
        if ((requestCode != REQUEST_CODE && resultCode != RESULT_CODE) || data == null) {
            return;
        }
        Bundle bundle = data.getExtras();
        if (bundle == null) {
            return;
        }
        /**
         * 处理二维码扫描结果
         */
        String result = bundle.getString(QR_DATA);
        Log.d("admin", "onActivityResult: result=" + result);
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
            ToastUtil.showShort(UIUtils.getValueString(R.string.scan_code));

        }
    }

    private void submitPersonInfo() {
        long userId = PreferencesUtils.getLong(CommonParams.LOGIN_USER_ID);

        UserData userData = new UserData();
        userData.setUSI_Id(userId);
        userData.setUSI_TrueName(edtUserName.getText().toString());
        userData.setUSI_SchoolNo(edtStuId.getText().toString());
        userData.setUSI_SchoolRoomNo(edtRoomAddress.getText().toString());
        // 性别
        userData.setUSI_Sex(tvGender.getText().toString());
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
