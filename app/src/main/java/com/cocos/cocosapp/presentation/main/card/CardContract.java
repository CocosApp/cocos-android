package com.cocos.cocosapp.presentation.main.card;

import com.cocos.cocosapp.core.BasePresenter;
import com.cocos.cocosapp.core.BaseView;
import com.cocos.cocosapp.data.entities.CardEntity;

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
