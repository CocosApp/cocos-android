package com.cerezaconsulting.cocosapp.presentation.main.card;


import com.cerezaconsulting.cocosapp.data.entities.CardEntity;
import com.cerezaconsulting.cocosapp.data.entities.SubCatEntity;

/**
 * Created by katherine on 24/04/17.
 */

public interface CardItem {

    void clickItem(CardEntity cardEntity);

    void deleteItem(CardEntity cardEntity, int position);
}
