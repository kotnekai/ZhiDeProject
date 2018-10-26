package com.zhide.app.model;

import java.io.Serializable;
import java.util.List;

/**
 * Create by Admin on 2018/9/14
 */
public class SystemInfoModel implements Serializable{
    private int code;
    private String message;
    private List<SystemData> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<SystemData> getData() {
        return data;
    }

    public void setData(List<SystemData> data) {
        this.data = data;
    }

    public class SystemData implements Serializable{
        private long NI_Id;
        private String NI_Title;
        private String NI_Summary;
        private String NI_Url;// 注册协议链接，或者apk更新链接
        private int NI_Index;
        private String NI_UpdateTime;//更新时间
        private String NI_PicUrl;//

        public long getNI_Id() {
            return NI_Id;
        }

        public void setNI_Id(long NI_Id) {
            this.NI_Id = NI_Id;
        }

        public String getNI_Title() {
            return NI_Title;
        }

        public void setNI_Title(String NI_Title) {
            this.NI_Title = NI_Title;
        }

        public String getNI_Summary() {
            return NI_Summary;
        }

        public void setNI_Summary(String NI_Summary) {
            this.NI_Summary = NI_Summary;
        }

        public String getNI_Url() {
            return NI_Url;
        }

        public void setNI_Url(String NI_Url) {
            this.NI_Url = NI_Url;
        }

        public int getNI_Index() {
            return NI_Index;
        }

        public void setNI_Index(int NI_Index) {
            this.NI_Index = NI_Index;
        }

        public String getNI_UpdateTime() {
            return NI_UpdateTime;
        }

        public void setNI_UpdateTime(String NI_UpdateTime) {
            this.NI_UpdateTime = NI_UpdateTime;
        }

        public String getNI_PicUrl() {
            return NI_PicUrl;
        }

        public void setNI_PicUrl(String NI_PicUrl) {
            this.NI_PicUrl = NI_PicUrl;
        }
    }
}
