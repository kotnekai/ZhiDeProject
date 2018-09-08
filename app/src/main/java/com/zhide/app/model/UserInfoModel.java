package com.zhide.app.model;

/**
 * Create by Admin on 2018/9/8
 */
public class UserInfoModel {

    private int code;
    private String msg;
    private UserData data;

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

    public UserData getData() {
        return data;
    }

    public void setData(UserData data) {
        this.data = data;
    }

    public class UserData {

        private long USI_Id;//用户id
        private String USI_TrueName;// 姓名
        private String USI_SchoolNo;// 学号
        private String USI_AlipayAcc;// 支付宝账号
        private String SI_ID;//学校id
        private String USI_RegChannel;//注册渠道，苹果APP，安卓APP，PC后台网站
        private String USI_MainCardBalance;//卡的主余额（卡面金额）
        private String USI_State;//状态：注销，资料未完善，可用，冻结
        private String USI_Frozen;//冻结金额
        private String USI_Card_SN_PIN;//学生卡ID
        private String USI_ContactMobile;//学生联系手机号码
        private String USI_SchoolRoomNo;//房间号
        private String USI_Sex;//性别男，女
        private String USI_IDCard;//身份证号码
        private String USI_RegTime;//注册时间
        private String USI_UpdateTime;//更新资料时间
        private String USI_MainBalance;//主钱包
        private String USI_AllowTurnCardBalance;//允许转款到卡的余额
        private String USI_Mobile;//手机号码，主键唯一（登录账号）
        private String USI_SMSCode;//短信验证码
        private String USI_PayPwd;// 支付密码
        private String USI_AlipayName;//  支付宝姓名

        public long getUSI_Id() {
            return USI_Id;
        }

        public void setUSI_Id(long USI_Id) {
            this.USI_Id = USI_Id;
        }

        public String getUSI_TrueName() {
            return USI_TrueName;
        }

        public void setUSI_TrueName(String USI_TrueName) {
            this.USI_TrueName = USI_TrueName;
        }

        public String getUSI_SchoolNo() {
            return USI_SchoolNo;
        }

        public void setUSI_SchoolNo(String USI_SchoolNo) {
            this.USI_SchoolNo = USI_SchoolNo;
        }

        public String getUSI_AlipayAcc() {
            return USI_AlipayAcc;
        }

        public void setUSI_AlipayAcc(String USI_AlipayAcc) {
            this.USI_AlipayAcc = USI_AlipayAcc;
        }

        public String getSI_ID() {
            return SI_ID;
        }

        public void setSI_ID(String SI_ID) {
            this.SI_ID = SI_ID;
        }

        public String getUSI_RegChannel() {
            return USI_RegChannel;
        }

        public void setUSI_RegChannel(String USI_RegChannel) {
            this.USI_RegChannel = USI_RegChannel;
        }

        public String getUSI_MainCardBalance() {
            return USI_MainCardBalance;
        }

        public void setUSI_MainCardBalance(String USI_MainCardBalance) {
            this.USI_MainCardBalance = USI_MainCardBalance;
        }

        public String getUSI_State() {
            return USI_State;
        }

        public void setUSI_State(String USI_State) {
            this.USI_State = USI_State;
        }

        public String getUSI_Frozen() {
            return USI_Frozen;
        }

        public void setUSI_Frozen(String USI_Frozen) {
            this.USI_Frozen = USI_Frozen;
        }

        public String getUSI_Card_SN_PIN() {
            return USI_Card_SN_PIN;
        }

        public void setUSI_Card_SN_PIN(String USI_Card_SN_PIN) {
            this.USI_Card_SN_PIN = USI_Card_SN_PIN;
        }

        public String getUSI_ContactMobile() {
            return USI_ContactMobile;
        }

        public void setUSI_ContactMobile(String USI_ContactMobile) {
            this.USI_ContactMobile = USI_ContactMobile;
        }

        public String getUSI_SchoolRoomNo() {
            return USI_SchoolRoomNo;
        }

        public void setUSI_SchoolRoomNo(String USI_SchoolRoomNo) {
            this.USI_SchoolRoomNo = USI_SchoolRoomNo;
        }

        public String getUSI_Sex() {
            return USI_Sex;
        }

        public void setUSI_Sex(String USI_Sex) {
            this.USI_Sex = USI_Sex;
        }

        public String getUSI_IDCard() {
            return USI_IDCard;
        }

        public void setUSI_IDCard(String USI_IDCard) {
            this.USI_IDCard = USI_IDCard;
        }

        public String getUSI_RegTime() {
            return USI_RegTime;
        }

        public void setUSI_RegTime(String USI_RegTime) {
            this.USI_RegTime = USI_RegTime;
        }

        public String getUSI_UpdateTime() {
            return USI_UpdateTime;
        }

        public void setUSI_UpdateTime(String USI_UpdateTime) {
            this.USI_UpdateTime = USI_UpdateTime;
        }

        public String getUSI_MainBalance() {
            return USI_MainBalance;
        }

        public void setUSI_MainBalance(String USI_MainBalance) {
            this.USI_MainBalance = USI_MainBalance;
        }

        public String getUSI_AllowTurnCardBalance() {
            return USI_AllowTurnCardBalance;
        }

        public void setUSI_AllowTurnCardBalance(String USI_AllowTurnCardBalance) {
            this.USI_AllowTurnCardBalance = USI_AllowTurnCardBalance;
        }

        public String getUSI_Mobile() {
            return USI_Mobile;
        }

        public void setUSI_Mobile(String USI_Mobile) {
            this.USI_Mobile = USI_Mobile;
        }

        public String getUSI_SMSCode() {
            return USI_SMSCode;
        }

        public void setUSI_SMSCode(String USI_SMSCode) {
            this.USI_SMSCode = USI_SMSCode;
        }

        public String getUSI_PayPwd() {
            return USI_PayPwd;
        }

        public void setUSI_PayPwd(String USI_PayPwd) {
            this.USI_PayPwd = USI_PayPwd;
        }

        public String getUSI_AlipayName() {
            return USI_AlipayName;
        }

        public void setUSI_AlipayName(String USI_AlipayName) {
            this.USI_AlipayName = USI_AlipayName;
        }
    }


}
