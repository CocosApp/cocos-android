package com.cocos.cocosapp.presentation.main.cardrestaurantes;


import com.cocos.cocosapp.data.entities.RestauranteResponse;

/**
 * Created by katherine on 24/04/17.
 */

public interface CardRestItem {

    void clickItem(RestauranteResponse restEntinty);

    void deleteItem(RestauranteResponse restEntinty, int position);
}
