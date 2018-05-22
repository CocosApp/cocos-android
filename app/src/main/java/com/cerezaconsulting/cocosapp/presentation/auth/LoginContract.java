package com.cerezaconsulting.cocosapp.presentation.auth;

import com.cerezaconsulting.cocosapp.core.BasePresenter;
import com.cerezaconsulting.cocosapp.core.BaseView;
import com.cerezaconsulting.cocosapp.data.entities.AccessTokenEntity;
import com.cerezaconsulting.cocosapp.data.entities.UserEntity;

/**
 * Created by katherine on 12/05/17.
 */

public interface LoginContract {
    interface View extends BaseView<Presenter> {
        void loginSuccessful(UserEntity userEntity);
        void errorLogin(String msg);
        void showDialogForgotPassword();
        void showSendEmail(String email);
        boolean isActive();
    }

    interface Presenter extends BasePresenter {
        void loginUser(String username, String password);
        void getProfile(AccessTokenEntity token);
        void loginUserFacebook(String token);
        void loginUserGoogle(String token);
        void openSession(AccessTokenEntity token, UserEntity userEntity);
        void sendEmail(String email);

    }
}
