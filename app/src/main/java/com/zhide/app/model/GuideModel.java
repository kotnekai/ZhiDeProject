package com.zhide.app.model;

/**
 * Create by Admin on 2018/9/14
 */
public class GuideModel {
    private int code;
    private String message;
    private GuideData data;

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

    public GuideData getData() {
        return data;
    }

    public void setData(GuideData data) {
        this.data = data;
    }

    public class GuideData {
        private long NI_Id;
        private String NI_Name;//标题
        private String NI_Summary;//内容简介，若列表中需要显示部分文字，则使用该字段
        private String NI_Url;//H5的页面链接，APP端打开内容页时直接访问这个H5的页面链接
        private int NI_Index;//顺序号
        private String NI_UpdateTime;//更新时间

        public long getNI_Id() {
            return NI_Id;
        }

        public void setNI_Id(long NI_Id) {
            this.NI_Id = NI_Id;
        }

        public String getNI_Name() {
            return NI_Name;
        }

        public void setNI_Name(String NI_Name) {
            this.NI_Name = NI_Name;
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
    }

}
