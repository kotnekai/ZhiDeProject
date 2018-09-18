package com.zhide.app.model;

import java.util.List;

/**
 * Create by Admin on 2018/8/22
 */
public class MyBillModel {
    private int code;
    private String message;
    private List<BillData> data;

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

    public List<BillData> getData() {
        return data;
    }

    public void setData(List<BillData> data) {
        this.data = data;
    }

    public class BillData {
        private Long USB_Id;//表Id
        private Float USB_Money;
        private Long USI_Id;//学生Id
        private Long SI_Id;//学校Id
        private String USB_CreateTime;//创建时间变动时间
        private Float USB_NextMoney;// 变动后账户金额
        private Float USB_PrevMoney;// 变动前账户金额
        private String USB_ChangeType;// 资金变动类型：支出或充值
        private String USB_ChangeThing;// 变动事由：用水/充值/转入卡片/提现/赠送


        public String getUSB_CreateTime() {
            return USB_CreateTime;
        }

        public void setUSB_CreateTime(String USB_CreateTime) {
            this.USB_CreateTime = USB_CreateTime;
        }

        public Long getUSB_Id() {
            return USB_Id;
        }

        public void setUSB_Id(Long USB_Id) {
            this.USB_Id = USB_Id;
        }

        public Float getUSB_Money() {
            return USB_Money;
        }

        public void setUSB_Money(Float USB_Money) {
            this.USB_Money = USB_Money;
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

        public Float getUSB_NextMoney() {
            return USB_NextMoney;
        }

        public void setUSB_NextMoney(Float USB_NextMoney) {
            this.USB_NextMoney = USB_NextMoney;
        }

        public Float getUSB_PrevMoney() {
            return USB_PrevMoney;
        }

        public void setUSB_PrevMoney(Float USB_PrevMoney) {
            this.USB_PrevMoney = USB_PrevMoney;
        }

        public String getUSB_ChangeType() {
            return USB_ChangeType;
        }

        public void setUSB_ChangeType(String USB_ChangeType) {
            this.USB_ChangeType = USB_ChangeType;
        }

        public String getUSB_ChangeThing() {
            return USB_ChangeThing;
        }

        public void setUSB_ChangeThing(String USB_ChangeThing) {
            this.USB_ChangeThing = USB_ChangeThing;
        }
    }


}
