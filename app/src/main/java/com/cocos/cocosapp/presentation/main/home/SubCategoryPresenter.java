package com.cocos.cocosapp.presentation.main.home;

import android.content.Context;
import android.support.annotation.NonNull;

import com.cocos.cocosapp.data.entities.SubCatEntity;
import com.cocos.cocosapp.data.entities.trackholder.TrackEntityHolder;
import com.cocos.cocosapp.data.local.SessionManager;
import com.cocos.cocosapp.data.remote.ServiceFactory;
import com.cocos.cocosapp.data.remote.request.ListRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by manu on 08/08/16.
 */
public class SubCategoryPresenter implements SubcategoryContract.Presenter , SubCategoryItem {

    private final SubcategoryContract.View mView;
    private final SessionManager mSessionManager;
    private int currentPage = 1;
    private boolean mFirstLoad = false;


    public SubCategoryPresenter(@NonNull SubcategoryContract.View view,
                                Context context) {
        this.mView = view;
        this.mSessionManager = new SessionManager(context);
        this.mView.setPresenter(this);
    }


    @Override
    public void start() {
        if (!mFirstLoad) {
            loadPost(1);
            mFirstLoad = true;
        }
    }

    @Override
    public void refreshPost() {
        currentPage = 1;
        loadPost(1);
    }


    @Override
    public void loadPost() {
        loadPost(currentPage);
    }

    @Override
    public void loadPost(int page) {
        if (currentPage != -1) {
            ListRequest listRequest =
                    ServiceFactory.createService(ListRequest.class);
            Call<TrackEntityHolder<SubCatEntity>> call = null;
            call = listRequest.getCategories("Token " + mSessionManager.getUserToken(), currentPage);

            if (currentPage == 1) {
                mView.setLoadingIndicator(true);
            } else {
                mView.showLoadMore(true);
            }
            call.enqueue(new Callback<TrackEntityHolder<SubCatEntity>>() {
                @Override
                public void onResponse(Call<TrackEntityHolder<SubCatEntity>> call, Response<TrackEntityHolder<SubCatEntity>> response) {


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
                            mView.showPost(response.body().getResults());
                        } else {
                            mView.showMorePost(response.body().getResults());
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
                public void onFailure(Call<TrackEntityHolder<SubCatEntity>> call, Throwable t) {
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


    @Override
    public void clickItem(SubCatEntity subCatEntity) {
        mView.clickItemCategories(subCatEntity);
    }

    @Override
    public void deleteItem(SubCatEntity subCatEntity, int position) {

    }
}