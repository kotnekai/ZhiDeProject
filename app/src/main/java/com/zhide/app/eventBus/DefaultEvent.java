package com.zhide.app.eventBus;

import com.zhide.app.model.DefaultEventModel;

public class DefaultEvent {
    private DefaultEventModel eventModel;

    public DefaultEvent(DefaultEventModel eventModel) {
        this.eventModel = eventModel;
    }

    public DefaultEventModel getEventModel() {
        return eventModel;
    }
}
