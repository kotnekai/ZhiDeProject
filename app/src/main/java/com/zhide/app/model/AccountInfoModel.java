package com.zhide.app.model;

/**
 * Create by Admin on 2018/8/20
 */
public class AccountInfoModel {
    private float totalMoney;//账户余额
    private float baseMoney;// 基本余额
    private float giftMoney;// 赠送余额
    private String userName;//姓名
    private String schoolName;//校区
    private String gender;//性别
    private String StudentId;//学号
    private String IdCardNumber;//身份证号

    public float getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(float totalMoney) {
        this.totalMoney = totalMoney;
    }

    public float getBaseMoney() {
        return baseMoney;
    }

    public void setBaseMoney(float baseMoney) {
        this.baseMoney = baseMoney;
    }

    public float getGiftMoney() {
        return giftMoney;
    }

    public void setGiftMoney(float giftMoney) {
        this.giftMoney = giftMoney;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getStudentId() {
        return StudentId;
    }

    public void setStudentId(String studentId) {
        StudentId = studentId;
    }

    public String getIdCardNumber() {
        return IdCardNumber;
    }

    public void setIdCardNumber(String idCardNumber) {
        IdCardNumber = idCardNumber;
    }
}
