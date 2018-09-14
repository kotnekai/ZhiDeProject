package com.zhide.app.view.activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.jooronjar.BluetoothService;
import com.example.jooronjar.utils.AnalyAdminTools;
import com.example.jooronjar.utils.CMDUtils;
import com.example.jooronjar.utils.OnWaterAdminListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhide.app.R;
import com.zhide.app.utils.ToastUtil;
import com.zhide.app.view.base.BaseActivity;

import java.io.IOException;
import java.util.Random;

import butterknife.BindView;


/**
 * Create by Admin on 2018/08/15
 *
 * @author Admin
 */
public class ShowerAdminActivity extends BaseActivity implements OnWaterAdminListener, View.OnClickListener, RadioGroup.OnCheckedChangeListener {


    public static final String DEVICE_MAC = "deviceMac";

    String MAC = "";
    private int bigType;
    private int littleType;
    private int mSize;
    private Context mContext;

    private BroadcastReceiver mStatusReceive;
    private BluetoothService mbtService = null;
    private BluetoothAdapter bluetoothAdapter;

    @BindView(R.id.tvSelectType)
    TextView tvSelectType;
    @BindView(R.id.radioWater)
    RadioGroup radioWater;
    @BindView(R.id.radioWashing)
    RadioGroup radioWashing;
    @BindView(R.id.llWater)
    LinearLayout llWater;
    @BindView(R.id.llWashing)
    LinearLayout llWashing;
    @BindView(R.id.btnWater)
    Button btnWater;
    @BindView(R.id.btnWashing)
    Button btnWashing;


    @Override
    protected int getCenterView() {
        return R.layout.activity_shower_admin;
    }

    @Override
    protected SmartRefreshLayout getRefreshView() {
        return null;
    }

    @Override
    protected void initHeader() {
        setHeaderTitle(getString(R.string.shower_title));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = ShowerAdminActivity.this;
        initData();
        initBluetooth();
    }

    /**
     * 加载数据
     */
    private void initData() {
        Intent in = getIntent();
        MAC = in.getStringExtra(DEVICE_MAC);
        Log.d(mContext.getClass().getSimpleName(), "设备的mac地址：=" + MAC);

        radioWater.setOnCheckedChangeListener(this);
        radioWashing.setOnCheckedChangeListener(this);
        //设备登记
        btnWater.setOnClickListener(this);
        btnWashing.setOnClickListener(this);
    }

    /**
     * 初始化蓝牙
     */
    private void initBluetooth() {
        AnalyAdminTools.setWaterAdminListener(this);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mbtService = BluetoothService.sharedManager();
        mbtService.setHandlerContext(mHandler);

        IntentFilter statusFilter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        // 状态改变
        statusFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);

