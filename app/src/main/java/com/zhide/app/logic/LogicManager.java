package com.zhide.app.logic;

public class LogicManager {
    private static LogicManager instance = null;

    public static LogicManager getInstance() {
        if (instance == null) {
            instance = new LogicManager();
        }
        return instance;
    }

    /**
     * 登录模块-登录操作
     * @param userName
     * @param password
     */
    public void login(String userName, String password) {
        UserManager.getInstance().login(userName, password);
    }


}
