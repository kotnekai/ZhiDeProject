package com.zhide.app.logic;

import com.zhide.app.okhttp.DataManager;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginManager {
    private static LoginManager instance = null;
    private static DataManager dataInstance = null;

    public static LoginManager getInstance() {
        if (instance == null) {
            instance = new LoginManager();
        }
        if (dataInstance == null) {
            dataInstance = DataManager.getInstance();
        }
        return instance;
    }

    public void login(String userName, String password) {
        JSONObject params = new JSONObject();
        try {
            params.put("userName", userName);
            params.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
