package com.cocos.cocosapp.presentation.auth;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.cocos.cocosapp.data.entities.AccessTokenEntity;
import com.cocos.cocosapp.data.entities.UserEntity;
import com.cocos.cocosapp.data.local.SessionManager;
import com.cocos.cocosapp.data.remote.ServiceFactory;
import com.cocos.cocosapp.data.remote.request.LoginRequest;
import com.cocos.cocosapp.data.remote.request.PostRequest;
import com.facebook.AccessToken;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.iid.FirebaseInstanceId;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by katherine on 10/05/17.
 */

public class LoginPresenter implements LoginContract.Presenter {

    private final LoginContract.View mView;
    private Context context;
    private final SessionManager mSessionManager;
    private FirebaseAnalytics mFirebaseAnalytics;
    private String date, time;
    private boolean isFb = false;
    private boolean isLogin = false;
    private boolean isGmail = false;

    public LoginPresenter(@NonNull LoginContract.View mView, @NonNull Context context) {
        this.context = checkNotNull(context, "context cannot be null!");
        this.mView = checkNotNull(mView, "newsView cannot be null!");
        this.mView.setPresenter(this);
        mSessionManager = new SessionManager(context);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);

    }

    @Override
    public void loginUser(final String username, String password) {
        LoginRequest loginService =
                ServiceFactory.createService(LoginRequest.class);
        Call<AccessTokenEntity> call = loginService.login(username,password);
        mView.setLoadingIndicator(true);
        call.enqueue(new Callback<AccessTokenEntity>() {
            @Override
            public void onResponse(Call<AccessTokenEntity> call, Response<AccessTokenEntity> response) {
                if(!mView.isActive()){
                    return;
                }
                if (response.isSuccessful()) {
                    getProfile(response.body());
                    isLogin = true;

                } else {
                    mView.setLoadingIndicator(false);
                    mView.errorLogin("Login Fallido");
                }
            }

            @Override
            public void onFailure(Call<AccessTokenEntity> call, Throwable t) {
                if(!mView.isActive()){
                    return;
                }
                mView.setLoadingIndicator(false);
                mView.errorLogin("No se puede conectar al servidor");
            }
        });
    }

    @Override
    public void getProfile(final AccessTokenEntity token) {
        LoginRequest loginService =
                ServiceFactory.createService(LoginRequest.class);
        Call<UserEntity> call = loginService.getUser("Token "+ token.getAccessToken());
        call.enqueue(new Callback<UserEntity>() {
            @Override
            public void onResponse(Call<UserEntity> call, Response<UserEntity> response) {
                if (response.isSuccessful()) {
                    if (!mView.isActive()) {
                        return;
                    }
                    openSession(token, response.body());

                } else {
                    if (!mView.isActive()) {
                        return;
                    }
                    mView.setLoadingIndicator(false);
                    mView.errorLogin("Ocurrió un error al cargar su perfil");
                }
            }

            @Override
            public void onFailure(Call<UserEntity> call, Throwable t) {
                if (!mView.isActive()) {
                    return;
                }
                mView.setLoadingIndicator(false);
                mView.errorLogin("Fallo al traer datos, comunicarse con su administrador");
            }
        });
    }

    @Override
    public void loginUserFacebook(String token) {
        LoginRequest loginService =
                ServiceFactory.createService(LoginRequest.class);

        Call<AccessTokenEntity> call = loginService.loginUserFacebook(token);
        mView.setLoadingIndicator(true);
        call.enqueue(new Callback<AccessTokenEntity>() {
            @Override
            public void onResponse(Call<AccessTokenEntity> call, Response<AccessTokenEntity> response) {
                if (!mView.isActive()) {
                    return;
                }
                if (response.isSuccessful()) {
                   isFb = true;
                    getProfile(response.body());
                } else {
                    mView.setLoadingIndicator(false);
                    AccessToken.setCurrentAccessToken(null);
                    mView.errorLogin("Login Fallido, puede que el correo vinculado a su " +
                            "facebook ya este registrado ");

                }
            }

            @Override
            public void onFailure(Call<AccessTokenEntity> call, Throwable t) {
                if (!mView.isActive()) {
                    return;
                }
                mView.setLoadingIndicator(false);
                AccessToken.setCurrentAccessToken(null);
                mView.errorLogin("Ocurrió un error al entrar, por favor intente nuevamente");
            }
        });
    }

    @Override
    public void loginUserGoogle(String token) {
        LoginRequest loginService =
                ServiceFactory.createService(LoginRequest.class);

        Call<AccessTokenEntity> call = loginService.loginGmail(token);
        mView.setLoadingIndicator(true);
        call.enqueue(new Callback<AccessTokenEntity>() {
            @Override
            public void onResponse(Call<AccessTokenEntity> call, Response<AccessTokenEntity> response) {
                if (!mView.isActive()) {
                    return;
                }
                if(response.isSuccessful()){
                    isGmail = true;
                    getProfile(response.body());
                }
                else {
                    mView.setLoadingIndicator(false);
                    AccessToken.setCurrentAccessToken(null);
                    mView.showMessage("No se pudo iniciar sesión, por favor inténtelo nuevamente");
                }
            }

            @Override
            public void onFailure(Call<AccessTokenEntity> call, Throwable t) {
                if (!mView.isActive()) {
                    return;
                }

                mView.setLoadingIndicator(false);
                AccessToken.setCurrentAccessToken(null);
                mView.showErrorMessage("Ocurrió un error al inicar sesión, por favor inténtelo nuevamente");
            }
        });

    }


    @Override
    public void openSession(AccessTokenEntity token, UserEntity userEntity) {

        mSessionManager.openSession(token);
        mSessionManager.setUser(userEntity);
        mView.setLoadingIndicator(false);
        mView.loginSuccessful(userEntity);
        sendFCM();
        sendDataToAnalitycs(userEntity);
    }


    private void sendDataToAnalitycs(UserEntity userEntity){
        if(isFb){
            Bundle params = new Bundle();
            params.putInt("id_user", userEntity.getId());
            params.putString("name_user", userEntity.getFullName());
            params.putString("date", getDateAndTimeNow());
            params.putString("label", "login_fb");
            params.putString("so", "android");
            mFirebaseAnalytics.logEvent("login_fb", params);
            isFb = false;
        }

        if(isLogin){
            if(!userEntity.getEmail().equals("invitado@gmail.com")){
                Bundle params = new Bundle();
                params.putInt("id_user", userEntity.getId());
                params.putString("name_user", userEntity.getFullName());
                params.putString("date", getDateAndTimeNow());
                params.putString("label", "login");
                mFirebaseAnalytics.logEvent("login", params);
            }else {
                Bundle params = new Bundle();
                params.putString("date", getDateAndTimeNow());
                params.putString("label", "login_guest");
                mFirebaseAnalytics.logEvent("login_guest", params);
            }
            isLogin = false;
        }

        if(isGmail){
            Bundle params = new Bundle();
            params.putInt("id_user", userEntity.getId());
            params.putString("name_user", userEntity.getFullName());
            params.putString("date", getDateAndTimeNow());
            params.putString("label", "login_gmail");
            mFirebaseAnalytics.logEvent("login_gmail", params);
            isGmail = false;
        }
    }

    @Override
    public void sendEmail(String email) {
        LoginRequest loginRequest =
                ServiceFactory.createService(LoginRequest.class);
        Call<UserEntity> call = loginRequest.recovery(email);
        call.enqueue(new Callback<UserEntity>() {
            @Override
            public void onResponse(Call<UserEntity> call, Response<UserEntity> response) {
                if (response.isSuccessful()) {
                    if (!mView.isActive()) {
                        return;
                    }
                    mView.showMessage("Se envió un correo con instrucciones");
                } else {
                    if (!mView.isActive()) {
                        return;
                    }
                    mView.setLoadingIndicator(false);
                    mView.showErrorMessage("Ocurrió un error al enviar el correo a su dirección");
                }
            }

            @Override
            public void onFailure(Call<UserEntity> call, Throwable t) {
                if (!mView.isActive()) {
                    return;
                }
                mView.setLoadingIndicator(false);
                mView.showErrorMessage("Fallo al traer datos, comunicarse con su administrador");
            }


        });
    }

    public void sendFCM() {
        PostRequest postRequest =
                ServiceFactory.createService(PostRequest.class);
        Call<Void> call = postRequest.sendMyFCM("Token "+mSessionManager.getUserToken(), mSessionManager.getUserEntity().getFullName(),
                String.valueOf(FirebaseInstanceId.getInstance().getToken()), mSessionManager.getUserEntity().getFullName(),
                "android");
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!mView.isActive()) {
                    return;
                }
                if (response.isSuccessful()) {

                    mView.showMessage("FCM agregado correctamente");
                } else {

                    mView.showErrorMessage("Ocurrió un error al enviar datos del equipo");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                if (!mView.isActive()) {
                    return;
                }
                mView.setLoadingIndicator(false);
                mView.showErrorMessage("Fallo al traer datos, comunicarse con su administrador");
            }


        });
    }
    @Override
    public void start() {

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
