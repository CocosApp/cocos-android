package com.cocos.cocosapp.presentation.main.home;


import com.cocos.cocosapp.core.BasePresenter;
import com.cocos.cocosapp.core.BaseView;
import com.cocos.cocosapp.data.entities.SubCatEntity;

import java.util.ArrayList;

/**
 * Especifica el contrato entre la vista y el presentador para logueo
 */
public interface SubcategoryContract {

    interface View extends BaseView<Presenter> {

        boolean isActive();

        void clickItemCategories(SubCatEntity cityEntity);


        void showMorePost(ArrayList<SubCatEntity> questionResponses);

        void showPost(ArrayList<SubCatEntity> questionResponses);

        void showLoadMore(boolean active);


    }

    interface Presenter extends BasePresenter {
        void refreshPost();

        void loadPost();

        void loadPost(int page);

    }
}
