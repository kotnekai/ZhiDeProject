package com.zhide.app.logic;

import android.os.Handler;
import android.os.HandlerThread;

import com.example.jooronjar.BluetoothService;
import com.example.jooronjar.DealRateInfo;
import com.example.jooronjar.DownRateInfo;
import com.example.jooronjar.utils.CMDUtils;
import com.example.jooronjar.utils.DigitalTrans;
import com.zhide.app.common.CommonUrl;
import com.zhide.app.common.CrashCat;
import com.zhide.app.eventBus.CardBillEvent;
import com.zhide.app.eventBus.ErrorMsgEvent;
import com.zhide.app.eventBus.MyBillEvent;
import com.zhide.app.model.CardBillModel;
import com.zhide.app.model.MyBillModel;
import com.zhide.app.okhttp.DataManager;
import com.zhide.app.utils.DateUtils;
import com.zhide.okhttputils.callback.GenericsCallback;
import com.zhide.okhttputils.request.JsonGenericsSerializator;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Create by Admin on 2018/9/7
 */
public class ShowerManager {

    private static final String TAG = ShowerManager.class.getSimpleName();


    private static ShowerManager instance = null;
    private HandlerThread mShowerHandlerThread = null;//睡眠子线程、处理一些耗时的操作
    private Handler mShowerHandler = null;//睡眠子线程handler 发消息到子线程

    private ShowerManager() {
    }

    public static ShowerManager getInstance() {
        if (instance == null) {
            synchronized (ShowerManager.class) {
                instance = new ShowerManager();
            }
        }
        return instance;
    }

    public Handler getShowerHandler() {
        if (mShowerHandler == null || mShowerHandlerThread == null) {
            synchronized (ShowerManager.class) {
                if (mShowerHandler == null || mShowerHandlerThread == null) {
                    mShowerHandlerThread = new HandlerThread("SHOWER_SUB_THREAD");
                    mShowerHandlerThread.start();
                    mShowerHandler = new Handler(mShowerHandlerThread.getLooper());
                }
            }
        }
        return mShowerHandler;
    }

    /**
     * 采集数据
     *
     * @param mbtService
     * @param returncmddata
     */
    public void caijishuju(final BluetoothService mbtService, final boolean returncmddata) {
        getShowerHandler().post(new Runnable() {
            @Override
            public void run() {
                CMDUtils.caijishuju(mbtService, returncmddata);
            }
        });
    }


    /**
     * 下发费率
     *
     * @param mbtService
     * @param mproductid
     * @param accountId
     * @param PerMoney
     * @param rate
     * @param macBuffer
     * @param tac_timeBuffer
     */
    public void startdDownfate(final BluetoothService mbtService, final int mproductid, final int accountId, final int PerMoney, final int rate, final byte[] macBuffer, final byte[] tac_timeBuffer) {

        getShowerHandler().post(new Runnable() {
            @Override
            public void run() {
                DownRateInfo downRateInfo = new DownRateInfo();
                //时间
                downRateInfo.ConsumeDT = DateUtils.getTimeID();

                //个人账号使用次数
                downRateInfo.UseCount = 1;
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
                downRateInfo.ChargeMethod = 17;
                //计费单位
                downRateInfo.MinChargeUnit = 10;
                //自动断开时间（秒），6的倍数
                downRateInfo.AutoDisConTime = 1;
                try {
                    CMDUtils.xiafafeilv(mbtService, true, downRateInfo,
                            mproductid, accountId, 2, macBuffer, tac_timeBuffer);

                } catch (IOException e1) {
                    e1.printStackTrace();

                }
            }

        });
    }

    /**
     * 查询设备
     *
     * @param mbtService
     * @param returncmddata
     */
    public void chaxueshebei(final BluetoothService mbtService, final boolean returncmddata) {
        getShowerHandler().post(new Runnable() {
            @Override
            public void run() {
                CMDUtils.chaxueshebei(mbtService, returncmddata);
            }
        });
    }

    /**
     * 返回储存
     *
     * @param mbtService
     * @param returncmddata
     * @param timeid
     * @param mproductid
     * @param mdeviceid
     * @param maccountid
     * @param usercount
     */
    public void fanhuicunchu(final BluetoothService mbtService, final boolean returncmddata, final String timeid, final int mproductid, final int mdeviceid, final int maccountid, final int usercount) {
        getShowerHandler().post(new Runnable() {
            @Override
            public void run() {
                try {
                    CMDUtils.fanhuicunchu(mbtService, true, timeid,
                            mproductid, mdeviceid, maccountid, usercount);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 结束费率
     *
     * @param mbtService
     * @param returncmddata
     * @return
     */
    public void jieshufeilv(final BluetoothService mbtService, final boolean returncmddata) {
        getShowerHandler().post(new Runnable() {
            @Override
            public void run() {
                CMDUtils.jieshufeilv(mbtService, returncmddata);
            }
        });
    }

    /**
     * 下发费率
     * @param mbtService
     * @param returncmddata
     * @param downRateInfo
     * @param mproductid
     * @param maccountid
     * @param maccounttype
     * @param macBuffer
     * @param tac_time
     * @throws IOException
     */
    public void xiafafeilv(final BluetoothService mbtService, final boolean returncmddata, final DownRateInfo downRateInfo, final int mproductid, final int maccountid, final int maccounttype, final byte[] macBuffer, final byte[] tac_time) throws IOException {
        getShowerHandler().post(new Runnable() {
            @Override
            public void run() {
                try {
                    CMDUtils.xiafafeilv(mbtService, returncmddata, downRateInfo,
                            mproductid, maccountid, maccounttype, macBuffer, tac_time);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

