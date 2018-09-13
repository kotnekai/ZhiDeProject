package com.zhide.app.eventBus;

import com.zhide.app.model.NewsModel;

/**
 * Create by Admin on 2018/9/13
 */
public class NewsModelEvent {
    private NewsModel newsModel;
    private int fromPage;

    public NewsModelEvent(NewsModel newsModel, int fromPage) {
        this.fromPage = fromPage;
        this.newsModel = newsModel;
    }

    public int getFromPage() {
        return fromPage;
    }

    public NewsModel getNewsModel() {
        return newsModel;
    }
}
