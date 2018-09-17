package com.cocos.cocosapp.presentation.register;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.cocos.cocosapp.data.entities.AccessTokenEntity;
import com.cocos.cocosapp.data.entities.UserEntity;
import com.cocos.cocosapp.data.local.SessionManager;
import com.cocos.cocosapp.data.remote.ServiceFactory;
import com.cocos.cocosapp.data.remote.request.RegisterRequest;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by katherine on 3/05/17.
 */

public class RegisterPresenter implements RegisterContract.Presenter {

    private RegisterContract.View mView;
    private Context context;
    private SessionManager mSessionManager;
    private FirebaseAnalytics mFirebaseAnalytics;
    private String date, time;


    public RegisterPresenter(RegisterContract.View mView, Context context) {
        this.context = checkNotNull(context, "context cannot be null!");
        this.mView = checkNotNull(mView, "view cannot be null!");
        this.mView.setPresenter(this);
        this.mSessionManager = new SessionManager(this.context);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);

    }


    @Override
    public void start() {

    }

    @Override
    public void registerUser(@NonNull final UserEntity userEntity) {

        RegisterRequest registerRequest =
                ServiceFactory.createService(RegisterRequest.class);

        Call<AccessTokenEntity> call = registerRequest.registerUser(userEntity.getEmail(),
                userEntity.getFirst_name(), userEntity.getLast_name(),
                userEntity.getPassword());


        mView.setLoadingIndicator(true);
        call.enqueue(new Callback<AccessTokenEntity>() {
            @Override
            public void onResponse(Call<AccessTokenEntity> call, Response<AccessTokenEntity> response) {
                if (response.isSuccessful()) {
                    mView.setLoadingIndicator(false);
                    Bundle params = new Bundle();
                    params.putString("date", getDateAndTimeNow());
                    params.putString("label", "register");
                    mFirebaseAnalytics.logEvent("register", params);

                    mView.registerSuccessful(userEntity);

                } else {
                    mView.setLoadingIndicator(false);
                    mView.errorRegister("Falló el registro, por favor inténtelo nuevamente");
                }
            }
            @Override
            public void onFailure(Call<AccessTokenEntity> call, Throwable t) {
                mView.setLoadingIndicator(false);
                mView.showErrorMessage("Ocurrió un error al tratar de ingresar, por favor intente nuevamente");
            }
        });
    }

    private String getDateAndTimeNow(){
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        SimpleDateFormat sdfHora = new SimpleDateFormat("HH:mm", Locale.getDefault());

        Date now = new Date();

        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        cal.add(Calendar.MINUTE, 1);
        String newTime = sdfHora.format(cal.getTime());

        date = sdfDate.format(now).replace(".", "-");
        time = newTime.replace(":", ":");

        return  date + " " + time;

    }

}
