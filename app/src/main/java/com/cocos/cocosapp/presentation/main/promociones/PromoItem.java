package com.cocos.cocosapp.presentation.main.promociones;


import com.cocos.cocosapp.data.entities.DescEntity;

/**
 * Created by katherine on 24/04/17.
 */

public interface PromoItem {

    void clickItem(DescEntity descEntity);

    void deleteItem(DescEntity descEntity, int position);
}
