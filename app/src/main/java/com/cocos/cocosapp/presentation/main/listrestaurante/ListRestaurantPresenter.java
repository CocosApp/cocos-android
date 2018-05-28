package com.cocos.cocosapp.presentation.main.listrestaurante;

import android.content.Context;

import com.cocos.cocosapp.data.entities.RestauranteResponse;
import com.cocos.cocosapp.data.entities.trackholder.TrackEntityHolder;
import com.cocos.cocosapp.data.local.SessionManager;
import com.cocos.cocosapp.data.remote.ServiceFactory;
import com.cocos.cocosapp.data.remote.request.ListRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by katherine on 28/06/17.
 */

public class ListRestaurantPresenter implements ListRestaurantContract.Presenter, RestItem {

    private ListRestaurantContract.View mView;
    private Context context;
    private SessionManager mSessionManager;
    private int currentPage = 1;
    private boolean mFirstLoad = false;


    public ListRestaurantPresenter(ListRestaurantContract.View mView, Context context) {
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
    public void startLoad(int id, String lat, String lng, int rad) {
        if (!mFirstLoad) {
            loadList(id, lat, lng, rad,1);
            mFirstLoad = true;
        }
    }

    @Override
    public void refreshList(int id, String lat, String lng, int rad) {

        currentPage = 1;
        loadList(id, lat,  lng,  rad ,1);
    }

    @Override
    public void loadList(int id, String lat, String lng, int rad) {
        loadList(id, lat, lng, rad,currentPage);

    }

    @Override
    public void loadList(int id,String lat, String lng, int rad, int page) {
        if (currentPage != -1) {
            ListRequest listRequest =
                    ServiceFactory.createService(ListRequest.class);
            Call<TrackEntityHolder<RestauranteResponse>> call = null;
            call = listRequest.getListRestaurantes("Token " + mSessionManager.getUserToken(),id, lat, lng, rad, currentPage);

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

                    mView.showErrorMessage("No se pudo cargar la data");
                }
            });
        }
    }


}
