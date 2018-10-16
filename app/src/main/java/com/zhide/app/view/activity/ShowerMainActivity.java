package com.zhide.app.view.activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhide.app.R;
import com.zhide.app.common.CommonParams;
import com.zhide.app.utils.DialogUtils;
import com.zhide.app.view.adapter.ScanBluetoothDeviceAdapter;
import com.zhide.app.view.base.BaseActivity;
import com.zhide.app.view.views.RippleBackground;
import com.zhide.app.view.widget.NiftyDialogBuilder;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Admin
 * @date 2018/8/14
 */

public class ShowerMainActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.ivSearch)
    ImageView ivSearch;

    @BindView(R.id.lvResult)
    ListView lvResult;
    @BindView(R.id.tvSearch)
    TextView tvSearch;
    @BindView(R.id.tvSearchCount)
    TextView tvSearchCount;
    @BindView(R.id.tvSearchTitle)
    TextView tvSearchTitle;


    private BluetoothAdapter mBtAdapter;
    private ScanBluetoothDeviceAdapter scanBluetoothDeviceAdapter;
    private ArrayList<BluetoothDevice> mBluetoothDevices;
    private ArrayList<String> mBluetoothName;
    private NiftyDialogBuilder dialogBuilder;
    private BroadcastReceiver mReceiver;

    private int mSecond = 0;
    private long lastFindTime;
    private CountDownTimer mCountDownTimer;
    private static final int LONGEST_SEARCH_SECOND = 30;
    AnimationDrawable animationDrawable;


    public static void start(Context context) {
        Intent intent = new Intent(context, ShowerMainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initHeader() {
        setHeaderTitle(getString(R.string.shower_title));
        setRightTextVisibility(View.VISIBLE);
        setHeader_RightText(getString(R.string.shower_scan));
        setHeader_RightTextClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(ShowerMainActivity.this,CaptureActivity.class),CommonParams.FINISH_CODE);

            }
        });
    }

    @Override
    protected int getCenterView() {
        return R.layout.activity_shower_main;
    }

    @Override
    protected SmartRefreshLayout getRefreshView() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBluetooth();
        registerReceivers();
        startSearch();
    }

    private void initEnable() {

    }

    void initBluetooth() {
        //设置动画
        animationDrawable = (AnimationDrawable) ivSearch.getDrawable();

        dialogBuilder = NiftyDialogBuilder.getInstance(this);
        mBtAdapter = BluetoothAdapter.getDefaultAdapter();
        mBluetoothDevices = new ArrayList<>();
        mBluetoothName = new ArrayList<>();
        scanBluetoothDeviceAdapter = new ScanBluetoothDeviceAdapter(this, mBluetoothDevices, dialogBuilder);
        lvResult.setAdapter(scanBluetoothDeviceAdapter);

        mCountDownTimer = new CountDownTimer(Long.MAX_VALUE, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (mSecond >= LONGEST_SEARCH_SECOND && mBluetoothDevices.size() == 0) {
                    //停止搜索
                    stopSearch();
                    mCountDownTimer.cancel();
                }

                if (lastFindTime != 0 && System.currentTimeMillis() - lastFindTime > 5 * 1000 && mBluetoothDevices.size() > 0) {
                    //停止搜索
                    stopSearch();
                    mCountDownTimer.cancel();
                }
                mSecond += 1;
            }

            @Override
            public void onFinish() {

            }
        };
    }


    void registerReceivers() {
        // 设置广播信息过滤
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
        intentFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        intentFilter.addAction(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
        intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);


        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                    // 获取查找到的蓝牙设备
                    lvResult.setVisibility(View.VISIBLE);
                    BluetoothDevice bluetoothDevice = intent
                            .getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                    BluetoothDevice mDevice = mBtAdapter
                            .getRemoteDevice(bluetoothDevice.getAddress());

                    if (mDevice != null && mDevice.getAddress() != null) {

//					mBtAdapter.cancelDiscovery();
                        String address = mDevice.getAddress();
                        Log.e("water", "ACTION_FOUND device = " + address);

                        if (!mBluetoothName.contains(address)) {

                            //00开头的才是安卓水表
                            String[] array = address.split(":");
                            if (array[0].equals("00")) {
                                mBluetoothDevices.add(mDevice);
                                mBluetoothName.add(address);
                                scanBluetoothDeviceAdapter.changeData(mBluetoothDevices);
                            }
                        }
                        //设置找到的时间
                        lastFindTime = System.currentTimeMillis();
                        //显示搜索设备数
                        tvSearchCount.setText(String.valueOf(mBluetoothDevices.size()));
                    }

                } else if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {
                    // 状态改变的广播
                    BluetoothDevice bluetoothDevice = intent
                            .getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    BluetoothDevice mDevice = mBtAdapter
                            .getRemoteDevice(bluetoothDevice.getAddress());

                    // if (mDevice != null && mDevice.getName() != null) {
                    // Log.e("water", "device = " + mDevice.getAddress());
                    // // if (device.getName().equalsIgnoreCase(name)) {
                    // int connectState = mDevice.getBondState();
                    // switch (connectState) {
                    // case BluetoothDevice.BOND_NONE:
                    // break;
                    // case BluetoothDevice.BOND_BONDING:
                    // break;
                    // case BluetoothDevice.BOND_BONDED:
                    // mbtService.connect(mDevice);
                    // break;
                    // }
                    // // }
                    // }

                }
            }
        };

        this.registerReceiver(mReceiver, intentFilter);
    }


    void startSearch() {
        BluetoothAdapter blueadapter = BluetoothAdapter.getDefaultAdapter();
        if (!blueadapter.isEnabled()) {
           DialogUtils.showEnableBlueToothDialog(ShowerMainActivity.this);
            tvSearchTitle.setVisibility(View.INVISIBLE);
        } else {
            tvSearchTitle.setVisibility(View.VISIBLE);
            tvSearch.setText(getString(R.string.driver_connect_search));
            animationDrawable.start();
            mBluetoothDevices.clear();
            scanBluetoothDeviceAdapter.changeData(mBluetoothDevices);
            mBluetoothName.clear();
            mCountDownTimer.start();
            doDiscovery();
        }
        tvSearchCount.setText("0");

    }

    void stopSearch() {
        tvSearch.setText(getString(R.string.driver_connect_restart));
        animationDrawable.stop();
        if (mBtAdapter.isDiscovering()) {
            mBtAdapter.cancelDiscovery();
        }
    }


    private void doDiscovery() {
        if (mBtAdapter.isDiscovering()) {
            mBtAdapter.cancelDiscovery();
        }
        mBtAdapter.startDiscovery();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBtAdapter != null) {
            mBtAdapter.cancelDiscovery();
        }
        stopSearch();
        this.unregisterReceiver(mReceiver);

    }

    @Override
    @OnClick(R.id.tvSearch)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvSearch:
                startSearch();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==CommonParams.FINISH_CODE)
        {
            if (resultCode==CommonParams.FINISH_CODE) {
                setResult(CommonParams.FINISH_CODE);
                finish();
            }
        }
    }
}
