package com.zhide.app.model;

/**
 * Create by Admin on 2018/9/19
 */
public class WaterPreBillModel {
    private int code;
    private String message;
    private PreBillData data;

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

    public PreBillData getData() {
        return data;
    }

    public void setData(PreBillData data) {
        this.data = data;
    }

    public class PreBillData {
        private int USB_Id;

        public int getUSB_Id() {
            return USB_Id;
        }

        public void setUSB_Id(int USB_Id) {
            this.USB_Id = USB_Id;
        }
    }
}
