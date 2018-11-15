package com.zhide.app.model;

import java.util.List;

/**
 * Create by Admin on 2018/11/15
 */
public class RoomInfoModel {
    private int code;
    private String message;
    private List<DataModel> data;

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

    public List<DataModel> getData() {
        return data;
    }

    public void setData(List<DataModel> data) {
        this.data = data;
    }

    public class DataModel {
        private String SDI_Name;
        private String SDI_UpdateTime;
        private String SDI_UpdateBy;
        private long SDI_Id;
        private String SDI_Remark;
        private long SI_Id;
        private String SDI_Type;
        private String SDI_NatureType;
        private long SDI_ParentId;
        private String SI_Name;

        public String getSDI_Name() {
            return SDI_Name;
        }

        public void setSDI_Name(String SDI_Name) {
            this.SDI_Name = SDI_Name;
        }

        public String getSDI_UpdateTime() {
            return SDI_UpdateTime;
        }

        public void setSDI_UpdateTime(String SDI_UpdateTime) {
            this.SDI_UpdateTime = SDI_UpdateTime;
        }

        public String getSDI_UpdateBy() {
            return SDI_UpdateBy;
        }

        public void setSDI_UpdateBy(String SDI_UpdateBy) {
            this.SDI_UpdateBy = SDI_UpdateBy;
        }

        public long getSDI_Id() {
            return SDI_Id;
        }

        public void setSDI_Id(long SDI_Id) {
            this.SDI_Id = SDI_Id;
        }

        public String getSDI_Remark() {
            return SDI_Remark;
        }

        public void setSDI_Remark(String SDI_Remark) {
            this.SDI_Remark = SDI_Remark;
        }

        public long getSI_Id() {
            return SI_Id;
        }

        public void setSI_Id(long SI_Id) {
            this.SI_Id = SI_Id;
        }

        public String getSDI_Type() {
            return SDI_Type;
        }

        public void setSDI_Type(String SDI_Type) {
            this.SDI_Type = SDI_Type;
        }

        public String getSDI_NatureType() {
            return SDI_NatureType;
        }

        public void setSDI_NatureType(String SDI_NatureType) {
            this.SDI_NatureType = SDI_NatureType;
        }

        public long getSDI_ParentId() {
            return SDI_ParentId;
        }

        public void setSDI_ParentId(long SDI_ParentId) {
            this.SDI_ParentId = SDI_ParentId;
        }

        public String getSI_Name() {
            return SI_Name;
        }

        public void setSI_Name(String SI_Name) {
            this.SI_Name = SI_Name;
        }
    }
}
