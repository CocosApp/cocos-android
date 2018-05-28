package com.cocos.cocosapp.presentation.main.gpslist;

import com.cocos.cocosapp.core.BasePresenter;
import com.cocos.cocosapp.core.BaseView;
import com.cocos.cocosapp.data.entities.RestauranteResponse;

import java.util.ArrayList;

/**
 * Created by katherine on 12/05/17.
 */

public interface GPSRestaurantContract {
    interface View extends BaseView<Presenter> {

        void clickItemRestaurante(RestauranteResponse restauranteResponse);

        void showMoreRestaurante(ArrayList<RestauranteResponse> list);

        void showRestaurante(ArrayList<RestauranteResponse> list);

        void showLoadMore(boolean active);

        boolean isActive();
    }

    interface Presenter extends BasePresenter {


        void startLoad(String latitude, String longitude);

        void refreshList(String latitude, String longitude);

        void loadList(String latitude, String longitude);

        void loadList( int page, String latitude, String longitude);

    }
}
