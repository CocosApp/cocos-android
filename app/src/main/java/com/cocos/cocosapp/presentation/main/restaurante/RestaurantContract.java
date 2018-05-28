package com.cocos.cocosapp.presentation.main.restaurante;

import com.cocos.cocosapp.core.BasePresenter;
import com.cocos.cocosapp.core.BaseView;
import com.cocos.cocosapp.data.entities.RestEntinty;
import com.cocos.cocosapp.data.entities.RestauranteResponse;
import com.cocos.cocosapp.data.entities.StatusColorEntity;

/**
 * Created by kath on 25/01/18.
 */

public interface RestaurantContract {
    interface View extends BaseView<Presenter> {

        void getRestaurante(RestEntinty restEntinty);

        void myFavoriteResponse(RestauranteResponse restauranteResponse);

        void getFavoriteStatusResponse(StatusColorEntity statusColorEntity);

        boolean isActive();
    }

    interface Presenter extends BasePresenter {

        void getRestaurante( int id);

        void sendMyFavoriteRestaurant(int id);

        void getFavoriteRestaurant(int idRestaurante);

    }
}
