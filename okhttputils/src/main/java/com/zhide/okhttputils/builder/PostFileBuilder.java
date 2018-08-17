package com.zhide.okhttputils.builder;
import com.zhide.okhttputils.request.PostFileRequest;
import com.zhide.okhttputils.request.RequestCall;

import java.io.File;

import okhttp3.MediaType;

/**
 * Created by zhy on 15/12/14.
 */
public class PostFileBuilder extends OkHttpRequestBuilder<PostFileBuilder>
{
    private File file;
    private MediaType mediaType;
    public boolean isFormSubmitFile = false;

    public OkHttpRequestBuilder file(File file)
    {
        this.file = file;
        return this;
    }

    public OkHttpRequestBuilder mediaType(MediaType mediaType)
    {
        this.mediaType = mediaType;
        return this;
    }

    @Override
    public RequestCall build()
    {
        PostFileRequest postFileRequest = new PostFileRequest(url, tag, params, headers, file, mediaType, id);
        postFileRequest.isFormSubmitFile = isFormSubmitFile;
        return postFileRequest.build();
    }


}
