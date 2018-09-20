package com.zhide.app.view.activity;

import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.jooronjar.BluetoothService;
import com.example.jooronjar.DealRateInfo;
import com.example.jooronjar.DownRateInfo;
import com.example.jooronjar.utils.AnalyTools;
import com.example.jooronjar.utils.CMDUtils;
import com.example.jooronjar.utils.DigitalTrans;
import com.example.jooronjar.utils.WaterCodeListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhide.app.R;
import com.zhide.app.common.CommonParams;
import com.zhide.app.eventBus.LoginEvent;
import com.zhide.app.eventBus.WaterPreBillEvent;
import com.zhide.app.logic.ChargeManager;
import com.zhide.app.utils.DateUtils;
import com.zhide.app.utils.DialogUtils;
import com.zhide.app.utils.PreferencesUtils;
import com.zhide.app.utils.ToastUtil;
import com.zhide.app.view.base.BaseActivity;
import com.zhide.app.view.views.CircleProgressBar;
import com.zhide.app.view.widget.TimeView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.lang.ref.WeakReference;

import butterknife.BindView;

/**
 * Create by Admin on 2018/08/15
 *
 * @author Admin
 */
public class ShowerConnectActivity extends BaseActivity implements WaterCodeListener, View.OnClickListener {

    public static final String DEVICE_MAC = "deviceMac";
    public static final int MSG_AUTO_CONNECT_COMPLETED = 0x09;

    private BluetoothService mbtService = null;
    private BluetoothAdapter bluetoothAdapter;

    String MAC = "";
    private int mStatus = 0;
    private int num = 1;
    private Context mContext;
    private boolean isDestory = false;
    private Handler myHandle;
    private Dialog xiaofeiDialog;
    private BroadcastReceiver mStatusReceive;

    private int diff = 30000;
    private int days;
    private int hours;
    private int minutes;
    private int seconds;
    private long curTime;
    private boolean isStart = false;
    AnimationDrawable animationDrawable;


    @BindView(R.id.washing_time)
    TimeView timeView;
    @BindView(R.id.ivShower)
    ImageView ivShower;
    @BindView(R.id.ivDeviceState)
    ImageView ivDeviceState;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;
    @BindView(R.id.tvState)
    TextView device_connect_state_txt;
    @BindView(R.id.circleProgressBar)
    CircleProgressBar circleProgressBar;


    @Override
    protected int getCenterView() {
        return R.layout.activity_shower_connect;
    }

    @Override
    protected SmartRefreshLayout getRefreshView() {
        return null;
    }

    @Override
    protected void initHeader() {
        setHeaderTitle(getString(R.string.shower_title));
//        setRightTextVisibility(View.VISIBLE);
//        setHeader_RightText(getString(R.string.shower_driver_reg));
//        setHeader_RightTextClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = ShowerConnectActivity.this;
        initData();
        //蓝牙监听
        initBluetooth();
        //连接设备
        connDevice();
        updateUI();
    }

    /**
     * 加载数据
     */
    private void initData() {
        Intent intent = getIntent();
        MAC = intent.getStringExtra(DEVICE_MAC);
        Log.d(mContext.getClass().getSimpleName(), "设备的mac地址：=" + MAC);

        ivDeviceState.setOnClickListener(this);

//        ivDeviceState.setOnTouchListener(this);
        myHandler = new MyHandler(ShowerConnectActivity.this);

        circleProgressBar.setSweepAngle(0);
        circleProgressBar.setText("0");
        circleProgressBar.setVisibility(View.INVISIBLE);
        animationDrawable = (AnimationDrawable) ivShower.getDrawable();

    }