        //蓝牙设备状态监听
        mStatusReceive = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                if (intent.getAction().equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                    int blueState = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, 0);
                    switch (blueState) {
                        case BluetoothAdapter.STATE_TURNING_ON:
                            break;
                        case BluetoothAdapter.STATE_ON:

                            break;
                        case BluetoothAdapter.STATE_TURNING_OFF:
                            break;
                        case BluetoothAdapter.STATE_OFF:

                    }
                } else if (intent.getAction().equals(BluetoothDevice.ACTION_BOND_STATE_CHANGED)) {
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    switch (device.getBondState()) {
                        case BluetoothDevice.BOND_BONDING:
                            // 正在配对
                            Log.d(mContext.getClass().getSimpleName(), getString(R.string.driver_bluetooth_connecting));
                            break;
                        case BluetoothDevice.BOND_BONDED:
                            // 配对结束
                            Log.d(mContext.getClass().getSimpleName(), getString(R.string.driver_bluetooth_connect_finish));
                            mbtService.connect(device);

                            break;
                        case BluetoothDevice.BOND_NONE:
                            // 取消配对/未配对
                            Log.d(mContext.getClass().getSimpleName(), getString(R.string.driver_bluetooth_connect_cancel));
                            break;
                        default:
                            break;
                    }

                }

            }
        };
        //注册广播
        registerReceiver(mStatusReceive, statusFilter);

        if (mbtService.getState() == BluetoothService.STATE_CONNECTED) {
            CMDUtils.chaxueshebei(mbtService, true);
        } else {
            mbtService.connect(bluetoothAdapter.getRemoteDevice(MAC));
        }
    }


    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkId) {
        switch (checkId) {
            case R.id.radioButton1:
                //水表
                bigType = 0;
                littleType = 1;
                break;
            case R.id.radioButton2:
                //饮水机
                bigType = 1;
                littleType = 2;
                break;
            case R.id.radioButton3:
                //吹风机
                bigType = 3;
                littleType = 47;
                break;
            case R.id.radioButton5:
                //室内洗衣机
                bigType = 2;
                littleType = 46;
                break;
            case R.id.radioButton6:
                //走廊洗衣机
                bigType = 2;
                littleType = 45;
                break;

        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnWater) {
            connectDevice();
        } else if (view.getId() == R.id.btnWashing) {
            connectDevice();
        }
    }

    /**
     * 连接设备
     */
    private void connectDevice() {
        if (mbtService.getState() == BluetoothService.STATE_CONNECTED) {

            CMDUtils.qingchushebei(mbtService, true);
        } else {
            mbtService.connect(bluetoothAdapter.getRemoteDevice(MAC));
        }
    }

    /**
     * 转到水表连接
     */
    private void skipUI() {
        Intent intent = new Intent(ShowerAdminActivity.this, ShowerConnectActivity.class);
        intent.putExtra(DEVICE_MAC, MAC);
        startActivity(intent);
        finish();
    }


    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                //下发
                case BluetoothService.MESSAGE_NO_XIAFACALLBACK:
                    break;
                case BluetoothService.MESSAGE_NO_JIESHUCALLBACK:
                    //结束费率
                    break;
                case BluetoothService.MESSAGE_STATE_NOTHING:
                    //状态通知
                    break;
                case BluetoothService.MESSAGE_STATE_CHANGE:
                    //状态变更
                    switch (msg.arg1) {
                        case BluetoothService.STATE_CONNECTED:
                            //连上
                            ToastUtil.showShort(getString(R.string.driver_connect_success));
                            CMDUtils.chaxueshebei(mbtService, true);
                            break;
                        case BluetoothService.STATE_CONNECTING:
                            //连接中

                            break;
                        case BluetoothService.STATE_LISTEN:
                            break;
                        case BluetoothService.STATE_CONNECTION_LOST:
                            //失连
                            // break;
                        case BluetoothService.STATE_CONNECTION_FAIL: {
                            //连接失败
                        }
                        break;
                        case BluetoothService.STATE_NONE:
                            break;
                    }
                    break;
                case BluetoothService.MESSAGE_WRITE:
                    break;
                case BluetoothService.MESSAGE_READ:
                    byte[] readBuf = (byte[]) msg.obj;

                    //从蓝牙水表返回的数据
                    AnalyAdminTools.analyWaterDatas(readBuf);
                    break;
                case 101:
                    ToastUtil.showShort("成功");
                    finish();
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mbtService != null) {
            if (mbtService.getState() == BluetoothService.STATE_CONNECTED) {
                mbtService.stop();
            }
        }
        Log.d(mContext.getClass().getSimpleName(), "onDestroy");
        unregisterReceiver(mStatusReceive);
    }



    @Override
    public void settingDeciveType(boolean b) {
        if (b) {
            ToastUtil.showShort(getString(R.string.device_login_success));
            skipUI();
        }

    }

    @Override
    public void qingchushebeiOnback(boolean b, int mproductid, int mdeviceid) {
        if (b) {
            int num = new Random().nextInt(1000000);
            try {
                CMDUtils.xiafaxiangmu(mbtService, num, 1, true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void xiafaxiangmuOnback(boolean b, int mproductids, int mdeviceids) {
        if (b) {
            if (mSize == 28) {
                if (bigType == 0 || littleType == 0) {
                    ToastUtil.showShort(getString(R.string.device_login_select_type));
                    return;
                }
                try {
                    CMDUtils.settingDecive(mbtService, 1, bigType, littleType, true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                ToastUtil.showShort(getString(R.string.device_login_success));
                skipUI();
            }
        }
    }

    @Override
    public void chaxueshebeiOnback(boolean b, int size, int macType) {
        Log.d(mContext.getClass().getSimpleName(), "size:" + size);
        Log.d(mContext.getClass().getSimpleName(), "macType:" + macType);

        mSize = size;
        if (mSize == 28) {
            tvSelectType.setText("设备预设置的类型：=" + macType);
            if (macType == 2) {
                llWater.setVisibility(View.GONE);
                llWashing.setVisibility(View.VISIBLE);
            } else {
                llWater.setVisibility(View.VISIBLE);
                llWashing.setVisibility(View.GONE);
            }
        } else if (mSize == 23) {
            llWater.setVisibility(View.VISIBLE);
            radioWater.setVisibility(View.VISIBLE);
            llWashing.setVisibility(View.GONE);
            tvSelectType.setText("");
        }
    }
}
