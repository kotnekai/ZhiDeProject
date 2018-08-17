package com.zhide.app.view.activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.zhide.app.R;
import com.zhide.app.view.adapter.ScanBluetoothDeviceAdapter;
import com.zhide.app.view.base.BaseActivity;
import com.zhide.app.view.views.RippleBackground;
import com.zhide.app.view.widget.NiftyDialogBuilder;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;

/**
 *
 * @author Admin
 * @date 2018/8/14
 */

public class ShowerMainActivity extends BaseActivity  implements  View.OnClickListener{


    @BindView(R.id.rippleBackground)
    RippleBackground rippleBackground;

    @BindView(R.id.lvResult)
    ListView lvResult;
    @BindView(R.id.tvSearch)
    TextView tvSearch;
    @BindView(R.id.tvSearchCount)
    TextView tvSearchCount;


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


    public static void start(Context context) {
        Intent intent = new Intent(context, ShowerMainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initHeader() {
        setHeaderTitle(getString(R.string.shower_title));

    }

    @Override
    protected int getCenterView() {
        return R.layout.activity_shower_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBluetooth();
        registerReceivers();
        startSearch();
    }

    void initBluetooth()
    {
        tvSearch.setOnClickListener(this);

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


         mReceiver  = new BroadcastReceiver() {
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
                         Log.e("water","ACTION_FOUND device = " + address);

                         if (!mBluetoothName.contains(address)) {
                             mBluetoothDevices.add(mDevice);
                             mBluetoothName.add(address);
                             scanBluetoothDeviceAdapter.changeData(mBluetoothDevices);
                         }
                         //设置找到的时间
                         lastFindTime = System.currentTimeMillis();
                         //显示搜索设备数
                         String content = String.format(context.getResources().getString(R.string.driver_connect_count), String.valueOf(mBluetoothDevices.size()));
                         tvSearchCount.setText(content);
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

         this.registerReceiver(mReceiver , intentFilter);
    }



    void startSearch()
    {
        tvSearch.setText(getString(R.string.driver_connect_search));
        rippleBackground.startRippleAnimation();
        mBluetoothDevices.clear();
        scanBluetoothDeviceAdapter.changeData(mBluetoothDevices);
        mBluetoothName.clear();
        mCountDownTimer.start();
        doDiscovery();
    }

    void stopSearch()
    {
        tvSearch.setText(getString(R.string.driver_connect_restart));
        rippleBackground.stopRippleAnimation();
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
        if (mBtAdapter != null) {
            mBtAdapter.cancelDiscovery();
        }
        super.onDestroy();
        this.unregisterReceiver(mReceiver);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.tvSearch:
                startSearch();
                break;
        }
    }
}
