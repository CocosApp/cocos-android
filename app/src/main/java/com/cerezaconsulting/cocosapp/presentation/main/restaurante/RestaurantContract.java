package com.cerezaconsulting.cocosapp.presentation.main.restaurante;

import com.cerezaconsulting.cocosapp.core.BasePresenter;
import com.cerezaconsulting.cocosapp.core.BaseView;
import com.cerezaconsulting.cocosapp.data.entities.IsMyFavouriteRestaurante;
import com.cerezaconsulting.cocosapp.data.entities.MessageResponse;
import com.cerezaconsulting.cocosapp.data.entities.RestEntinty;
import com.cerezaconsulting.cocosapp.data.entities.RestauranteResponse;
import com.cerezaconsulting.cocosapp.data.entities.StatusColorEntity;

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
