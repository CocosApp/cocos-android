package com.cerezaconsulting.cocosapp.presentation.main.favoritos;

import android.content.Context;

import com.cerezaconsulting.cocosapp.data.entities.FavouriteResponse;
import com.cerezaconsulting.cocosapp.data.entities.RestEntinty;
import com.cerezaconsulting.cocosapp.data.entities.RestauranteResponse;
import com.cerezaconsulting.cocosapp.data.entities.trackholder.TrackEntityHolder;
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

public class FavoritosPresenter implements FavoritosContract.Presenter, RestItem {

    private FavoritosContract.View mView;
    private Context context;
    private SessionManager mSessionManager;
    private boolean firstLoad = false;
    private int currentPage = 1;

    public FavoritosPresenter(FavoritosContract.View mView, Context context) {
        this.context = checkNotNull(context, "context cannot be null!");
        this.mView = checkNotNull(mView, "view cannot be null!");
        this.mView.setPresenter(this);
        this.mSessionManager = new SessionManager(this.context);
    }


    @Override
    public void start() {

    }


    @Override
    public void clickItem(RestauranteResponse restauranteResponse) {
        mView.clickItemRestaurante(restauranteResponse);
    }

    @Override
    public void deleteItem(RestauranteResponse restEntinty, int position) {
        deleteFavouritesRestaurants(restEntinty.getId());
    }


    @Override
    public void startLoad() {
        getFavouritesRestaurants();
    }

    @Override
    public void getFavouritesRestaurants() {
        mView.setLoadingIndicator(true);
        ListRequest listRequest = ServiceFactory.createService(ListRequest.class);
        Call<FavouriteResponse> orders = listRequest.getMyFavouriteRestaurantes("Token "+mSessionManager.getUserToken());
        orders.enqueue(new Callback<FavouriteResponse>() {
            @Override
            public void onResponse(Call<FavouriteResponse> call, Response<FavouriteResponse> response) {

                if (!mView.isActive()) {
                    return;
                }
                mView.setLoadingIndicator(false);
                if (response.isSuccessful()) {
                    mView.getFavouritesRestaurantResponse(response.body().getFav_restaurant());

                } else {
                    mView.showErrorMessage("Error al obtener las órdenes");
                }
            }

            @Override
            public void onFailure(Call<FavouriteResponse> call, Throwable t) {
                if (!mView.isActive()) {
                    return;
                }
                mView.setLoadingIndicator(false);
                mView.showErrorMessage("Error al conectar con el servidor");
            }
        });
    }

    @Override
    public void getSearch(String text) {
        mView.setLoadingIndicator(true);
        ListRequest listRequest = ServiceFactory.createService(ListRequest.class);
        Call<TrackEntityHolder<RestauranteResponse>> orders = listRequest.getSearchFavoritesRestaurantes("Token "+mSessionManager.getUserToken(), text);
        orders.enqueue(new Callback<TrackEntityHolder<RestauranteResponse>>() {
            @Override
            public void onResponse(Call<TrackEntityHolder<RestauranteResponse>> call, Response<TrackEntityHolder<RestauranteResponse>> response) {

                if (!mView.isActive()) {
                    return;
                }
                mView.setLoadingIndicator(false);
                if (response.isSuccessful()) {

                    mView.getFavouritesRestaurantResponse(response.body().getResults());

                } else {
                    mView.showErrorMessage("Error al obtener las órdenes");
                }
            }

            @Override
            public void onFailure(Call<TrackEntityHolder<RestauranteResponse>> call, Throwable t) {
                if (!mView.isActive()) {
                    return;
                }
                mView.setLoadingIndicator(false);
                mView.showErrorMessage("Error al conectar con el servidor");
            }
        });
    }

    @Override
    public void deleteFavouritesRestaurants(int id) {
        mView.setLoadingIndicator(true);
        PostRequest listRequest = ServiceFactory.createService(PostRequest.class);
        Call<Void> orders = listRequest.sendNoFavorite("Token "+mSessionManager.getUserToken(), id);
        orders.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                if (!mView.isActive()) {
                    return;
                }
                mView.setLoadingIndicator(false);
                if (response.isSuccessful()) {

                    mView.responseDeleteRestaurants();

                } else {
                    mView.showErrorMessage("Error al eliminar de favoritos");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                if (!mView.isActive()) {
                    return;
                }
                mView.setLoadingIndicator(false);
                mView.showErrorMessage("Error al conectar con el servidor");
            }
        });
    }
}
