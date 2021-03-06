package com.zhide.app.view.fragment;

import android.content.Intent;
import android.os.Bundle;
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
import com.zhide.app.eventBus.RoomInfoEvent;
import com.zhide.app.eventBus.RoomInfoEvent2;
import com.zhide.app.eventBus.SaveInfoEvent;
import com.zhide.app.eventBus.SchoolInfoEvent;
import com.zhide.app.eventBus.UserInfoEvent;
import com.zhide.app.logic.MainManager;
import com.zhide.app.logic.UserManager;
import com.zhide.app.model.AccountInfoModel;
import com.zhide.app.model.ResponseModel;
import com.zhide.app.model.RoomInfoModel;
import com.zhide.app.model.SchoolInfoModel;
import com.zhide.app.model.SpinnerSelectModel;
import com.zhide.app.model.UserData;
import com.zhide.app.utils.DialogUtils;
import com.zhide.app.utils.EmptyUtil;
import com.zhide.app.utils.PickViewUtil;
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
import com.zhide.app.view.views.KeyboardListenRelativeLayout;
import com.zhide.app.view.views.SpinerPopWindow;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jaaksi.pickerview.dataset.OptionDataSet;
import org.jaaksi.pickerview.picker.OptionPicker;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MineFragment extends BaseFragment implements AdapterView.OnItemClickListener, KeyboardListenRelativeLayout.IOnKeyboardStateChangedListener {

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
    EditText edtUserName;
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

    @BindView(R.id.rlDayHeader)
    KeyboardListenRelativeLayout rlDayHeader;

    @BindView(R.id.tvSeat)
    TextView tvSeat;

    @BindView(R.id.tvFloor)
    TextView tvFloor;

    @BindView(R.id.tvRoom)
    TextView tvRoom;


    private float mainMoney;
    private long userId;
    private UserData userData;
    private String selectSex;
    private SpinerPopWindow mSpinerPopWindow;

    boolean isClickSeat = false, isClickFloor = false, isClickRoom = false;


    @Override
    protected void initData() {
        userId = PreferencesUtils.getLong(CommonParams.LOGIN_USER_ID);

        UserManager.getInstance().getUserInfoById(userId, CommonParams.PAGE_MINE_FRAG_TYPE);
        userData = PreferencesUtils.getObject(CommonParams.USER_INFO);
        if (userData != null) {
            updateUserInfoUI();
        }
    }

    @Override
    protected void reLoadData() {

    }

    @Override
    public void onResume() {
        super.onResume();
        UserManager.getInstance().getUserInfoById(userId, CommonParams.PAGE_MINE_FRAG_TYPE);
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

        genderList = new ArrayList<>();
        genderList.add("男");
        genderList.add("女");
        if (mSpinerPopWindow == null) {
            mSpinerPopWindow = new SpinerPopWindow<>(getActivity(), genderList, this);
        }
        rlDayHeader.setOnKeyboardStateChangedListener(this);

    }


    @Override
    public void onKeyboardStateChanged(int state) {
        switch (state) {
            case KeyboardListenRelativeLayout.KEYBOARD_STATE_HIDE://软键盘隐藏

                break;
            case KeyboardListenRelativeLayout.KEYBOARD_STATE_SHOW://软键盘显示
                tvSaveInfo.setEnabled(true);
                tvSaveInfo.setSelected(true);
                break;
            default:
                break;
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
        if (!(event.getUpdatePage() == CommonParams.PAGE_MINE_FRAG_TYPE || event.getUpdatePage() == CommonParams.PAGE_WALLET_FRAG_TYPE)) {
            return;
        }

        UserData userInfo = event.getUserData();
        if (userInfo == null) {
            return;
        }
        userData = userInfo;
        updateUserInfoUI();
        selectRoomId = userData.getSDI_Id();

        if (userData.getSDI_Id() == 0) {
            MainManager.getInstance().getSchoolRoom(userId, 0, CommonParams.REQUEST_TYPE_SEAT);
        } else {
            MainManager.getInstance().getSchoolRoom2(selectRoomId);
        }

        PreferencesUtils.putObject(CommonParams.USER_INFO, userInfo);

    }

    private void updateUserInfoUI() {
        if (userData == null) {
            return;
        }
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

        if (EmptyUtil.isEmpty(userData.getSI_Code())) { // 表示未绑定学校
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
            UserManager.getInstance().getUserInfoById(userId, CommonParams.PAGE_MINE_FRAG_TYPE);
        }
        ToastUtil.showShort(responseModel.getMsg());
    }


    @OnClick({R.id.tvGender, R.id.tvBindSchool, R.id.tvSaveInfo, R.id.tvRecharge, R.id.tvWithdraw, R.id.tvMyBill, R.id.llSchool, R.id.tvResetPsw, R.id.tvLoginOut
            , R.id.tvSeat, R.id.tvFloor, R.id.tvRoom})
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
                if (EmptyUtil.isEmpty(guidStr)) {
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
            case R.id.tvSeat://
                tvSaveInfo.setEnabled(false);
                tvSaveInfo.setSelected(false);
                //{"USI_Id":29,"SDI_ParentId":0,"SDI_Type":"幢座"}
                if (selectSeatList == null || selectSeatList.size() == 0) {
                    isClickSeat = true;
                    isClickFloor = false;
                    isClickRoom = false;
                    MainManager.getInstance().getSchoolRoom(userId, 0, CommonParams.REQUEST_TYPE_SEAT);
                    return;
                }
                PickViewUtil.showSelectPickDialog(getActivity(), selectSeatList, 1, new OptionPicker.OnOptionSelectListener() {
                    @Override
                    public void onOptionSelect(OptionPicker picker, int[] selectedPosition, OptionDataSet[] selectedOptions) {

                        SpinnerSelectModel selectedOption = (SpinnerSelectModel) selectedOptions[0];
                        selectSeatId = selectedOption.getId();
                        tvSeat.setText(selectedOption.getName());
                        MainManager.getInstance().getSchoolRoom(userId, selectSeatId, CommonParams.REQUEST_TYPE_FLOOR);

                    }
                });
                break;
            case R.id.tvFloor:
                tvSaveInfo.setEnabled(false);
                tvSaveInfo.setSelected(false);
//                if (selectFloorList == null || selectFloorList.size() == 0) {
//                    ToastUtil.showShort(getString(R.string.have_no_data));
//                    return;
//                }
                if (selectFloorList == null || selectFloorList.size() == 0) {
                    isClickSeat = false;
                    isClickFloor = true;
                    isClickRoom = false;
                    MainManager.getInstance().getSchoolRoom(userId, selectSeatId, CommonParams.REQUEST_TYPE_FLOOR);
                    return;
                }
                PickViewUtil.showSelectPickDialog(getActivity(), selectFloorList, 1, new OptionPicker.OnOptionSelectListener() {
                    @Override
                    public void onOptionSelect(OptionPicker picker, int[] selectedPosition, OptionDataSet[] selectedOptions) {

                        SpinnerSelectModel selectedOption = (SpinnerSelectModel) selectedOptions[0];
                        selectFloorId = selectedOption.getId();
                        tvFloor.setText(selectedOption.getName());
                        MainManager.getInstance().getSchoolRoom(userId, selectFloorId, CommonParams.REQUEST_TYPE_ROOM);
                    }
                });
                break;
            case R.id.tvRoom:
//                if (selectRoomList == null || selectRoomList.size() == 0) {
//                    ToastUtil.showShort(getString(R.string.have_no_data));
//                    return;
//                }
                if (selectRoomList == null || selectRoomList.size() == 0) {
                    isClickSeat = false;
                    isClickFloor = false;
                    isClickRoom = true;
                    MainManager.getInstance().getSchoolRoom(userId, selectFloorId, CommonParams.REQUEST_TYPE_ROOM);
                    return;
                }
                PickViewUtil.showSelectPickDialog(getActivity(), selectRoomList, 1, new OptionPicker.OnOptionSelectListener() {
                    @Override
                    public void onOptionSelect(OptionPicker picker, int[] selectedPosition, OptionDataSet[] selectedOptions) {
                        SpinnerSelectModel selectedOption = (SpinnerSelectModel) selectedOptions[0];
                        tvSaveInfo.setEnabled(true);
                        tvSaveInfo.setSelected(true);
                        selectRoomId = selectedOption.getId();
                        tvRoom.setText(selectedOption.getName());
                    }
                });

                break;
        }
    }

    private long selectSeatId;
    private long selectFloorId;
    private long selectRoomId;


    private List<SpinnerSelectModel> selectSeatList = new ArrayList<>();
    private List<SpinnerSelectModel> selectFloorList = new ArrayList<>();
    private List<SpinnerSelectModel> selectRoomList = new ArrayList<>();

    /**
     * 处理宿舍地址返回的结果信息
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRoomInfoEvent(RoomInfoEvent event) {
        RoomInfoModel infoModel = event.getInfoModel();
        String requestType = event.getRequestType();

        if (infoModel == null) {
            ToastUtil.showShort(getString(R.string.get_net_data_error));
            return;
        }
        List<RoomInfoModel.DataModel> data = infoModel.getData();
        if (data == null || data.size() == 0) {
            ToastUtil.showShort(getString(R.string.have_no_data));
            return;
        }

        switch (requestType) {
            case CommonParams.REQUEST_TYPE_SEAT:
                selectSeatList.clear();
                for (int i = 0; i < data.size(); i++) {
                    SpinnerSelectModel selectModel = new SpinnerSelectModel(data.get(i).getSDI_Id(), data.get(i).getSDI_Name());
                    selectSeatList.add(selectModel);
                    tvSeat.setText(data.get(i).getSDI_Name());
                }
                break;
            case CommonParams.REQUEST_TYPE_FLOOR:
                selectFloorList.clear();
                for (int i = 0; i < data.size(); i++) {
                    SpinnerSelectModel selectModel = new SpinnerSelectModel(data.get(i).getSDI_Id(), data.get(i).getSDI_Name());
                    selectFloorList.add(selectModel);
                }
                break;
            case CommonParams.REQUEST_TYPE_ROOM:
                selectRoomList.clear();
                for (int i = 0; i < data.size(); i++) {
                    SpinnerSelectModel selectModel = new SpinnerSelectModel(data.get(i).getSDI_Id(), data.get(i).getSDI_Name());
                    selectRoomList.add(selectModel);
                }
                break;
        }


        if (isClickSeat) {
            PickViewUtil.showSelectPickDialog(getActivity(), selectSeatList, 1, new OptionPicker.OnOptionSelectListener() {
                @Override
                public void onOptionSelect(OptionPicker picker, int[] selectedPosition, OptionDataSet[] selectedOptions) {

                    SpinnerSelectModel selectedOption = (SpinnerSelectModel) selectedOptions[0];
                    selectSeatId = selectedOption.getId();
                    tvSeat.setText(selectedOption.getName());
                    MainManager.getInstance().getSchoolRoom(userId, selectSeatId, CommonParams.REQUEST_TYPE_FLOOR);

                }
            });
        } else if (isClickFloor) {
            PickViewUtil.showSelectPickDialog(getActivity(), selectFloorList, 1, new OptionPicker.OnOptionSelectListener() {
                @Override
                public void onOptionSelect(OptionPicker picker, int[] selectedPosition, OptionDataSet[] selectedOptions) {

                    SpinnerSelectModel selectedOption = (SpinnerSelectModel) selectedOptions[0];
                    selectFloorId = selectedOption.getId();
                    tvFloor.setText(selectedOption.getName());
                    MainManager.getInstance().getSchoolRoom(userId, selectFloorId, CommonParams.REQUEST_TYPE_ROOM);
                }
            });
        } else if (isClickRoom) {
            PickViewUtil.showSelectPickDialog(getActivity(), selectRoomList, 1, new OptionPicker.OnOptionSelectListener() {
                @Override
                public void onOptionSelect(OptionPicker picker, int[] selectedPosition, OptionDataSet[] selectedOptions) {
                    SpinnerSelectModel selectedOption = (SpinnerSelectModel) selectedOptions[0];
                    tvSaveInfo.setEnabled(true);
                    tvSaveInfo.setSelected(true);
                    selectRoomId = selectedOption.getId();
                    tvRoom.setText(selectedOption.getName());
                }
            });

        }
        isClickSeat = false;
        isClickFloor = false;
        isClickRoom = false;

    }

    /**
     * 处理宿舍地址返回的结果信息
     *
     * @param event2
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRoomInfoEvent2(RoomInfoEvent2 event2) {
        RoomInfoModel infoModel = event2.getInfoModel();

        List<RoomInfoModel.DataModel> data = infoModel.getData();
        if (data == null || data.size() == 0) {
            return;
        }
        for (int i = 0; i < data.size(); i++) {
            String sdi_type = data.get(i).getSDI_Type();

            if (sdi_type.equals("宿舍")) {
                tvRoom.setText(data.get(i).getSDI_Name());
                selectRoomId = data.get(i).getSDI_Id();
            } else if (sdi_type.equals("楼层")) {
                tvFloor.setText(data.get(i).getSDI_Name());
                selectFloorId = data.get(i).getSDI_Id();
            } else {
                tvSeat.setText(data.get(i).getSDI_Name());
                selectSeatId = data.get(i).getSDI_Id();
            }

        }

    }


    private String guidStr;
    private final int REQUEST_CODE = 101;
    public static final String QR_DATA = "data";
    public static final int RESULT_CODE = 200;

    /**
     * 处理二维码扫描结果
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode != REQUEST_CODE && resultCode != RESULT_CODE) || data == null) {
            return;
        }
        Bundle bundle = data.getExtras();
        if (bundle == null) {
            return;
        }

        String result = bundle.getString(QR_DATA);
        Log.d("admin", "onActivityResult: result=" + result);
        if (result != null) {
            guidStr = result;
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

    /**
     * 提交个人信息
     */
    private void submitPersonInfo() {
        long userId = PreferencesUtils.getLong(CommonParams.LOGIN_USER_ID);
        if (userId == 0) {
            ToastUtil.showShort("用户信息出错，请重新登录");
            return;
        }
        String idCard = edtIdCard.getText().toString();
//        if (!EmptyUtil.isEmpty(idCard)) {
//            if (!DataUtils.isValidIdNo(idCard)) {
//                ToastUtil.showShort("身份证格式错误，请重新输入");
//                return;
//            }
//        }
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
        userData.setSelectSeatId(selectSeatId);
        userData.setSelectFloorId(selectFloorId);
        userData.setSDI_Id(selectRoomId);
        MainManager.getInstance().savePersonInfo(userData);

    }

}
