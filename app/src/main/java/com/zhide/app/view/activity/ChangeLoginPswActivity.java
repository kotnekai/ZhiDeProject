package com.zhide.app.view.activity;

import android.content.Context;
import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhide.app.R;
import com.zhide.app.common.CommonParams;
import com.zhide.app.eventBus.OkResponseEvent;
import com.zhide.app.logic.UserManager;
import com.zhide.app.model.ResponseModel;
import com.zhide.app.utils.PreferencesUtils;
import com.zhide.app.utils.ToastUtil;
import com.zhide.app.view.base.BaseActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

public class ChangeLoginPswActivity extends BaseActivity {

    @BindView(R.id.edtOldPsw)
    EditText edtOldPsw;
    @BindView(R.id.edtNewPsw)
    EditText edtNewPsw;
    @BindView(R.id.edtConfirmPsw)
    EditText edtConfirmPsw;
    @BindView(R.id.tvSavePsw)
    TextView tvSavePsw;

    @Override
    protected int getCenterView() {
        return R.layout.activity_change_login_psw;
    }

    @Override
    protected SmartRefreshLayout getRefreshView() {
        return null;
    }

    @Override
    protected void initHeader() {
        setHeaderTitle(getString(R.string.change_psw_title));
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, ChangeLoginPswActivity.class);
    }

    @OnClick({R.id.tvSavePsw})
    public void submitPsw() {
        String oldPsw = edtOldPsw.getText().toString();
        String newPsw = edtNewPsw.getText().toString();
        String confirmPsw = edtConfirmPsw.getText().toString();
        long userId = PreferencesUtils.getLong(CommonParams.LOGIN_USER_ID);

        if (oldPsw.isEmpty()) {
            ToastUtil.showShort(getString(R.string.input_old_psw));
            return;
        }
        if (newPsw.isEmpty()) {
            ToastUtil.showShort(getString(R.string.input_new_psw));
            return;
        }
        if (!newPsw.equals(confirmPsw)) {
            ToastUtil.showShort(getString(R.string.no_equal_psw));
            return;
        }
        UserManager.getInstance().resetLoginPsw(userId, newPsw, oldPsw);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onOkResponseEvent(OkResponseEvent event) {
        ResponseModel responseModel = event.getResponseModel();
        if (responseModel == null) {
            ToastUtil.showShort(getString(R.string.get_net_data_error));
            return;
        }
        ToastUtil.showShort(responseModel.getMsg());
        int code = responseModel.getCode();
        if (code == 1) {
            finish();
        }
    }
}
