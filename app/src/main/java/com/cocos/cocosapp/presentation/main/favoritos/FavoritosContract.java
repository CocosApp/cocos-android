package com.cocos.cocosapp.presentation.main.favoritos;

import com.cocos.cocosapp.core.BasePresenter;
import com.cocos.cocosapp.core.BaseView;
import com.cocos.cocosapp.data.entities.RestauranteResponse;

import java.util.ArrayList;

/**
 * Created by katherine on 12/05/17.
 */

public interface FavoritosContract {
    interface View extends BaseView<Presenter> {

        void getFavouritesRestaurantResponse(ArrayList<RestauranteResponse> list);

        void clickItemRestaurante(RestauranteResponse restauranteResponse);

        void responseDeleteRestaurants();

        boolean isActive();
    }

    interface Presenter extends BasePresenter {


        void startLoad();

        void getFavouritesRestaurants();

        void getSearch(String text);

        void deleteFavouritesRestaurants(int id);

    }
}
