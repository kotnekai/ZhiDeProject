package com.zhide.okhttputils.log;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.zhide.okhttputils.log.model.NetLogModel;


import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

/**
 * Created by zhy on 16/3/1.
 */
public class LoggerInterceptor implements Interceptor {
    public static final String TAG = "OkHttpUtils";
    private String tag;
    private boolean showResponse;
    private Context context;
    public LoggerInterceptor(String tag, boolean showResponse, Context context) {
        if (TextUtils.isEmpty(tag)) {
            tag = TAG;
        }
        this.showResponse = showResponse;
        this.tag = tag;
        this.context = context;
    }

    public LoggerInterceptor(String tag, Context context) {
        this(tag, false,context);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if(showResponse){
            logForRequest(request);
        }
        Response response = chain.proceed(request);
        return logForResponse(response);
    }

    private Response logForResponse(Response response) {
        try {
            //===>response log
            Log.e(tag, "========response'log=======");

            Response.Builder builder = response.newBuilder();
            Response clone = builder.build();
            Log.i(tag, "url : " + clone.request().url());
            Log.i(tag, "code : " + clone.code());
            Log.i(tag, "protocol : " + clone.protocol());
            if (!TextUtils.isEmpty(clone.message()))
                Log.i(tag, "message : " + clone.message());
           //EventBus.getDefault().post(new InterceptCodeEvent(clone.code()));
            if (showResponse) {
                NetLogModel netLogModel = new NetLogModel();
                netLogModel.setUrl(clone.request().url().toString());
                netLogModel.setCode(clone.code());

                ResponseBody body = clone.body();
                if (body != null) {
                    MediaType mediaType = body.contentType();
                    if (mediaType != null) {

                        Log.i(tag, "responseBody's contentType : " + mediaType.toString());
                        netLogModel.setContentType(mediaType.toString());

                        if (isText(mediaType)) {

                            String resp = body.string();
                            Log.i(tag, "responseBody's content : " + resp);
                            netLogModel.setContent(resp);

                            body = ResponseBody.create(mediaType, resp);

                            NetLogManager.getInstance(context).logNetResponse(netLogModel,true);
                            return response.newBuilder().body(body).build();
                        } else {
                            Log.i(tag, "responseBody's content : " + " maybe [file part] , too large too print , ignored!");
                        }
                    }
                }

            }

            Log.e(tag, "========response'log=======end");
        } catch (Exception e) {
//            e.printStackTrace();
        }

        return response;
    }

    private void logForRequest(Request request) {
        try {
            String url = request.url().toString();
            Headers headers = request.headers();
            NetLogModel netLogModel = new NetLogModel();
            netLogModel.setMethod(request.method());
            netLogModel.setUrl(url);

            Log.e(tag, "========request'log=======");
            Log.i(tag, "method : " + request.method());
            Log.i(tag, "url : " + url);
            if (headers != null && headers.size() > 0) {
                Log.i(tag, "headers : " + headers.toString());
                netLogModel.setHeaders(headers.toString());
                NetLogManager.getInstance(context).logNetResponse(netLogModel,false);
            }
            RequestBody requestBody = request.body();
            if (requestBody != null) {
                MediaType mediaType = requestBody.contentType();
                if (mediaType != null) {
                    Log.i(tag, "requestBody's contentType : " + mediaType.toString());
                    if (isText(mediaType)) {
                        Log.i(tag, "requestBody's content : " + bodyToString(request));
                    } else {
                        Log.i(tag, "requestBody's content : " + " maybe [file part] , too large too print , ignored!");
                    }
                }
            }
            Log.e(tag, "========request'log=======end");
        } catch (Exception e) {
//            e.printStackTrace();
        }
    }

    private boolean isText(MediaType mediaType) {
        if (mediaType.type() != null && mediaType.type().equals("text")) {
            return true;
        }
        if (mediaType.subtype() != null) {
            if (mediaType.subtype().equals("json") ||
                    mediaType.subtype().equals("xml") ||
                    mediaType.subtype().equals("html") ||
                    mediaType.subtype().equals("webviewhtml")
                    )
                return true;
        }
        return false;
    }

    private String bodyToString(final Request request) {
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "something error when show requestBody.";
        }
    }
}
