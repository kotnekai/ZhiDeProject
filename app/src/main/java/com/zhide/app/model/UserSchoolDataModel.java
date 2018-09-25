package com.zhide.app.model;

/**
 * Create by Admin on 2018/9/13
 */
public class UserSchoolDataModel {
     private int code;
     private String message;
     private SchoolInfoData data;

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

    public SchoolInfoData getData() {
        return data;
    }

    public void setData(SchoolInfoData data) {
        this.data = data;
    }

    public class  SchoolInfoData{
       private long USI_Id;//用户id
       private String USI_TrueName;// 姓名
       private String USI_SchoolNo;// 学号
       private String USI_AlipayAcc;// 支付宝账号
       private Long SI_ID;//学校id
       private String USI_RegChannel;//注册渠道，苹果APP，安卓APP，PC后台网站
       private Float USI_MainCardBalance;//卡的主余额（卡面金额）
       private String USI_State;//状态：注销，资料未完善，可用，冻结
       private Float USI_Frozen;//冻结金额
       private String USI_Pwd;//登录密码
       private String USI_Card_SN_PIN;//学生卡ID
       private String USI_ContactMobile;//学生联系手机号码
       private String USI_SchoolRoomNo;//房间号
       private String USI_Sex;//性别男，女
       private String USI_IDCard;//身份证号码
       private String USI_RegTime;//注册时间
       private String USI_UpdateTime;//更新资料时间USI_UpdateTime
       private Float USI_MainBalance;//主钱包余额、、基本余额
       private String USI_AllowTurnCardBalance;//允许转款到卡的余额
       private String USI_Mobile;//手机号码，主键唯一（登录账号）
       private String USI_SMSCode;//短信验证码
       private String USI_PayPwd;// 支付密码
       private Long UCI_Id;//卡id
       private String USI_AlipayName;//  支付宝姓名
       private String SI_Code;//  扫学校二维码得出的guid
       private Float USI_GiftBalance;//  赠送余额
       private Float USI_TotalBalance;//  总余额 （基本余额+赠送余额）


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
       private Float SI_Deducting; //学校设定的预扣费金额，以里为单位如：5000 为5元
       private Float SI_WaterRate; //学校设定用水费率，以里为单位，如：5000 为5元






       public String getSI_Name() {
           return SI_Name;
       }

       public void setSI_Name(String SI_Name) {
           this.SI_Name = SI_Name;
       }

       public String getSI_Code() {
           return SI_Code;
       }

       public void setSI_Code(String SI_Code) {
           this.SI_Code = SI_Code;
       }

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


       public String getUSI_RegChannel() {
           return USI_RegChannel;
       }

       public void setUSI_RegChannel(String USI_RegChannel) {
           this.USI_RegChannel = USI_RegChannel;
       }


       public String getUSI_State() {
           return USI_State;
       }

       public void setUSI_State(String USI_State) {
           this.USI_State = USI_State;
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

       public String getUSI_Pwd() {
           return USI_Pwd;
       }

       public void setUSI_Pwd(String USI_Pwd) {
           this.USI_Pwd = USI_Pwd;
       }

       public Long getSI_ID() {
           return SI_ID;
       }

       public void setSI_ID(Long SI_ID) {
           this.SI_ID = SI_ID;
       }

       public Float getUSI_MainCardBalance() {
           return USI_MainCardBalance;
       }

       public void setUSI_MainCardBalance(Float USI_MainCardBalance) {
           this.USI_MainCardBalance = USI_MainCardBalance;
       }

       public Float getUSI_Frozen() {
           return USI_Frozen;
       }

       public void setUSI_Frozen(Float USI_Frozen) {
           this.USI_Frozen = USI_Frozen;
       }

       public Float getUSI_MainBalance() {
           return USI_MainBalance;
       }

       public void setUSI_MainBalance(Float USI_MainBalance) {
           this.USI_MainBalance = USI_MainBalance;
       }

       public Long getUCI_Id() {
           return UCI_Id;
       }

       public void setUCI_Id(Long UCI_Id) {
           this.UCI_Id = UCI_Id;
       }

       public Float getUSI_GiftBalance() {
           return USI_GiftBalance;
       }

       public void setUSI_GiftBalance(Float USI_GiftBalance) {
           this.USI_GiftBalance = USI_GiftBalance;
       }

       public Float getUSI_TotalBalance() {
           return USI_TotalBalance;
       }

       public void setUSI_TotalBalance(Float USI_TotalBalance) {
           this.USI_TotalBalance = USI_TotalBalance;
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

       public Float getSI_Deducting() {
           return SI_Deducting;
       }

       public void setSI_Deducting(Float SI_Deducting) {
           this.SI_Deducting = SI_Deducting;
       }

       public Float getSI_WaterRate() {
           return SI_WaterRate;
       }

       public void setSI_WaterRate(Float SI_WaterRate) {
           this.SI_WaterRate = SI_WaterRate;
       }
   }

}
