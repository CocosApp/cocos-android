package com.cocos.cocosapp.presentation.main.home;

import com.cocos.cocosapp.core.BasePresenter;
import com.cocos.cocosapp.core.BaseView;
import com.cocos.cocosapp.data.entities.SubCatEntity;

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