    /**
     * 初始化蓝牙
     */
    private void initBluetooth() {
        AnalyTools.setWaterCodeLisnter(this);

        mbtService = BluetoothService.sharedManager();
        mbtService.setHandlerContext(mHandler);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        IntentFilter statusFilter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        //状态改变
        statusFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
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
                            break;
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
                            ToastUtil.showShort(getString(R.string.driver_bluetooth_connect_fail));
                            mStatus = 41;
                            //配对失败
                            break;
                        default:
                            break;
                    }

                }

            }
        };
        registerReceiver(mStatusReceive, statusFilter);
    }

    /**
     * 连续设备
     */
    private void connDevice() {
        if (mbtService.getState() == BluetoothService.STATE_CONNECTED) {
            CMDUtils.chaxueshebei(mbtService, true);
        } else {
            //取消扫描
            if (bluetoothAdapter.isDiscovering()) {
                bluetoothAdapter.cancelDiscovery();
                Log.d(mContext.getClass().getSimpleName(), "bluetoothAdapter.isDiscovering():" + bluetoothAdapter.isDiscovering());
            }
            mbtService.connect(bluetoothAdapter.getRemoteDevice(MAC));
        }
    }

    /**
     * 设备登记
     */
    private void skipUI() {
        Intent intent = new Intent(mContext, ShowerAdminActivity.class);
        intent.putExtra(DEVICE_MAC, MAC);
        startActivity(intent);
        finish();
    }

    /**
     * 更新UI
     */
    private void updateUI() {
        AnimationDrawable animationDrawable;
        switch (mStatus) {
            case 0:
                //正在连接
                showProgress();
                break;
            case 31:
                //连接成功
                hideProgress();
                ivDeviceState.setImageResource(R.mipmap.start);
                device_connect_state_txt.setText(getString(R.string.driver_start_using));
                break;
            case 32:
                //下发费率成功
                hideProgress();
                ivShower.setImageResource(R.drawable.animation_shower);
                animationDrawable = (AnimationDrawable) ivShower.getDrawable();
                animationDrawable.start();
                ivDeviceState.setImageResource(R.mipmap.stop);
                device_connect_state_txt.setText(getString(R.string.driver_stop_use));
                break;
            case 33:
                //洗衣机的连接成功
                hideProgress();
                ivDeviceState.setImageResource(R.mipmap.dryer_connected);
                device_connect_state_txt.setText(getString(R.string.driver_start_using));
                break;
            case 34:
                break;
            case 35:
                //未登记

                break;
            case 36:
                //洗衣机开始交易
                ivDeviceState.setImageResource(R.drawable.animation_washing);
                animationDrawable = (AnimationDrawable) ivDeviceState.getDrawable();
                animationDrawable.start();
                device_connect_state_txt.setText(getString(R.string.driver_wash_clothes));
                hideProgress();
                timeView.setVisibility(View.VISIBLE);
                timeView.setTime(times);
                break;
            case 41:
                //配对失败
                hideProgress();
                device_connect_state_txt.setText(getString(R.string.driver_click_restart));
                break;
            case 42:
                //连接失败
                hideProgress();
                device_connect_state_txt.setText(getString(R.string.driver_click_restart));
                break;
            case 43:
                //断开连接
                ivDeviceState.setImageResource(R.mipmap.dryer_unconnected);
                device_connect_state_txt.setText(getString(R.string.driver_click_reconnect));
                timeView.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    /**
     * 显示进度
     */
    private void showProgress() {
//        progressBar.setVisibility(View.VISIBLE);
//        ivDeviceState.setEnabled(false);
        progressBar.setVisibility(View.GONE);
        ivDeviceState.setEnabled(true);
    }

    /**
     * 隐藏进度
     */
    private void hideProgress() {
        progressBar.setVisibility(View.GONE);
        ivDeviceState.setEnabled(true);
    }


    private int i = 0;
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case BluetoothService.MESSAGE_NO_XIAFACALLBACK:
                    //下发
                    break;

                case BluetoothService.MESSAGE_NO_JIESHUCALLBACK:
                    //结束费率
                    break;

                case BluetoothService.MESSAGE_STATE_NOTHING:
                    //状态通知
                    if (mbtService.getState() == BluetoothService.STATE_CONNECTED) {
                        mbtService.stop();
                    }
                    break;

                case BluetoothService.MESSAGE_STATE_CHANGE:
                    //状态变更
                    switch (msg.arg1) {
                        case BluetoothService.STATE_CONNECTED:
                            //连上
                            ToastUtil.showShort(getString(R.string.driver_connect_success));
                            CMDUtils.chaxueshebei(mbtService, true);
                            // mbtService.startTime();
                            // resetHandler();
                            break;
                        case BluetoothService.STATE_CONNECTING:
                            //连接中
                            Log.d(mContext.getClass().getSimpleName(), getString(R.string.connecting_device));
                            break;
                        case BluetoothService.STATE_LISTEN:
                            break;
                        case BluetoothService.STATE_CONNECTION_LOST:
                            //失连
                            mStatus = 43;
                            //断开连接
                            // break;
                        case BluetoothService.STATE_CONNECTION_FAIL: {
                            //连接失败
                            mStatus = 42;
                            Log.d(mContext.getClass().getSimpleName(), getString(R.string.driver_connect_fail));
                            i++;
                            if (i <= 3) {
                                if (!isDestory) {
                                    mbtService.connect(bluetoothAdapter.getRemoteDevice(MAC));
                                }

                            }


                        }
                        break;
                        case BluetoothService.STATE_NONE:
                            break;

                        default:
                            break;
                    }
                    updateUI();
                    break;
                case BluetoothService.MESSAGE_WRITE:
                    break;
                case BluetoothService.MESSAGE_READ:
                    byte[] readBuf = (byte[]) msg.obj;
                    String result = DigitalTrans.bytesToHexString(readBuf);
                    //	processBluetoothDada(result);
                    //从蓝牙水表返回的数据
                    AnalyTools.analyWaterDatas(readBuf);
                    break;
                case BluetoothService.TIME_TXT:
                    diff = diff - 1000;
                    Log.d(mContext.getClass().getSimpleName(), "diff:" + diff);

                    days = diff / (1000 * 60 * 60 * 24);
                    hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
                    minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
                    seconds = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60) - minutes * (1000 * 60)) / (1000);
                    if (minutes < 10 && seconds < 10) {
                        setText("0" + minutes + ":0" + seconds);
                    } else if (minutes < 10 && seconds > 10) {
                        setText("0" + minutes + ":" + seconds);
                    } else if (minutes > 10 && seconds > 10) {
                        setText(minutes + ":" + seconds);
                    } else if (minutes > 10 && seconds < 10) {
                        setText(minutes + ":0" + seconds);
                    }
                    break;

                default:
                    break;
            }
        }
    };

    /**
     * 重置蓝牙断开的时间
     * time=60s
     */
    private void resetHandler() {
        mHandler.removeMessages(BluetoothService.MESSAGE_STATE_NOTHING);
        Message message = new Message();
        message.what = BluetoothService.MESSAGE_STATE_NOTHING;
        mHandler.sendMessageDelayed(message, 30000);
    }

    private void setText(String s) {
        Log.d(mContext.getClass().getSimpleName(), s);
    }


