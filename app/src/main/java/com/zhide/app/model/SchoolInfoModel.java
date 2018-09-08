package com.zhide.app.model;

/**
 * Create by Admin on 2018/9/8
 */
public class SchoolInfoModel {
    private int code;
    private String msg;
    private SchoolModel schoolModel;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public SchoolModel getSchoolModel() {
        return schoolModel;
    }

    public void setSchoolModel(SchoolModel schoolModel) {
        this.schoolModel = schoolModel;
    }

    public class SchoolModel {
        private long SI_Id;
        private String SI_Code;//学校GUID，用于生成二维码使用
        private String SI_UpdateTime;//更新时间
        private int SI_MaxTurnCount;//每天最多转款次数
        private int SI_RechargeMoney;//允许充值的金额，30|40|50|80|100|其他
        private int SI_MaxTurn;//单笔最大转款金额
        private int SI_IsRefund;// 是否退款 是或否
        private int SI_UpdateBy;// 更新人
        private int SI_UseMode;//  学校用水表模式：单卡，单蓝牙，卡和蓝牙
        private int SI_Name;//  学校名称
        private int SI_Phone;//学校联系电话
        private int SI_People;//学校联系人
        private int SI_Mobile;//学校联系人手机号码
        private int SI_Address;//学校地址
        private int SI_Email;//学校邮箱地址
        private int SI_State;//状态：可用，注销

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

        public int getSI_RechargeMoney() {
            return SI_RechargeMoney;
        }

        public void setSI_RechargeMoney(int SI_RechargeMoney) {
            this.SI_RechargeMoney = SI_RechargeMoney;
        }

        public int getSI_MaxTurn() {
            return SI_MaxTurn;
        }

        public void setSI_MaxTurn(int SI_MaxTurn) {
            this.SI_MaxTurn = SI_MaxTurn;
        }

        public int getSI_IsRefund() {
            return SI_IsRefund;
        }

        public void setSI_IsRefund(int SI_IsRefund) {
            this.SI_IsRefund = SI_IsRefund;
        }

        public int getSI_UpdateBy() {
            return SI_UpdateBy;
        }

        public void setSI_UpdateBy(int SI_UpdateBy) {
            this.SI_UpdateBy = SI_UpdateBy;
        }

        public int getSI_UseMode() {
            return SI_UseMode;
        }

        public void setSI_UseMode(int SI_UseMode) {
            this.SI_UseMode = SI_UseMode;
        }

        public int getSI_Name() {
            return SI_Name;
        }

        public void setSI_Name(int SI_Name) {
            this.SI_Name = SI_Name;
        }

        public int getSI_Phone() {
            return SI_Phone;
        }

        public void setSI_Phone(int SI_Phone) {
            this.SI_Phone = SI_Phone;
        }

        public int getSI_People() {
            return SI_People;
        }

        public void setSI_People(int SI_People) {
            this.SI_People = SI_People;
        }

        public int getSI_Mobile() {
            return SI_Mobile;
        }

        public void setSI_Mobile(int SI_Mobile) {
            this.SI_Mobile = SI_Mobile;
        }

        public int getSI_Address() {
            return SI_Address;
        }

        public void setSI_Address(int SI_Address) {
            this.SI_Address = SI_Address;
        }

        public int getSI_Email() {
            return SI_Email;
        }

        public void setSI_Email(int SI_Email) {
            this.SI_Email = SI_Email;
        }

        public int getSI_State() {
            return SI_State;
        }

        public void setSI_State(int SI_State) {
            this.SI_State = SI_State;
        }
    }
}
