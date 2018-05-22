package com.cerezaconsulting.cocosapp.presentation.main.home;

import com.cerezaconsulting.cocosapp.core.BasePresenter;
import com.cerezaconsulting.cocosapp.core.BaseView;
import com.cerezaconsulting.cocosapp.data.entities.SubCatEntity;

import java.util.ArrayList;

/**
 * Created by katherine on 12/05/17.
 */

public interface CategoryContract {
    interface View extends BaseView<Presenter> {

        void getListCategories(ArrayList<SubCatEntity> list);

        void clickItemCategories(SubCatEntity cityEntity);

        boolean isActive();
    }

    interface Presenter extends BasePresenter {


        void loadOrdersFromPage(int page);

        void loadfromNextPage();

        void startLoad();

        void getCategories(int page);

    }
}
