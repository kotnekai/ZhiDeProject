package com.zhide.app.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhide.app.R;
import com.zhide.app.model.SpinnerSelectModel;
import com.zhide.app.utils.InitUtils;
import com.zhide.app.utils.PickViewUtil;
import com.zhide.app.utils.ToastUtil;
import com.zhide.app.view.base.BaseActivity;

import org.jaaksi.pickerview.dataset.OptionDataSet;
import org.jaaksi.pickerview.picker.OptionPicker;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class RepairActivity extends BaseActivity {

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

    }

    public static Intent makeIntent(Context context) {
        return new Intent(context,RepairActivity.class);
    }

    @OnClick({R.id.tvSelectDevice,R.id.tvSelectFault,R.id.tvSubmit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvSelectDevice:
                List<SpinnerSelectModel> repairDeviceList = InitUtils.getRepairDeviceList();
                PickViewUtil.showSelectPickDialog(this, repairDeviceList, 1, new OptionPicker.OnOptionSelectListener() {
                    @Override
                    public void onOptionSelect(OptionPicker picker, int[] selectedPosition, OptionDataSet[] selectedOptions) {
                        SpinnerSelectModel spinnerItem = (SpinnerSelectModel) selectedOptions[0];
                        tvSelectDevice.setText(spinnerItem.getName());
                    }
                });
                break;
            case R.id.tvSelectFault:
                List<SpinnerSelectModel> repairReasonList = InitUtils.getRepairReasonList();
                PickViewUtil.showSelectPickDialog(this, repairReasonList, 1, new OptionPicker.OnOptionSelectListener() {
                    @Override
                    public void onOptionSelect(OptionPicker picker, int[] selectedPosition, OptionDataSet[] selectedOptions) {
                        SpinnerSelectModel spinnerItem = (SpinnerSelectModel) selectedOptions[0];
                        tvSelectFault.setText(spinnerItem.getName());
                    }
                });
                break;
            case R.id.tvSubmit:
                ToastUtil.showShort("提交成功");
                break;
        }
    }
}
