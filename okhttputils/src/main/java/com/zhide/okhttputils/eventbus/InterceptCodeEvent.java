package com.zhide.okhttputils.eventbus;

/**
 * Created by hasee on 2018/3/6.
 */

public class InterceptCodeEvent {
    private int code;
    public InterceptCodeEvent(int code){
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
