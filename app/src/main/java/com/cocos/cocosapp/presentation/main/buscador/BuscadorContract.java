package com.cocos.cocosapp.presentation.main.buscador;

import com.cocos.cocosapp.core.BasePresenter;
import com.cocos.cocosapp.core.BaseView;
import com.cocos.cocosapp.data.entities.RestauranteResponse;

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
