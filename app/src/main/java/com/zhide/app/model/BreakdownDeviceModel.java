package com.zhide.app.model;

import java.util.List;

/**
 * Create by Admin on 2018/9/18
 */
public class BreakdownDeviceModel {
    private String ZBI_DeviceType;
    private List<BreakType> ZBI_Types;

    public String getZBI_DeviceType() {
        return ZBI_DeviceType;
    }

    public void setZBI_DeviceType(String ZBI_DeviceType) {
        this.ZBI_DeviceType = ZBI_DeviceType;
    }

    public List<BreakType> getZBI_Types() {
        return ZBI_Types;
    }

    public void setZBI_Types(List<BreakType> ZBI_Types) {
        this.ZBI_Types = ZBI_Types;
    }

    public class BreakType{
        private String ZBI_Type;

        public String getZBI_Type() {
            return ZBI_Type;
        }

        public void setZBI_Type(String ZBI_Type) {
            this.ZBI_Type = ZBI_Type;
        }
    }
}
