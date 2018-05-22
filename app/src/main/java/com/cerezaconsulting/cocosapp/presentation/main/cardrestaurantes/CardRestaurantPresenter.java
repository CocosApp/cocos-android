package com.cerezaconsulting.cocosapp.presentation.main.cardrestaurantes;

import android.content.Context;

import com.cerezaconsulting.cocosapp.data.entities.CardRestResponse;
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

public class CardRestaurantPresenter implements CardRestaurantContract.Presenter, CardRestItem {

    private CardRestaurantContract.View mView;
    private Context context;
    private SessionManager mSessionManager;
    private int currentPage = 1;
    private boolean mFirstLoad = false;

    public CardRestaurantPresenter(CardRestaurantContract.View mView, Context context) {
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
    public void startLoad(int id) {
        if (!mFirstLoad) {
            loadList(id, 1);
            mFirstLoad = true;
        }
    }

    @Override
    public void refreshList(int id) {

        currentPage = 1;
        loadList(id, 1);
    }

    @Override
    public void loadList(int id) {
        loadList(id, currentPage);

    }

    @Override
    public void loadList(int id, int page) {
        if (currentPage != -1) {
            ListRequest listRequest =
                    ServiceFactory.createService(ListRequest.class);
            Call<TrackEntityHolder<CardRestResponse>> call = null;
            call = listRequest.getCardsRestaurantes("Token " + mSessionManager.getUserToken(),id, currentPage);

            if (currentPage == 1) {
                mView.setLoadingIndicator(true);
            } else {
                mView.showLoadMore(true);
            }
            call.enqueue(new Callback<TrackEntityHolder<CardRestResponse>>() {
                @Override
                public void onResponse(Call<TrackEntityHolder<CardRestResponse>> call, Response<TrackEntityHolder<CardRestResponse>> response) {


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
                public void onFailure(Call<TrackEntityHolder<CardRestResponse>> call, Throwable t) {
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
