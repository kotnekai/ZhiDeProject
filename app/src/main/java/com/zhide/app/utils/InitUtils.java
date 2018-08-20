package com.zhide.app.utils;

import com.zhide.app.model.SpinnerSelectModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by Admin on 2018/8/20
 */
public class InitUtils {

    public static List<SpinnerSelectModel> getRepairDeviceList() {
        List<SpinnerSelectModel> deviceList = new ArrayList<>();
        deviceList.add(new SpinnerSelectModel(10, "热水表"));
        deviceList.add(new SpinnerSelectModel(11, "电表"));
        return deviceList;
    }

    public static List<SpinnerSelectModel> getRepairReasonList() {
        List<SpinnerSelectModel> reasonList = new ArrayList<>();
        reasonList.add(new SpinnerSelectModel(1, "水表不出水"));
        reasonList.add(new SpinnerSelectModel(2, "水表链接不上"));
        reasonList.add(new SpinnerSelectModel(3, "水表损坏"));
        return reasonList;

    }
}
