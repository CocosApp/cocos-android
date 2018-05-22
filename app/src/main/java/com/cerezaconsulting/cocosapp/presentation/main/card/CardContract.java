package com.cerezaconsulting.cocosapp.presentation.main.card;

import com.cerezaconsulting.cocosapp.core.BasePresenter;
import com.cerezaconsulting.cocosapp.core.BaseView;
import com.cerezaconsulting.cocosapp.data.entities.CardEntity;
import com.cerezaconsulting.cocosapp.data.entities.SubCatEntity;

import java.util.ArrayList;

/**
 * Created by katherine on 12/05/17.
 */

public interface CardContract {
    interface View extends BaseView<Presenter> {


        void clickItemCard(CardEntity cardEntity);

        void showMorePost(ArrayList<CardEntity> list);

        void showPost(ArrayList<CardEntity> list);

        void showLoadMore(boolean active);



        boolean isActive();
    }

    interface Presenter extends BasePresenter {

        void refreshPost();

        void loadPost();

        void loadPost(int page);
        void startLoad();


    }
}
