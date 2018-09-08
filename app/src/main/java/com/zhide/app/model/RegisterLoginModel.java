package com.zhide.app.model;

/**
 * Create by Admin on 2018/9/7
 */
public class RegisterLoginModel {
    private int code;
    private String message;
    private UserModel data;

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

    public UserModel getData() {
        return data;
    }

    public void setData(UserModel data) {
        this.data = data;
    }

    public class UserModel {
        private long USI_Id;
        private String USI_Mobile;

        public long getUSI_Id() {
            return USI_Id;
        }

        public void setUSI_Id(long USI_Id) {
            this.USI_Id = USI_Id;
        }

        public String getUSI_Mobile() {
            return USI_Mobile;
        }

        public void setUSI_Mobile(String USI_Mobile) {
            this.USI_Mobile = USI_Mobile;
        }

        @Override
        public String toString() {
            return "UserModel{" +
                    "USI_Id=" + USI_Id +
                    ", USI_Mobile='" + USI_Mobile + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "RegisterModel{" +
                "code=" + code +
                ", msg='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