//    @Override
//    public boolean onTouch(View v, MotionEvent event) {
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                if (v.getId() == R.id.ivDeviceState) {
//                    if (isStart) {
//                        curTime = System.currentTimeMillis();
//                        circleProgressBar.setVisibility(View.VISIBLE);
//                        circleProgressBar.setSweepAngle(360);
//                        circleProgressBar.setText("360");
//                        circleProgressBar.startCustomAnimation(new Animation.AnimationListener() {
//                            @Override
//                            public void onAnimationStart(Animation animation) {
//
//                            }
//
//                            @Override
//                            public void onAnimationEnd(Animation animation) {
//                                if (System.currentTimeMillis() - curTime > 1500) {
//
//                                    ToastUtil.showLong("停止了");
//                                    animationDrawable.stop();
//                                } else {
//                                    ivDeviceState.setImageResource(R.mipmap.stop);
//                                }
//                            }
//
//                            @Override
//                            public void onAnimationRepeat(Animation animation) {
//                            }
//                        });
//                    }
//                    break;
//                }
//            case MotionEvent.ACTION_UP:
//                if (v.getId() == R.id.ivDeviceState) {
//                    if (isStart) {
//                        circleProgressBar.setVisibility(View.INVISIBLE);
//                        circleProgressBar.clearCustomAnimation();
//                    }
//                    else
//                    {
//                        ivDeviceState.setImageResource(R.mipmap.stop);
//                        isStart = true;
//                        animationDrawable.start();
//                    }
//                }
//
//                switch (mStatus) {
//                    case 31:
//                        //连接成功
//                        showProgress();
//                        startdDownfate(mprid, mBuffer, tac_Buffer);
//                        break;
//                    case 32:
//                        //结束费率
//                        showProgress();
//                        CMDUtils.jieshufeilv(mbtService, true);
//                        break;
//                    case 33:
//                        startDeal(mprid, mdecived, mBuffer, tac_Buffer);
//                        showProgress();
//                        break;
//                    case 35:
//                        skipUI();
//                        break;
//                    case 41:
//                        //配对失败
//                        showProgress();
//                        //连接设备
//                        connDevice();
//                        break;
//                    case 42:
//                        //连接失败
//                        showProgress();
//                        //连接设备
//                        connDevice();
//                        break;
//                    case 43:
//                        //断开连接
//                        showProgress();
//                        //连接设备
//                        connDevice();
//                        break;
//                    default:
//                        break;
//                }
//
//                break;
//        }
//        return true;
//
//    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvNext:
                skipUI();
                break;
            case R.id.ivDeviceState:

                switch (mStatus) {
                    case 31:
                        //连接成功
                        showProgress();
                        //执行服务端接口，先预扣费
                        long currentUserId = PreferencesUtils.getLong(CommonParams.LOGIN_USER_ID);
                        ChargeManager.getInstance().useWaterPreBill(currentUserId);
                        break;
                    case 32:
                        //结束费率
                        showProgress();
//                        ivShower.setImageResource(R.drawable.animation_shower);
                        animationDrawable = (AnimationDrawable) ivShower.getDrawable();
                        animationDrawable.stop();
                        CMDUtils.jieshufeilv(mbtService, true);
                        break;
                    case 33:
                        startDeal(mprid, mdecived, mBuffer, tac_Buffer);
                        showProgress();
                        break;
                    case 35:
                        skipUI();
                        break;
                    case 41:
                        //配对失败
                        showProgress();
                        //连接设备
                        connDevice();
                        break;
                    case 42:
                        //连接失败
                        showProgress();
                        //连接设备
                        connDevice();
                        break;
                    case 43:
                        //断开连接
                        showProgress();
                        //连接设备
                        connDevice();
                        break;
                    default:
                        break;
                }

                break;
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(WaterPreBillEvent event) {
        //服务端返回学生用水预扣费接口，可以执行下发费率
        if (event.getWaterPreBillModel()!=null)
        {
            String USB_OrderNo = event.getWaterPreBillModel().getData().getUSB_OrderNo();
            long userId = PreferencesUtils.getLong(CommonParams.LOGIN_USER_ID);
            float mainBalance =  PreferencesUtils.getFloat(CommonParams.USI_MAINBALANCE);
            float waterRate =  PreferencesUtils.getFloat(CommonParams.SCHOOL_WATERRATE);

            startdDownfate(mprid, USB_OrderNo, (int) userId, (int) mainBalance, (int) waterRate, mBuffer, tac_Buffer);
        }
        else
        {

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isDestory = true;
        if (mbtService != null) {
            if (mbtService.getState() == BluetoothService.STATE_CONNECTED) {
                mbtService.stop();
            }
        }
        Log.d(mContext.getClass().getSimpleName(), "onDestroy");
        unregisterReceiver(mStatusReceive);
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void eventMsg(String msg) {
        if (msg.equals("washingtimeover")) {
            connDevice();
        }
    }


    @Override
    public void caijishujuOnback(boolean b, String timeid, int mproductid, int mdeviceid,
                                 int maccountid, String accounttypeString, int usercount, String ykmoneyString, String consumeMoneString, String rateString, String macString) {

        if (b) {
            Log.d(mContext.getClass().getSimpleName(), "数据采集成功");
            //

            // 清除消费数据
            try {
                //consumeMoneString 实际使用 多少钱


                //生成消费数据单据
                xiaofeiDialog = DialogUtils.createDingdanDialog(
                        mContext, timeid,
                        mproductid + "", mdeviceid + "",
                        maccountid + "", accounttypeString,
                        usercount + "", ykmoneyString,
                        consumeMoneString, rateString,
                        macString);
                CMDUtils.fanhuicunchu(mbtService, true, timeid,
                        mproductid, mdeviceid, maccountid, usercount);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            Log.d(mContext.getClass().getSimpleName(), "数据采集失败");
        }
    }

    @Override
    public void jieshufeilvOnback(boolean b, int maccountid) {
        if (b) {
            CMDUtils.caijishuju(mbtService, true);
        }

    }

    @Override
    public void xiafafeilvOnback(boolean b) {
        if (b) {
            ToastUtil.showShort(getString(R.string.download_tate_success));
            //下发费率后
            mStatus = 32;
            updateUI();
        } else {
            ToastUtil.showShort(getString(R.string.download_tate_fail));
            mStatus = 42;
            updateUI();
        }
    }

    @Override
    public void fanhuicunchuOnback(boolean b) {
        if (b) {
            if (xiaofeiDialog != null) {
                xiaofeiDialog.show();
            }
            ToastUtil.showShort(getString(R.string.clean_cache_success));
            CMDUtils.chaxueshebei(mbtService, true);
        }
    }

    private int mprid;
    private int mdecived;
    private byte[] mBuffer;
    private byte[] tac_Buffer;
    private int times;
    private int dType;

    @Override
    public void chaxueNewshebeiOnback(boolean b, int charge, int mdeviceid, int mproductid, int maccountid,
                                      byte[] macBuffer, byte[] tac_timeBuffer, int macType, int lType, int constype, int macTime) {


        Log.d(mContext.getClass().getSimpleName(), "mdeviceid:" + mdeviceid);
        Log.d(mContext.getClass().getSimpleName(), "mproductid:" + mproductid);
        Log.d("-------", "maccountid:" + maccountid);
        Log.d(mContext.getClass().getSimpleName(), "macType:" + macType);
        Log.d(mContext.getClass().getSimpleName(), "lType:" + lType);
        Log.d(mContext.getClass().getSimpleName(), "charge:" + charge);
        mprid=mproductid;
        times =macTime;
        mdecived =mdeviceid;
        mBuffer =macBuffer;
        tac_Buffer =tac_timeBuffer;
        dType =constype;
        wtype =macType+"&"+lType;
        ToastUtil.showShort("查询成功");
        if (mproductid == 0) {
            ToastUtil.showShort(getString(R.string.device_no_login));
            mStatus = 35;
            updateUI();
            return;
        }
        switch (macType) {
            case 0:
                //水表
                setHeaderTitle(getString(R.string.water_meter));
                switch (charge) {
                    case 0:
                        mStatus = 31;
                        //连接成功
                        updateUI();
                        break;
                    case 1:
                        mStatus = 32;
                        updateUI();
                        break;
                    case 2:
                        ToastUtil.showShort(getString(R.string.payment_cards));
                        break;
                    case 3:
                        CMDUtils.caijishuju(mbtService, true);
                        break;
                }
                break;
            case 1:
                //饮水机
                setHeaderTitle(getString(R.string.water_fountain));
                switch (charge) {
                    case 0:
                        mStatus = 31;
                        //连接成功
                        updateUI();

                        break;
                    case 1:
                        mStatus = 32;
                        updateUI();
                        break;
                    case 2:
                        ToastUtil.showShort(getString(R.string.payment_cards));
                        break;
                    case 3:
                        CMDUtils.caijishuju(mbtService, true);
                        break;
                }
                break;
            case 2:
                //洗衣机
                setHeaderTitle(getString(R.string.washing_machine));
                switch (charge) {
                    case 0:
                        mStatus = 33;
                        updateUI();


                        break;
                    case 1:
                        mStatus = 36;
                        updateUI();
                        timeView.setTime(macTime);
                        break;
                    case 3:
                        CMDUtils.caijishuju(mbtService, true);
                        break;
                }
                break;
            case 3:
                //吹风机
                setHeaderTitle(getString(R.string.hair_drier));
                switch (charge) {
                    case 0:
                        mStatus = 31;
                        //连接成功
                        updateUI();
                        break;
                    case 1:
                        mStatus = 32;
                        updateUI();
                        break;
                    case 2:
                        ToastUtil.showShort(getString(R.string.payment_cards));
                        break;
                    case 3:
                        CMDUtils.caijishuju(mbtService, true);
                        break;
                }
                break;
            case 4:
                setHeaderTitle(getString(R.string.Charger));
                switch (charge) {
                    case 0:
                        mStatus = 31;
                        //连接成功
                        updateUI();
                        break;
                    case 1:
                        mStatus = 32;
                        updateUI();
                        break;
                    case 2:
                        ToastUtil.showShort(getString(R.string.payment_cards));
                        break;
                    case 3:
                        CMDUtils.caijishuju(mbtService, true);
                        break;
                }
                break;
            case 5:
                setHeaderTitle(getString(R.string.air_conditioner));
                break;
            default:
                setHeaderTitle(getString(R.string.other));
                break;
        }

    }

    /**
     * 下发费率
     */
    private void startdDownfate(int mproductid, String orderNo, int accountId, int PerMoney, int rate, byte[] macBuffer, byte[] tac_timeBuffer) {
        DownRateInfo downRateInfo = new DownRateInfo();
        //时间
        downRateInfo.ConsumeDT = DateUtils.getTimeID();
//        downRateInfo.ConsumeDT = orderNo;

        //个人账号使用次数
        downRateInfo.UseCount = 100;
        //预扣金额
        downRateInfo.PerMoney = PerMoney;
        //1标准水表2阶梯收费
        downRateInfo.ParaTypeID = 1;
        //费率1
        downRateInfo.Rate1 = rate;
        downRateInfo.Rate2 = 500;
        downRateInfo.Rate3 = 500;
        //保底消费时间
        downRateInfo.MinTime = 2;
        //保底消费金额
        downRateInfo.MinMoney = 5;
        //计费方式0 /17（16进制 0x00计时 0x11计量）
        downRateInfo.ChargeMethod = 0;
        //计费单位
        downRateInfo.MinChargeUnit = 6;
        //自动断开时间（秒），6的倍数
        downRateInfo.AutoDisConTime = 12;

        try {
            CMDUtils.xiafafeilv(mbtService, true, downRateInfo,
                    mproductid, accountId, 2, macBuffer, tac_timeBuffer);

        } catch (IOException e1) {
            e1.printStackTrace();

        }
    }

    private String wtype;

    private void startDeal(int mproductid, int mdeviceid, byte[] macBuffer, byte[] tac_timeBuffer) {
        int tims = 180;
        DealRateInfo dealRateInfo = new DealRateInfo();
        dealRateInfo.timeId = DateUtils.getTimeID();
        dealRateInfo.usecount = 1;
        dealRateInfo.MacType = wtype;
        //扣费方式
        dealRateInfo.Constype = 2;
        //钱
        dealRateInfo.ykMoney = 1000;
        //时间
        dealRateInfo.YkTimes = tims;
        dealRateInfo.WRate1 = 0;
        //计费方式
        dealRateInfo.ChargeMethod = 33;
        //计量单位
        dealRateInfo.MinChargeUnit = 1;
        dealRateInfo.AutoDisConTime = 10000;
        dealRateInfo.ERate1 = 0;
        dealRateInfo.GRate1 = 0;
        dealRateInfo.ERate2 = 0;
        dealRateInfo.Grate2 = 0;
        dealRateInfo.ERate3 = 0;
        dealRateInfo.Grate3 = 0;
        dealRateInfo.fullw = 0;
        dealRateInfo.fullTime = 0;
        times = tims;
        try {
            CMDUtils.dealStart(mbtService, dealRateInfo, mproductid, 180, 2, macBuffer, tac_timeBuffer, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startDeal(boolean b) {
        if (b) {
            Log.d(mContext.getClass().getSimpleName(), "开始交易");
            mStatus = 36;
            updateUI();
        } else {
            Log.d(mContext.getClass().getSimpleName(), "交易开始失败");
        }
    }

    @Override
    public void stopDeal(boolean b) {
        if (b) {
            Log.d(mContext.getClass().getSimpleName(), "结束交易");
            CMDUtils.caijishuju(mbtService, true);
        }
    }

    private MyHandler myHandler;


    private static class MyHandler extends Handler {
        public static final int MSG_RECEIVED_SPEED = 0x01;
        public static final int MSG_RECEIVED_STRING = 0x02;
        public static final int MESSAGE_CLEAR = 0x03;
        public static final int MSG_AUTO_WRITE_STARTED = 0x04;
        public static final int MSG_AUTO_WRITE_SPEED = 0x05;
        public static final int MSG_AUTO_WRITE_COMPLETED = 0x06;
        public static final int MSG_AUTO_CONNECT_STARTED = 0x07;
        public static final int MSG_AUTO_CONNECT = 0x08;
        public static final int MSG_AUTO_CONNECT_COMPLETED = 0x09;

        private final WeakReference<ShowerConnectActivity> host;

        public MyHandler(ShowerConnectActivity view) {
            host = new WeakReference<ShowerConnectActivity>(view);
        }

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {

                case MSG_AUTO_CONNECT_COMPLETED:
                    break;
            }
            super.handleMessage(msg);
        }
    }

}
