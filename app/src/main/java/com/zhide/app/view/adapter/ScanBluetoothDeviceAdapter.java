package com.zhide.app.view.adapter;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.zhide.app.R;
import com.zhide.app.common.CommonParams;
import com.zhide.app.utils.DialogUtils;
import com.zhide.app.view.activity.ShowerConnectActivity;
import com.zhide.app.view.activity.ShowerMainActivity;
import com.zhide.app.view.widget.Effectstype;
import com.zhide.app.view.widget.NiftyDialogBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Admin
 */
public class ScanBluetoothDeviceAdapter extends BaseAdapter {

    private LayoutInflater mInflator;
    private List<BluetoothDevice> devices;
    private Context mContext;
    private NiftyDialogBuilder dialogBuilder;

    public ScanBluetoothDeviceAdapter(Context context,
                                      List<BluetoothDevice> list, NiftyDialogBuilder niftyDialogBuilder) {
        super();
        mContext = context;
        mInflator = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        devices = list;

        dialogBuilder = niftyDialogBuilder;
    }

    @Override
    public int getCount() {
        return devices.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return devices.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub

        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder;
        if (view == null) {
            view = mInflator.inflate(R.layout.item_scan_bluetoothdevice, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.tvAddress = (TextView) view.findViewById(R.id.tvAddress);
            viewHolder.btnBinding = (Button) view.findViewById(R.id.btnBinding);
            viewHolder.rlLayout = (RelativeLayout) view.findViewById(R.id.rlLayout);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        BluetoothDevice bluetoothDevice = devices.get(i);

        viewHolder.tvAddress.setText(TextUtils.isEmpty(bluetoothDevice.getName()) ? bluetoothDevice.getAddress() : bluetoothDevice.getName());
        viewHolder.rlLayout.setTag(bluetoothDevice);
        viewHolder.rlLayout.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
                if (!adapter.isEnabled()) {
                    DialogUtils.showEnableBlueToothDialog((Activity) mContext);
                } else {
                    final BluetoothDevice bDevice = (BluetoothDevice) v.getTag();
                    Intent intent = new Intent();
                    intent.setClass(mContext, ShowerConnectActivity.class);
                    intent.putExtra(ShowerConnectActivity.DEVICE_MAC, bDevice.getAddress());
                    intent.putExtra(ShowerConnectActivity.DEVICE_NAME, bDevice.getName());
                    ((Activity) mContext).startActivityForResult(intent, CommonParams.FINISH_CODE);

//                    dialogBuilder
//                            .withTitle(mContext.getString(R.string.dialog_tips_title))
//                            .withMessage(
//                                    mContext.getString(R.string.dialog_bluetooth_bind))
//                            .withEffect(Effectstype.Fadein)
//                            .isCancelable(false)
//                            .withButton1Text(
//                                    mContext.getString(R.string.cancel))
//                            .setButton1Click(new OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    dialogBuilder.dismiss();
//                                }
//                            })
//                            .withButton2Text(mContext.getString(R.string.sure))
//                            .setButton2Click(new OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    try {
//                                        dialogBuilder.dismiss();
//
//                                        Intent intent = new Intent();
//                                        intent.setClass(mContext, ShowerConnectActivity.class);
//                                        intent.putExtra(ShowerConnectActivity.DEVICE_MAC, bDevice.getAddress());
//                                        intent.putExtra(ShowerConnectActivity.DEVICE_NAME, bDevice.getName());
//
//                                        ((Activity) mContext).startActivityForResult(intent, CommonParams.FINISH_CODE);
//                                    }catch (Exception ei)
//                                    {
//                                        ei.printStackTrace();
//                                    }
//                                }
//                            }).show();
                }
            }
        });
        return view;
    }

    static class ViewHolder {
        TextView tvAddress;
        Button btnBinding;
        RelativeLayout rlLayout;
    }

    public void changeData(ArrayList<BluetoothDevice> lists) {
        devices = lists;
        notifyDataSetChanged();

    }

}
