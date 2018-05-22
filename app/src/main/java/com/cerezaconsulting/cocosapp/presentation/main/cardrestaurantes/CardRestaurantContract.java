package com.cerezaconsulting.cocosapp.presentation.main.cardrestaurantes;

import com.cerezaconsulting.cocosapp.core.BasePresenter;
import com.cerezaconsulting.cocosapp.core.BaseView;
import com.cerezaconsulting.cocosapp.data.entities.CardRestResponse;
import com.cerezaconsulting.cocosapp.data.entities.RestauranteResponse;

import java.util.ArrayList;

/**
 * Created by katherine on 12/05/17.
 */

public interface CardRestaurantContract {
    interface View extends BaseView<Presenter> {


        void showMoreRestaurante(ArrayList<CardRestResponse> list);

        void showRestaurante(ArrayList<CardRestResponse> list);

        void showLoadMore(boolean active);


        void clickItemRestaurante(RestauranteResponse restauranteResponse);

        boolean isActive();
    }

    interface Presenter extends BasePresenter {

        void startLoad(int id);

        void refreshList(int id);

        void loadList(int id);

        void loadList(int id, int page);
    }
}
