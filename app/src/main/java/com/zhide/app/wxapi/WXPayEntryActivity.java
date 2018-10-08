package com.zhide.app.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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
import com.zhide.app.utils.PreferencesUtils;
import com.zhide.app.view.base.BaseActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

public class WXPayEntryActivity extends BaseActivity implements IWXAPIEventHandler {

    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

    private IWXAPI api;
    @BindView(R.id.tvResult)
    TextView tvResult;
    @BindView(R.id.ivIcon)
    ImageView ivIcon;
    @BindView(R.id.tvPayAmount)
    TextView tvPayAmount;
    @BindView(R.id.tvGoBack)
    TextView tvGoBack;


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
        Log.d("admin", "onCreate: api=" + api);
        tvGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
                    ivIcon.setImageResource(R.mipmap.pay_failed);
                    tvResult.setText(getString(R.string.pay_failed));
                    if (resp.errStr == null) {
                        resp.errStr = "空";
                    }
                    tvPayAmount.setText(getString(R.string.failed_reson_tip) + resp.errStr);

                    break;
                case 0:
                    ivIcon.setImageResource(R.mipmap.pay_success);
                    tvResult.setText(getString(R.string.pay_success));
                    float selectAmount = PreferencesUtils.getFloat("selectAmount");
                    tvPayAmount.setText(selectAmount+getString(R.string.yuan));
                    break;
                case -2:
                    ivIcon.setImageResource(R.mipmap.pay_failed);
                    tvResult.setText(getString(R.string.pay_cancel));
                    if (resp.errStr == null) {
                        resp.errStr = "空";
                    }
                    tvPayAmount.setText(getString(R.string.failed_reson_tip)+ resp.errStr);
                    break;
            }
            EventBus.getDefault().post(new PayResultEvent(resp.errCode));
        }
    }
}