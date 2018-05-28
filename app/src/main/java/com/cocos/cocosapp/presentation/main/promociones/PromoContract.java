package com.cocos.cocosapp.presentation.main.promociones;

import com.cocos.cocosapp.core.BasePresenter;
import com.cocos.cocosapp.core.BaseView;
import com.cocos.cocosapp.data.entities.DescEntity;

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
