package com.zhide.app.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zhide.app.R;
import com.zhide.app.common.CommonParams;
import com.zhide.app.eventBus.PayResultEvent;
import com.zhide.app.utils.ToastUtil;
import com.zhide.app.view.base.BaseActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

public class WXPayEntryActivity extends BaseActivity implements IWXAPIEventHandler {

    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

    private IWXAPI api;
    @BindView(R.id.tvResult)
    TextView tvResult;

    @Override
    protected int getCenterView() {
        return R.layout.pay_result;
    }

    @Override
    protected SmartRefreshLayout getRefreshView() {
        return null;
    }

    @Override
    protected void initHeader() {
        setHeaderTitle("支付结果");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, CommonParams.WECHAT_APPID);
      //  api = ApplicationHolder.getInstance().getMsgApi();
        api.handleIntent(getIntent(), this);
        Log.d("admin", "onCreate: api="+api);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {

        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            switch (resp.errCode) {
                case -1:
                    tvResult.setText(getString(R.string.pay_failed));
                    ToastUtil.showShort(getString(R.string.pay_failed));
                    break;
                case 0:
                    ToastUtil.showShort(getString(R.string.pay_success));
                    tvResult.setText(getString(R.string.pay_success));

                    break;
                case -2:
                    tvResult.setText(getString(R.string.pay_cancel));
                    ToastUtil.showShort(getString(R.string.pay_cancel));
                    break;
            }
            EventBus.getDefault().post(new PayResultEvent(resp.errCode));
        }
    }
}