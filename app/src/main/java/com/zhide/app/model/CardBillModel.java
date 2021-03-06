package com.zhide.app.model;

import java.util.List;

/**
 * Create by Admin on 2018/9/7
 */
public class CardBillModel {
    private int code;
    private String message;
    private List<DataModel> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return message;
    }

    public void setMsg(String msg) {
        this.message = msg;
    }

    public List<DataModel> getData() {
        return data;
    }

    public void setData(List<DataModel> data) {
        this.data = data;
    }

    public class DataModel {
        private Float USCP_Money;
        private String USCP_CreateTime;
        private String USCP_Type;//转账类型：转入卡内，转入卡片
        private String USCP_Channel;//转账渠道：转款机，PC，APP
        private String USCP_DeviceID;//设备ID
        private Long USCP_Id;//表Id
        private Long USI_Id;//学生Id
        private Long SI_Id;//学校Id
        private String UCI_Id;//卡Id
        private Float USCP_NextMoney;//变动后余额（即操作后余额）

        public Float getUSCP_Money() {
            return USCP_Money;
        }

        public void setUSCP_Money(Float USCP_Money) {
            this.USCP_Money = USCP_Money;
        }

        public String getUSCP_CreateTime() {
            return USCP_CreateTime;
        }

        public void setUSCP_CreateTime(String USCP_CreateTime) {
            this.USCP_CreateTime = USCP_CreateTime;
        }

        public String getUSCP_Type() {
            return USCP_Type;
        }

        public void setUSCP_Type(String USCP_Type) {
            this.USCP_Type = USCP_Type;
        }

        public String getUSCP_Channel() {
            return USCP_Channel;
        }

        public void setUSCP_Channel(String USCP_Channel) {
            this.USCP_Channel = USCP_Channel;
        }

        public String getUSCP_DeviceID() {
            return USCP_DeviceID;
        }

        public void setUSCP_DeviceID(String USCP_DeviceID) {
            this.USCP_DeviceID = USCP_DeviceID;
        }


        public String getUCI_Id() {
            return UCI_Id;
        }

        public void setUCI_Id(String UCI_Id) {
            this.UCI_Id = UCI_Id;
        }

        public Long getUSCP_Id() {
            return USCP_Id;
        }

        public void setUSCP_Id(Long USCP_Id) {
            this.USCP_Id = USCP_Id;
        }

        public Long getUSI_Id() {
            return USI_Id;
        }

        public void setUSI_Id(Long USI_Id) {
            this.USI_Id = USI_Id;
        }

        public Long getSI_Id() {
            return SI_Id;
        }

        public void setSI_Id(Long SI_Id) {
            this.SI_Id = SI_Id;
        }

        public Float getUSCP_NextMoney() {
            return USCP_NextMoney;
        }

        public void setUSCP_NextMoney(Float USCP_NextMoney) {
            this.USCP_NextMoney = USCP_NextMoney;
        }
    }
}


