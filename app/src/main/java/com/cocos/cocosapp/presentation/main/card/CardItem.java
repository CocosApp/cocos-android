package com.cocos.cocosapp.presentation.main.card;


import com.cocos.cocosapp.data.entities.CardEntity;

/**
 * Created by katherine on 24/04/17.
 */

public interface CardItem {

    void clickItem(CardEntity cardEntity);

    void deleteItem(CardEntity cardEntity, int position);
}
