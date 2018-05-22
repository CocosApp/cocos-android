package com.cerezaconsulting.cocosapp.presentation.main.gpslist;

import android.content.Context;

import com.cerezaconsulting.cocosapp.data.entities.RestauranteResponse;
import com.cerezaconsulting.cocosapp.data.entities.trackholder.TrackEntityHolder;
import com.cerezaconsulting.cocosapp.data.local.SessionManager;
import com.cerezaconsulting.cocosapp.data.remote.ServiceFactory;
import com.cerezaconsulting.cocosapp.data.remote.request.ListRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by katherine on 28/06/17.
 */

public class GPSRestaurantPresenter implements GPSRestaurantContract.Presenter, RestItem {

    private GPSRestaurantContract.View mView;
    private Context context;
    private SessionManager mSessionManager;
    private int currentPage = 1;
    private boolean mFirstLoad = false;

    public GPSRestaurantPresenter(GPSRestaurantContract.View mView, Context context) {
        this.context = checkNotNull(context, "context cannot be null!");
        this.mView = checkNotNull(mView, "view cannot be null!");
        this.mView.setPresenter(this);
        this.mSessionManager = new SessionManager(this.context);
    }


    @Override
    public void start() {

    }


    @Override
    public void clickItem(RestauranteResponse restEntinty) {
        mView.clickItemRestaurante(restEntinty);
    }

    @Override
    public void deleteItem(RestauranteResponse restEntinty, int position) {

    }

    @Override
    public void startLoad(String latitude, String longitude) {

        if (!mFirstLoad) {
            loadList(latitude, longitude);
            mFirstLoad = true;
        }
    }

    @Override
    public void refreshList(String latitude, String longitude) {

        currentPage = 1;
        loadList(1, latitude, longitude);
    }

    @Override
    public void loadList(String latitude, String longitude) {
        loadList(currentPage, latitude, longitude);

    }

    @Override
    public void loadList(int page, String latitude, String longitude) {
        if (currentPage != -1) {
            ListRequest listRequest =
                    ServiceFactory.createService(ListRequest.class);
            Call<TrackEntityHolder<RestauranteResponse>> call = null;
            call = listRequest.getRestaurantesByGPS("Token "+mSessionManager.getUserToken(),
                    latitude, longitude , 3 ,currentPage);

            if (currentPage == 1) {
                mView.setLoadingIndicator(true);
            } else {
                mView.showLoadMore(true);
            }
            call.enqueue(new Callback<TrackEntityHolder<RestauranteResponse>>() {
                @Override
                public void onResponse(Call<TrackEntityHolder<RestauranteResponse>> call, Response<TrackEntityHolder<RestauranteResponse>> response) {


                    if (currentPage == 1) {
                        mView.setLoadingIndicator(false);
                    } else {
                        mView.showLoadMore(false);
                    }


                    if (response.isSuccessful()) {

                        if (!mView.isActive()) {
                            return;
                        }

                        if (currentPage == 1) {
                            mView.showRestaurante(response.body().getResults());
                        } else {
                            mView.showMoreRestaurante(response.body().getResults());
                        }

                        if (response.body().getNext() != null) {
                            currentPage++;
                        } else {
                            currentPage = -1;
                        }


                    } else {
                        if (!mView.isActive()) {
                            return;
                        }
                        mView.showErrorMessage("Ocurrió un error intentarlo nuevamente");
                    }
                }

                @Override
                public void onFailure(Call<TrackEntityHolder<RestauranteResponse>> call, Throwable t) {
                    if (!mView.isActive()) {
                        return;
                    }

                    if (currentPage == 1) {
                        mView.setLoadingIndicator(false);
                    } else {
                        mView.showLoadMore(false);
                    }

                    mView.showErrorMessage("Error al conectar con el servidor, intente de nuevo en unos minutos");
                }
            });
        }
    }
}
