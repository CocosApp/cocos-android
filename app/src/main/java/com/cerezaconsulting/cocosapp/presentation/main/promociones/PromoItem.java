package com.cerezaconsulting.cocosapp.presentation.main.promociones;


import com.cerezaconsulting.cocosapp.data.entities.DescEntity;
import com.cerezaconsulting.cocosapp.data.entities.RestEntinty;

/**
 * Created by katherine on 24/04/17.
 */

public interface PromoItem {

    void clickItem(DescEntity descEntity);

    void deleteItem(DescEntity descEntity, int position);
}
