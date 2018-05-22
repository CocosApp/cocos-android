package com.cerezaconsulting.cocosapp.presentation.main.favoritos;


import com.cerezaconsulting.cocosapp.data.entities.RestEntinty;
import com.cerezaconsulting.cocosapp.data.entities.RestauranteResponse;

/**
 * Created by katherine on 24/04/17.
 */

public interface RestItem {

    void clickItem(RestauranteResponse restauranteResponse);

    void deleteItem(RestauranteResponse restauranteResponse, int position);
}
