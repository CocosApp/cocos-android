package com.cocos.cocosapp.presentation.main.favoritos;


import com.cocos.cocosapp.data.entities.RestauranteResponse;

/**
 * Created by katherine on 24/04/17.
 */

public interface RestItem {

    void clickItem(RestauranteResponse restauranteResponse);

    void deleteItem(RestauranteResponse restauranteResponse, int position);
}
