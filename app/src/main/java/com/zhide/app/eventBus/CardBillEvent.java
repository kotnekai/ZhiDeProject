package com.zhide.app.eventBus;

import com.zhide.app.model.CardBillModel;

/**
 * Create by Admin on 2018/9/7
 */
public class CardBillEvent {
    private CardBillModel cardBillModel;

    public CardBillEvent(CardBillModel cardBillModel) {
        this.cardBillModel = cardBillModel;
    }

    public CardBillModel getCardBillModel() {
        return cardBillModel;
    }
}
