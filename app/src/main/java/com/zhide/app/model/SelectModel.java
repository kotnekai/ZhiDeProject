package com.zhide.app.model;

import java.io.Serializable;

/**
 * Created by hasee on 2018/4/3.
 */

public class SelectModel implements Serializable {
    private long id;// 选项id
    private String name;// 选项内容
    private boolean isCheck;

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public SelectModel() {
    }

    public SelectModel(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "SelectModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
