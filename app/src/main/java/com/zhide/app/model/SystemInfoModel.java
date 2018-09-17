package com.zhide.app.model;

/**
 * Create by Admin on 2018/9/14
 */
public class SystemInfoModel {
    private int code;
    private String message;
    private SystemData data;

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

    public SystemData getData() {
        return data;
    }

    public void setData(SystemData data) {
        this.data = data;
    }

    private class SystemData {
      private long NI_Id;
      private String NI_Name;
      private String NI_Summary;
      private String NI_UpdateTime;//内容值

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

        public String getNI_UpdateTime() {
            return NI_UpdateTime;
        }

        public void setNI_UpdateTime(String NI_UpdateTime) {
            this.NI_UpdateTime = NI_UpdateTime;
        }
    }
}
