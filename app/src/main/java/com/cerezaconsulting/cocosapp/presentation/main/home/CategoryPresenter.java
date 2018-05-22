package com.cerezaconsulting.cocosapp.presentation.main.home;

import android.content.Context;

import com.cerezaconsulting.cocosapp.data.entities.SubCatEntity;
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

public class CategoryPresenter implements CategoryContract.Presenter, SubCategoryItem {

    private CategoryContract.View mView;
    private Context context;
    private SessionManager mSessionManager;
    private boolean firstLoad = false;
    private int currentPage = 1;

    public CategoryPresenter(CategoryContract.View mView, Context context) {
        this.context = checkNotNull(context, "context cannot be null!");
        this.mView = checkNotNull(mView, "view cannot be null!");
        this.mView.setPresenter(this);
        this.mSessionManager = new SessionManager(this.context);
    }


    @Override
    public void start() {
        if (!firstLoad) {
            firstLoad = true;
            loadOrdersFromPage(1);
        }
    }


    @Override
    public void clickItem(SubCatEntity subCatEntity) {
        mView.clickItemCategories(subCatEntity);
    }

    @Override
    public void deleteItem(SubCatEntity subCatEntity, int position) {

    }

    @Override
    public void loadOrdersFromPage(int page) {
        getCategories(page);

    }

    @Override
    public void loadfromNextPage() {

        if (currentPage > 0)
            getCategories(currentPage);

    }

    @Override
    public void startLoad() {
        if (!firstLoad) {
            firstLoad = true;
            loadOrdersFromPage(1);
        }
    }

    @Override
    public void getCategories(final int page) {
        mView.setLoadingIndicator(true);
        ListRequest listRequest = ServiceFactory.createService(ListRequest.class);
        Call<TrackEntityHolder<SubCatEntity>> orders = listRequest.getCategories("Token "+mSessionManager.getUserToken() ,page);
        orders.enqueue(new Callback<TrackEntityHolder<SubCatEntity>>() {
            @Override
            public void onResponse(Call<TrackEntityHolder<SubCatEntity>> call, Response<TrackEntityHolder<SubCatEntity>> response) {

                if (!mView.isActive()) {
                    return;
                }
                mView.setLoadingIndicator(false);
                if (response.isSuccessful()) {

                    mView.getListCategories(response.body().getResults());

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
            public void onFailure(Call<TrackEntityHolder<SubCatEntity>> call, Throwable t) {
                if (!mView.isActive()) {
                    return;
                }
                mView.setLoadingIndicator(false);
                mView.showErrorMessage("Error al conectar con el servidor");
            }
        });
    }
}
