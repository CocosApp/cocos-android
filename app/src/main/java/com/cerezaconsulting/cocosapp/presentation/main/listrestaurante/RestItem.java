package com.cerezaconsulting.cocosapp.presentation.main.listrestaurante;


import com.cerezaconsulting.cocosapp.data.entities.RestEntinty;
import com.cerezaconsulting.cocosapp.data.entities.RestauranteResponse;

/**
 * Created by katherine on 24/04/17.
 */

public interface RestItem {

    void clickItem(RestauranteResponse restEntinty);

    void deleteItem(RestauranteResponse restEntinty, int position);
}
