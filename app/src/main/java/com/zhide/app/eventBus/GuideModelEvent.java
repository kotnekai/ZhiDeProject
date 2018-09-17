package com.zhide.app.eventBus;

import com.zhide.app.model.GuideModel;

/**
 * Create by Admin on 2018/9/14
 */
public class GuideModelEvent {
    private GuideModel guideModel;

    public GuideModelEvent(GuideModel guideModel) {
        this.guideModel = guideModel;
    }

    public GuideModel getGuideModel() {
        return guideModel;
    }
}
