package com.zhide.okhttputils.builder;

import com.zhide.okhttputils.request.OtherRequest;
import com.zhide.okhttputils.request.RequestCall;
import com.zhide.okhttputils.utils.OkHttpUtils;

/**
 * Created by zhy on 16/3/2.
 */
public class HeadBuilder extends GetBuilder
{
    @Override
    public RequestCall build()
    {
        return new OtherRequest(null, null, OkHttpUtils.METHOD.HEAD, url, tag, params, headers,id).build();
    }
}
