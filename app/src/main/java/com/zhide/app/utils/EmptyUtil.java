package com.zhide.app.utils;

import com.zhide.app.model.UserData;

/**
 * Created by hasee on 2018/6/7.
 */

public class EmptyUtil {

    public static boolean isEmpty(String content) {
        if (content == null) {
            return true;
        }
        if (content.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * 用户信息是否完善
     * @param userData
     * @return
     */
    public static boolean isCompleteInfo(UserData userData) {
        if (userData == null) {
            return false;
        }
        if (isEmpty(userData.getUSI_TrueName())||isEmpty(userData.getSI_Code())|| isEmpty(userData.getUSI_SchoolRoomNo())
                || isEmpty(userData.getUSI_SchoolNo())) {
            return false;
        }
        return true;
    }
}
