package com.cerezaconsulting.cocosapp.presentation.register;


import android.support.annotation.NonNull;

import com.cerezaconsulting.cocosapp.core.BasePresenter;
import com.cerezaconsulting.cocosapp.core.BaseView;
import com.cerezaconsulting.cocosapp.data.entities.UserEntity;

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
