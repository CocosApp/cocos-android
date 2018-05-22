package com.cerezaconsulting.cocosapp.presentation.profile;

import com.cerezaconsulting.cocosapp.core.BasePresenter;
import com.cerezaconsulting.cocosapp.core.BaseView;
import com.cerezaconsulting.cocosapp.data.entities.UploadResponse;
import com.cerezaconsulting.cocosapp.data.entities.UserEntity;

import java.io.File;

/**
 * Created by katherine on 21/06/17.
 */

public interface ProfileContract {
    interface View extends BaseView<Presenter> {
        void updateUser(UserEntity userEntity);
        void editSuccessful(UserEntity userEntity);
        void ShowSessionInformation(UserEntity userEntity);
        void updateProfileImage(UploadResponse body);
        boolean isActive();
    }

    interface Presenter extends BasePresenter {
        void editUser(UserEntity userEntity, String token);
        void updatePhoto( File image);

    }
}
