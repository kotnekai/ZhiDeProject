package com.zhide.app.common;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zhide.app.R;
import com.zhide.app.okhttp.MyOkhttpUtils;
import com.zhide.app.utils.PickViewUtil;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by hasee on 2018/8/8.
 */

public class ComApplication extends Application {
    public static ComApplication mzjApplication;
    public List<Activity> mActivityList = null;
    private IWXAPI msgApi;

    //static 代码段可以防止内存泄露
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.main_blue_color, R.color.white);//全局设置主题颜色
                return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }
    /**
     * 在这里做一些全局的初始化操作
     */
    @Override
    public void onCreate() {
        super.onCreate();

        ApplicationHolder.getInstance().setAppContext(this);
        // CrashManager.getInstance().init(this); //初始化本地崩溃日志收集

        MyOkhttpUtils.initOkhttp(this);
        msgApi = WXAPIFactory.createWXAPI(this, CommonParams.WECHAT_APPID);
        // 将该app注册到微信
        msgApi.registerApp(CommonParams.WECHAT_APPID);
        Log.d("admin", "getMsgApi: msgApi1="+msgApi);
        ApplicationHolder.getInstance().setMsgApi(msgApi);
        PickViewUtil.initTimePickOptions(this);
      // ZXingLibrary.initDisplayOpinion(this);

        mzjApplication = this;
        initCrashCat();

    }

    private void initCrashCat(){

        ThreadPoolManager.getInstance().getSingleThreadPool().execute(new Runnable() {
           @Override
           public void run() {
               String path = Environment.getExternalStorageDirectory().getPath();
               Log.d("admin", "initCrashCat: path="+path);
               CrashCat.getInstance(getApplicationContext(),  path+ CommonParams.DIRECTORY_ROOT, CommonParams.FILE_LOG).start();
           }
       });
    }

    /**
     * 获取application实例
     *
     * @return
     */
    public static ComApplication getApp() {
        return mzjApplication;
    }



    /**
     * 添加activity
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        if (mActivityList == null) {
            mActivityList = new CopyOnWriteArrayList<>();
        }

        if (activity != null) {

            mActivityList.add(activity);
        }
    }

    /**
     * 退出栈中所有的activity，用于退出登录
     */
    public void removeAllActivity() {
        if (mActivityList != null) {
            for (Activity activity : mActivityList) {
                removeActivity(activity);
                activity.finish();
            }
        }
    }

    /**
     * 退出栈中所有的activity，用于退出登录
     */
    public void removeAllActivity(Activity currentActivity) {
        if (mActivityList != null) {
            for (Activity activity : mActivityList) {
                if (activity == currentActivity) {
                    return;
                }
                removeActivity(activity);
                activity.finish();
            }
        }
    }

    /**
     * 获取当前的activity
     *
     * @return
     */
    public Activity getCurrentActivity() {
        if (mActivityList != null) {
            if (mActivityList.size() > 0) {
                return mActivityList.get(mActivityList.size() - 1);
            }
        }
        return null;
    }

    /**
     * 移除activity
     *
     * @param activity
     */
    public void removeActivity(Activity activity) {
        if (mActivityList != null && activity != null) {
            mActivityList.remove(activity);
        }
    }
}
