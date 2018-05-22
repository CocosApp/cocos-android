package com.cerezaconsulting.cocosapp.presentation.main.promociones;

import com.cerezaconsulting.cocosapp.core.BasePresenter;
import com.cerezaconsulting.cocosapp.core.BaseView;
import com.cerezaconsulting.cocosapp.data.entities.DescEntity;
import com.cerezaconsulting.cocosapp.data.entities.RestEntinty;

import java.util.ArrayList;

/**
 * Created by katherine on 12/05/17.
 */

public interface PromoContract {
    interface View extends BaseView<Presenter> {

        void getListPromo(ArrayList<DescEntity> list);

        void clickItemPromo(DescEntity descEntity);

        boolean isActive();
    }

    interface Presenter extends BasePresenter {


      /*  void loadOrdersFromPage(int page, int id);

        void loadfromNextPage(int id);

        void startLoad(int id);

        void getPromos(int page, int id);*/

    }
}
