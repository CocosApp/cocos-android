package com.cerezaconsulting.cocosapp.presentation.main.listrestaurante;

import com.cerezaconsulting.cocosapp.core.BasePresenter;
import com.cerezaconsulting.cocosapp.core.BaseView;
import com.cerezaconsulting.cocosapp.data.entities.RestEntinty;
import com.cerezaconsulting.cocosapp.data.entities.RestauranteResponse;
import com.cerezaconsulting.cocosapp.data.entities.SubCatEntity;

import java.util.ArrayList;

/**
 * Created by katherine on 12/05/17.
 */

public interface ListRestaurantContract {
    interface View extends BaseView<Presenter> {

        void clickItemRestaurante(RestauranteResponse restauranteResponse);

        void showMoreRestaurante(ArrayList<RestauranteResponse> list);

        void showRestaurante(ArrayList<RestauranteResponse> list);

        void showLoadMore(boolean active);

        boolean isActive();
    }

    interface Presenter extends BasePresenter {


        void startLoad(int id, String lat, String lng, int rad);

        void refreshList(int id, String lat, String lng, int rad);

        void loadList(int id, String lat, String lng, int rad);

        void loadList(int id, String lat, String lng, int rad, int page);
    }
}
