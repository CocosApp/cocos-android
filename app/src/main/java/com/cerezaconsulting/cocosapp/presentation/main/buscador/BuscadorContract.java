package com.cerezaconsulting.cocosapp.presentation.main.buscador;

import com.cerezaconsulting.cocosapp.core.BasePresenter;
import com.cerezaconsulting.cocosapp.core.BaseView;
import com.cerezaconsulting.cocosapp.data.entities.RestEntinty;
import com.cerezaconsulting.cocosapp.data.entities.RestauranteResponse;
import com.cerezaconsulting.cocosapp.presentation.main.listrestaurante.ListRestaurantContract;

import java.util.ArrayList;

/**
 * Created by kath on 11/01/18.
 */

public class BuscadorContract {
    interface View extends BaseView<Presenter> {
        void clickItemRestaurante(RestauranteResponse restauranteResponse);

        void showMoreRestaurante(ArrayList<RestauranteResponse> list);

        void showRestaurante(ArrayList<RestauranteResponse> list);

        void showLoadMore(boolean active);

        boolean isActive();
    }

    interface Presenter extends BasePresenter {


        void refreshList(String search);

        void loadList(String search);

        void loadList(int page, String search);

        void startLoad(String search);

        void getRestaurante(int page, String search);



    }
}
