package com.cerezaconsulting.cocosapp.presentation.main.buscador;


import com.cerezaconsulting.cocosapp.data.entities.RestEntinty;
import com.cerezaconsulting.cocosapp.data.entities.RestauranteResponse;

/**
 * Created by katherine on 24/04/17.
 */

public interface BuscadorItem {

    void clickItem(RestauranteResponse restEntinty);

    void deleteItem(RestauranteResponse restEntinty, int position);
}
