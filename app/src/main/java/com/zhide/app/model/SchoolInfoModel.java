package com.zhide.app.model;

/**
 * Create by Admin on 2018/9/8
 */
public class SchoolInfoModel {
    private int code;
    private String message;
    private SchoolModel data;

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


    public SchoolModel getData() {
        return data;
    }

    public void setData(SchoolModel data) {
        this.data = data;
    }

    public class SchoolModel {
        private long SI_Id;
        private String SI_Code;//学校GUID，用于生成二维码使用
        private String SI_Name;//  学校名称
        private String SI_Phone;//学校联系电话
        private String SI_People;//学校联系人
        private String SI_Mobile;//学校联系人手机号码

        private String SI_UpdateTime;//更新时间
        private int SI_MaxTurnCount;//每天最多转款次数
        private String SI_RechargeMoney;//允许充值的金额，30|40|50|80|100|其他
        private int SI_MaxTurn;//单笔最大转款金额
        private String SI_IsRefund;// 是否退款 是或否
        private String SI_UpdateBy;// 更新人
        private String SI_UseMode;//  学校用水表模式：单卡，单蓝牙，卡和蓝牙
        private String SI_Address;//学校地址
        private String SI_Email;//学校邮箱地址
        private String SI_State;//状态：可用，注销

        public long getSI_Id() {
            return SI_Id;
        }

        public void setSI_Id(long SI_Id) {
            this.SI_Id = SI_Id;
        }

        public String getSI_Code() {
            return SI_Code;
        }

        public void setSI_Code(String SI_Code) {
            this.SI_Code = SI_Code;
        }

        public String getSI_Name() {
            return SI_Name;
        }

        public void setSI_Name(String SI_Name) {
            this.SI_Name = SI_Name;
        }

        public String getSI_Phone() {
            return SI_Phone;
        }

        public void setSI_Phone(String SI_Phone) {
            this.SI_Phone = SI_Phone;
        }

        public String getSI_People() {
            return SI_People;
        }

        public void setSI_People(String SI_People) {
            this.SI_People = SI_People;
        }

        public String getSI_Mobile() {
            return SI_Mobile;
        }

        public void setSI_Mobile(String SI_Mobile) {
            this.SI_Mobile = SI_Mobile;
        }

        public String getSI_UpdateTime() {
            return SI_UpdateTime;
        }

        public void setSI_UpdateTime(String SI_UpdateTime) {
            this.SI_UpdateTime = SI_UpdateTime;
        }

        public int getSI_MaxTurnCount() {
            return SI_MaxTurnCount;
        }

        public void setSI_MaxTurnCount(int SI_MaxTurnCount) {
            this.SI_MaxTurnCount = SI_MaxTurnCount;
        }

        public String getSI_RechargeMoney() {
            return SI_RechargeMoney;
        }

        public void setSI_RechargeMoney(String SI_RechargeMoney) {
            this.SI_RechargeMoney = SI_RechargeMoney;
        }

        public int getSI_MaxTurn() {
            return SI_MaxTurn;
        }

        public void setSI_MaxTurn(int SI_MaxTurn) {
            this.SI_MaxTurn = SI_MaxTurn;
        }

        public String getSI_IsRefund() {
            return SI_IsRefund;
        }

        public void setSI_IsRefund(String SI_IsRefund) {
            this.SI_IsRefund = SI_IsRefund;
        }

        public String getSI_UpdateBy() {
            return SI_UpdateBy;
        }

        public void setSI_UpdateBy(String SI_UpdateBy) {
            this.SI_UpdateBy = SI_UpdateBy;
        }

        public String getSI_UseMode() {
            return SI_UseMode;
        }

        public void setSI_UseMode(String SI_UseMode) {
            this.SI_UseMode = SI_UseMode;
        }

        public String getSI_Address() {
            return SI_Address;
        }

        public void setSI_Address(String SI_Address) {
            this.SI_Address = SI_Address;
        }

        public String getSI_Email() {
            return SI_Email;
        }

        public void setSI_Email(String SI_Email) {
            this.SI_Email = SI_Email;
        }

        public String getSI_State() {
            return SI_State;
        }

        public void setSI_State(String SI_State) {
            this.SI_State = SI_State;
        }
    }
}
