package com.zhide.app.okhttp;

import android.content.Context;

import com.zhide.app.common.CommonParams;
import com.zhide.okhttputils.https.HttpsUtils;
import com.zhide.okhttputils.log.LoggerInterceptor;
import com.zhide.okhttputils.utils.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by hasee on 2018/2/26.
 */

public class MyOkhttpUtils {

    public static final long CONNECT_TIMEOUT = 15000L;
    public static final long READ_TIMEOUT = 15000L;
    public static final String TAG = "admin";
    public static OkHttpClient mOkHttpClient = null;

    public static void initOkhttp(Context context) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoggerInterceptor(TAG, CommonParams.IS_WRITE_LOG, context))
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)
                //其他配置
                .build();
        OkHttpUtils okHttpUtils = OkHttpUtils.initClient(okHttpClient);
        okHttpClient = okHttpUtils.getOkHttpClient();
        mOkHttpClient = okHttpClient;
    }

    public static void initHttpsOkHttp(Context context) {
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                //其他配置
                .addInterceptor(new LoggerInterceptor(TAG, CommonParams.IS_WRITE_LOG, context))
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)
                .build();
        OkHttpUtils okHttpUtils = OkHttpUtils.initClient(okHttpClient);
        mOkHttpClient = okHttpUtils.getOkHttpClient();
    }

    public static OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }
}
