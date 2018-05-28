package com.cocos.cocosapp.presentation.main.card;

import android.content.Context;

import com.cocos.cocosapp.data.entities.CardEntity;
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

public class CardPresenter implements CardContract.Presenter, CardItem {

    private CardContract.View mView;
    private Context context;
    private SessionManager mSessionManager;
    private int currentPage = 1;
    private boolean mFirstLoad = false;

    public CardPresenter(CardContract.View mView, Context context) {
        this.context = checkNotNull(context, "context cannot be null!");
        this.mView = checkNotNull(mView, "view cannot be null!");
        this.mView.setPresenter(this);
        this.mSessionManager = new SessionManager(this.context);
    }


    @Override
    public void start() {
        if (!mFirstLoad) {
            loadPost(1);
            mFirstLoad = true;
        }
    }


    @Override
    public void clickItem(CardEntity cardEntity) {
        mView.clickItemCard(cardEntity);
    }

    @Override
    public void deleteItem(CardEntity cardEntity, int position) {

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
            Call<TrackEntityHolder<CardEntity>> call = null;
            call = listRequest.getCards("Token " + mSessionManager.getUserToken(), currentPage);

            if (currentPage == 1) {
                mView.setLoadingIndicator(true);
            } else {
                mView.showLoadMore(true);
            }
            call.enqueue(new Callback<TrackEntityHolder<CardEntity>>() {
                @Override
                public void onResponse(Call<TrackEntityHolder<CardEntity>> call, Response<TrackEntityHolder<CardEntity>> response) {


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
                public void onFailure(Call<TrackEntityHolder<CardEntity>> call, Throwable t) {
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
    public void startLoad() {
        if (!mFirstLoad) {
            loadPost(1);
            mFirstLoad = true;
        }
    }
}
