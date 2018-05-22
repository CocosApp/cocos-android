package com.cerezaconsulting.cocosapp.presentation.main.restaurante;

import android.content.Context;

import com.cerezaconsulting.cocosapp.data.entities.IsMyFavouriteRestaurante;
import com.cerezaconsulting.cocosapp.data.entities.MessageResponse;
import com.cerezaconsulting.cocosapp.data.entities.RestEntinty;
import com.cerezaconsulting.cocosapp.data.entities.RestauranteResponse;
import com.cerezaconsulting.cocosapp.data.entities.StatusColorEntity;
import com.cerezaconsulting.cocosapp.data.local.SessionManager;
import com.cerezaconsulting.cocosapp.data.remote.ServiceFactory;
import com.cerezaconsulting.cocosapp.data.remote.request.ListRequest;
import com.cerezaconsulting.cocosapp.data.remote.request.PostRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by katherine on 28/06/17.
 */

public class RestaurantPresenter implements RestaurantContract.Presenter {

    private RestaurantContract.View mView;
    private Context context;
    private SessionManager mSessionManager;
    private boolean firstLoad = false;
    private int currentPage = 1;

    public RestaurantPresenter(RestaurantContract.View mView, Context context) {
        this.context = checkNotNull(context, "context cannot be null!");
        this.mView = checkNotNull(mView, "view cannot be null!");
        this.mView.setPresenter(this);
        this.mSessionManager = new SessionManager(this.context);
    }


    @Override
    public void start() {

    }



    @Override
    public void getRestaurante(int id) {
        mView.setLoadingIndicator(true);
        ListRequest listRequest = ServiceFactory.createService(ListRequest.class);
        Call<RestEntinty> orders = listRequest.getRestaurant("Token "+mSessionManager.getUserToken(),id);
        orders.enqueue(new Callback<RestEntinty>() {
            @Override
            public void onResponse(Call<RestEntinty> call, Response<RestEntinty> response) {

                if (!mView.isActive()) {
                    return;
                }
                mView.setLoadingIndicator(false);
                if (response.isSuccessful()) {

                    mView.getRestaurante(response.body());


                } else {
                    mView.showErrorMessage("Error al obtener datos del restaurante");
                }
            }

            @Override
            public void onFailure(Call<RestEntinty> call, Throwable t) {
                if (!mView.isActive()) {
                    return;
                }
                mView.setLoadingIndicator(false);
                mView.showErrorMessage("Error al conectar con el servidor");
            }
        });
    }

    @Override
    public void sendMyFavoriteRestaurant(int id) {
        mView.setLoadingIndicator(true);
        PostRequest postRequest = ServiceFactory.createService(PostRequest.class);
        Call<RestauranteResponse> orders = postRequest.sendMyFavorite("Token "+mSessionManager.getUserToken(),id);
        orders.enqueue(new Callback<RestauranteResponse>() {
            @Override
            public void onResponse(Call<RestauranteResponse> call, Response<RestauranteResponse> response) {

                if (!mView.isActive()) {
                    return;
                }
                mView.setLoadingIndicator(false);
                if (response.isSuccessful()) {

                    mView.myFavoriteResponse(response.body());


                } else {
                    mView.showErrorMessage("Usted ya cuenta con este restaurante como favorito");
                }
            }

            @Override
            public void onFailure(Call<RestauranteResponse> call, Throwable t) {
                if (!mView.isActive()) {
                    return;
                }
                mView.setLoadingIndicator(false);
                mView.showErrorMessage("Error al conectar con el servidor");
            }
        });
    }

    @Override
    public void getFavoriteRestaurant(int idRestaurante) {
        mView.setLoadingIndicator(true);
        ListRequest listRequest = ServiceFactory.createService(ListRequest.class);
        Call<StatusColorEntity> orders = listRequest.getMyFavoriteStatus("Token "+mSessionManager.getUserToken(), idRestaurante);
        orders.enqueue(new Callback<StatusColorEntity>() {
            @Override
            public void onResponse(Call<StatusColorEntity> call, Response<StatusColorEntity> response) {

                if (!mView.isActive()) {
                    return;
                }
                mView.setLoadingIndicator(false);
                if (response.isSuccessful()) {

                    mView.getFavoriteStatusResponse(response.body());


                } else {
                   // mView.showErrorMessage("Error al obtener datos del restaurante");
                }
            }

            @Override
            public void onFailure(Call<StatusColorEntity> call, Throwable t) {
                if (!mView.isActive()) {
                    return;
                }
                mView.setLoadingIndicator(false);
                mView.showErrorMessage("Error al conectar con el servidor");
            }
        });
    }
}
