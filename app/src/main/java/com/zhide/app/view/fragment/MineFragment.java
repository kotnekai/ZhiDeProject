package com.zhide.app.view.fragment;

import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhide.app.R;
import com.zhide.app.eventBus.MineAccountEvent;
import com.zhide.app.model.AccountInfoModel;
import com.zhide.app.utils.DialogUtils;
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

public class MineFragment extends BaseFragment {

    @BindView(R.id.tvRecharge)
    TextView tvRecharge;
    @BindView(R.id.tvWithdraw)
    TextView tvWithdraw;
    @BindView(R.id.tvMoneyTip)
    TextView tvMoneyTip;
    @BindView(R.id.tvMyBill)
    TextView tvMyBill;
    @BindView(R.id.tvUserName)
    TextView tvUserName;
    @BindView(R.id.llSchool)
    LinearLayout llSchool;
    @BindView(R.id.tvSchoolName)
    TextView tvSchoolName;
    @BindView(R.id.checkMan)
    CheckBox checkMan;
    @BindView(R.id.checkWoman)
    CheckBox checkWoman;
    @BindView(R.id.tvStuId)
    TextView tvStuId;
    @BindView(R.id.tvIdCard)
    TextView tvIdCard;
    @BindView(R.id.tvResetPsw)
    TextView tvResetPsw;
    @BindView(R.id.tvLoginOut)
    TextView tvLoginOut;

    @Override
    protected void initData() {

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
        checkMan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ToastUtil.showShort(isChecked + "");
            }
        });
        checkWoman.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ToastUtil.showShort(isChecked + "");

            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMineAccountEvent(MineAccountEvent event) {
        AccountInfoModel infoModel = event.getInfoModel();
        if (infoModel == null) {
            ToastUtil.showShort(getString(R.string.get_net_data_error));
            return;
        }
        updateUI(infoModel);
    }

    private void updateUI(AccountInfoModel infoModel) {
        tvMoneyTip.setText("账户余额：" + infoModel.getAccountBalance());
        tvUserName.setText(infoModel.getUserName());
        tvSchoolName.setText(infoModel.getSchoolName());
        if (infoModel.isMan()) {
            checkMan.setChecked(true);
        } else {
            checkWoman.setChecked(false);
        }
        tvStuId.setText(infoModel.getStudentId());
        tvIdCard.setText(infoModel.getIdCardNumber());
    }


    @OnClick({R.id.tvRecharge, R.id.tvWithdraw, R.id.tvMyBill, R.id.llSchool, R.id.tvResetPsw, R.id.tvLoginOut})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvRecharge:
                startActivity(RechargeActivity.makeIntent(getActivity()));
                break;
            case R.id.tvWithdraw:
                startActivity(WithdrawActivity.makeIntent(getActivity()));
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
                    }
                });
                break;
        }
    }
}
