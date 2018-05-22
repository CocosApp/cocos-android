package com.cerezaconsulting.cocosapp.presentation.main.promociones;

import android.content.Context;

import com.cerezaconsulting.cocosapp.data.entities.DescEntity;
import com.cerezaconsulting.cocosapp.data.entities.RestEntinty;
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

public class PromoPresenter implements PromoContract.Presenter, PromoItem {

    private PromoContract.View mView;
    private Context context;
    private SessionManager mSessionManager;
    private boolean firstLoad = false;
    private int currentPage = 1;

    public PromoPresenter(PromoContract.View mView, Context context) {
        this.context = checkNotNull(context, "context cannot be null!");
        this.mView = checkNotNull(mView, "view cannot be null!");
        this.mView.setPresenter(this);
        this.mSessionManager = new SessionManager(this.context);
    }


    @Override
    public void start() {

    }

    @Override
    public void clickItem(DescEntity descEntity) {
        mView.clickItemPromo(descEntity);
    }

    @Override
    public void deleteItem(DescEntity descEntity, int position) {

    }


   /* @Override
    public void clickItem(RestEntinty restEntinty) {
        mView.clickItemRestaurante(restEntinty);
    }

    @Override
    public void deleteItem(RestEntinty restEntinty, int position) {

    }

    @Override
    public void loadOrdersFromPage(int page, int id) {
        getRestaurante(page, id);

    }

    @Override
    public void loadfromNextPage(int id) {

        if (currentPage > 0)
            getRestaurante(currentPage, id);

    }

    @Override
    public void startLoad(int id) {
        if (!firstLoad) {
            firstLoad = true;
            loadOrdersFromPage(1, id);
        }
    }

    @Override
    public void getRestaurante(final int page, int id) {
        mView.setLoadingIndicator(true);
        ListRequest listRequest = ServiceFactory.createService(ListRequest.class);
        Call<TrackEntityHolder<RestEntinty>> orders = listRequest.getListRestaurantes("Token "+mSessionManager.getUserToken(),id ,page);
        orders.enqueue(new Callback<TrackEntityHolder<RestEntinty>>() {
            @Override
            public void onResponse(Call<TrackEntityHolder<RestEntinty>> call, Response<TrackEntityHolder<RestEntinty>> response) {

                if (!mView.isActive()) {
                    return;
                }
                mView.setLoadingIndicator(false);
                if (response.isSuccessful()) {

                    mView.getListRestaurante(response.body().getResults());

                    if (response.body().getNext() != null) {
                        currentPage = page +1;
                    } else {
                        currentPage = -1;
                    }

                } else {
                    mView.showErrorMessage("Error al obtener las órdenes");
                }
            }

            @Override
            public void onFailure(Call<TrackEntityHolder<RestEntinty>> call, Throwable t) {
                if (!mView.isActive()) {
                    return;
                }
                mView.setLoadingIndicator(false);
                mView.showErrorMessage("Error al conectar con el servidor");
            }
        });
    }*/
}
