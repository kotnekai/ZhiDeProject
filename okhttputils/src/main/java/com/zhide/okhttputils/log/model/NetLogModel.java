package com.zhide.okhttputils.log.model;

/**
 * Created by hasee on 2018/2/26.
 */

public class NetLogModel {
    private String method;
    private String headers;
    private String url;
    private int code;
    private String contentType;
    private String content;

    public NetLogModel() {
    }

    public NetLogModel(String method, String headers, String url) {
        this.method = method;
        this.headers = headers;
        this.url = url;
    }

    public NetLogModel(String url, int code, String contentType, String content) {
        this.url = url;
        this.code = code;
        this.contentType = contentType;
        this.content = content;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getHeaders() {
        return headers;
    }

    public void setHeaders(String headers) {
        this.headers = headers;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String toRequestString() {
        return "NetLogModel{" +
                "method='" + method + '\n' +
                ", headers='" + headers + '\n' +
                ", url='" + url + '\n' +
                ", code=" + code + '\n' +
                '}';
    }

    public String toResponseString() {
        return "NetLogModel{" +
                ", url='" + url + '\n' +
                ", code=" + code + '\n' +
                ", contentType='" + contentType + '\n' +
                ", content='" + content + '\n' +
                '}';
    }
}
