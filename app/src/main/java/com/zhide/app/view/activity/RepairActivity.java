package com.zhide.app.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhide.app.R;
import com.zhide.app.common.CommonParams;
import com.zhide.app.eventBus.BreakdownEvent;
import com.zhide.app.eventBus.OkResponseEvent;
import com.zhide.app.logic.MainManager;
import com.zhide.app.model.BreakdownDeviceModel;
import com.zhide.app.model.BreakdownModel;
import com.zhide.app.model.ResponseModel;
import com.zhide.app.model.SpinnerSelectModel;
import com.zhide.app.model.UserData;
import com.zhide.app.utils.EmptyUtil;
import com.zhide.app.utils.PickViewUtil;
import com.zhide.app.utils.PreferencesUtils;
import com.zhide.app.utils.ProgressUtils;
import com.zhide.app.utils.ToastUtil;
import com.zhide.app.view.base.BaseActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jaaksi.pickerview.dataset.OptionDataSet;
import org.jaaksi.pickerview.picker.OptionPicker;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class RepairActivity extends BaseActivity {

    @BindView(R.id.rlSelectDevice)
    RelativeLayout rlSelectDevice;

    @BindView(R.id.rlSelectType)
    RelativeLayout rlSelectType;

    @BindView(R.id.tvSelectDevice)
    TextView tvSelectDevice;
    @BindView(R.id.tvSelectFault)
    TextView tvSelectFault;
    @BindView(R.id.tvRoomNumber)
    TextView tvRoomNumber;
    @BindView(R.id.edtContent)
    EditText edtContent;
    @BindView(R.id.tvSubmit)
    TextView tvSubmit;
    private List<SpinnerSelectModel> repairDeviceList = new ArrayList<>();
    private List<SpinnerSelectModel> repairReasonList = new ArrayList<>();
    private List<BreakdownDeviceModel> deviceData;
    private UserData userData;

    @Override
    protected int getCenterView() {
        return R.layout.activity_repair;
    }

    @Override
    protected SmartRefreshLayout getRefreshView() {
        return null;
    }

    @Override
    protected void initHeader() {
        setHeaderTitle(getString(R.string.repair_title));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    private void initData() {
        MainManager.getInstance().getBreakdownType();
        userData = PreferencesUtils.getObject(CommonParams.USER_INFO);

    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, RepairActivity.class);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onBreakdownEvent(BreakdownEvent event) {
        ProgressUtils.getIntance().dismissProgress();
        BreakdownModel breakdownModel = event.getBreakdownModel();
        if (breakdownModel == null) {
            return;
        }
        deviceData = breakdownModel.getData();
        if (deviceData == null) {
            return;
        }
        for (int i = 0; i < deviceData.size(); i++) {
            repairDeviceList.add(new SpinnerSelectModel(i, deviceData.get(i).getZBI_DeviceType()));
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onOkResponseEvent(OkResponseEvent event) {
        ResponseModel responseModel = event.getResponseModel();
        if (responseModel == null) {
            return;
        }
        ToastUtil.showShort(responseModel.getMsg());
        if (responseModel.getCode() == 1) {
            finish();
        }
    }

    @OnClick({R.id.rlSelectDevice, R.id.rlSelectType, R.id.tvSubmit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rlSelectDevice:
                if (repairDeviceList == null || repairDeviceList.size() == 0) {
                    return;
                }
                PickViewUtil.showSelectPickDialog(this, repairDeviceList, 1, new OptionPicker.OnOptionSelectListener() {
                    @Override
                    public void onOptionSelect(OptionPicker picker, int[] selectedPosition, OptionDataSet[] selectedOptions) {
                        SpinnerSelectModel spinnerItem = (SpinnerSelectModel) selectedOptions[0];
                        List<BreakdownDeviceModel.BreakType> zbiTypes = deviceData.get(selectedPosition[0]).getZBI_Types();
                        for (int j = 0; j < zbiTypes.size(); j++) {
                            repairReasonList.add(new SpinnerSelectModel(j, zbiTypes.get(j).getZBI_Type()));
                        }
                        selectDevice = spinnerItem.getName();
                        tvSelectDevice.setText(selectDevice);
                    }
                });
                break;
            case R.id.rlSelectType:
                if (repairReasonList == null || repairReasonList.size() == 0) {
                    ToastUtil.showShort(getString(R.string.select_fault_device_tip));
                    return;
                }
                PickViewUtil.showSelectPickDialog(this, repairReasonList, 1, new OptionPicker.OnOptionSelectListener() {
                    @Override
                    public void onOptionSelect(OptionPicker picker, int[] selectedPosition, OptionDataSet[] selectedOptions) {
                        SpinnerSelectModel spinnerItem = (SpinnerSelectModel) selectedOptions[0];
                        reasonType = spinnerItem.getName();
                        tvSelectFault.setText(reasonType);
                    }
                });
                break;
            case R.id.tvSubmit:
                boolean completeInfo = EmptyUtil.isCompleteInfo(userData);
                if (!completeInfo) {
                    ToastUtil.showShort(getString(R.string.complete_info_tip));
                    return;
                }
                submitInfo();
                break;
        }
    }

    private String selectDevice;
    private String reasonType;

    private void submitInfo() {
        long userId = PreferencesUtils.getLong(CommonParams.LOGIN_USER_ID);
        String content = edtContent.getText().toString();
        ProgressUtils.getIntance().setProgressDialog(getString(R.string.submit_ing), this);
        MainManager.getInstance().submitBreakInfo(selectDevice, reasonType, userId, content);
    }
}
