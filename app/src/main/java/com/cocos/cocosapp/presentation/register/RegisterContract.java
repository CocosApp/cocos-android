package com.cocos.cocosapp.presentation.register;


import android.support.annotation.NonNull;

import com.cocos.cocosapp.core.BasePresenter;
import com.cocos.cocosapp.core.BaseView;
import com.cocos.cocosapp.data.entities.UserEntity;

/**
 * Created by katherine on 3/05/17.
 */

public interface RegisterContract {
    interface View extends BaseView<Presenter> {

        void registerSuccessful(UserEntity userEntity);
        void errorRegister(String msg);
        boolean isActive();
    }

    interface Presenter extends BasePresenter {
        void registerUser(@NonNull UserEntity userEntity);

    }
}
