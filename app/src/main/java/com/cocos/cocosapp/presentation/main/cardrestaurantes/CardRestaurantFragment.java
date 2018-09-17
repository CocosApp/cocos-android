package com.cocos.cocosapp.presentation.main.cardrestaurantes;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cocos.cocosapp.R;
import com.cocos.cocosapp.core.BaseActivity;
import com.cocos.cocosapp.core.BaseFragment;
import com.cocos.cocosapp.core.RecyclerViewScrollListener;
import com.cocos.cocosapp.core.ScrollChildSwipeRefreshLayout;
import com.cocos.cocosapp.data.entities.CardRestResponse;
import com.cocos.cocosapp.data.entities.RestauranteResponse;
import com.cocos.cocosapp.data.entities.SubCatEntity;
import com.cocos.cocosapp.data.local.SessionManager;
import com.cocos.cocosapp.presentation.main.restaurante.RestaurantActivity;
import com.cocos.cocosapp.utils.ProgressDialogCustom;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by katherine on 28/06/17.
 */

public class CardRestaurantFragment extends BaseFragment implements CardRestaurantContract.View {


    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.noListIcon)
    ImageView noListIcon;
    @BindView(R.id.noListMain)
    TextView noListMain;
    @BindView(R.id.noList)
    LinearLayout noList;
    @BindView(R.id.refresh_layout)
    ScrollChildSwipeRefreshLayout refreshLayout;
    Unbinder unbinder;
    private SubCatEntity subCatEntity;
    private String daySelected;
    private CardRestaurantAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private CardRestaurantContract.Presenter mPresenter;
    private ProgressDialogCustom mProgressDialogCustom;
    int id;
    private String date, time;
    private FirebaseAnalytics mFirebaseAnalytics;

    private SessionManager mSessionManager;

    public CardRestaurantFragment() {
        // Requires empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.startLoad(id);
    }

    public static CardRestaurantFragment newInstance(Bundle bundle) {
        CardRestaurantFragment fragment = new CardRestaurantFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new CardRestaurantPresenter(this, getContext());
        mSessionManager = new SessionManager(getContext());
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());

        id = (int) getArguments().getSerializable("id");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_list, container, false);
        final ScrollChildSwipeRefreshLayout swipeRefreshLayout =
                (ScrollChildSwipeRefreshLayout) root.findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.black),
                ContextCompat.getColor(getActivity(), R.color.dark_gray),
                ContextCompat.getColor(getActivity(), R.color.black)
        );
        // Set the scrolling view in the custom SwipeRefreshLayout.
        swipeRefreshLayout.setScrollUpChild(rvList);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.refreshList(id);
            }
        });

        unbinder = ButterKnife.bind(this, root);

        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mProgressDialogCustom = new ProgressDialogCustom(getContext(), "Obteniendo datos...");
        mLayoutManager = new LinearLayoutManager(getContext());
        rvList.setLayoutManager(mLayoutManager);
        mAdapter = new CardRestaurantAdapter(new ArrayList<RestauranteResponse>(), getContext(), (CardRestItem) mPresenter);
        rvList.setAdapter(mAdapter);
    }


    @Override
    public void showMoreRestaurante(ArrayList<CardRestResponse> list) {
        ArrayList<RestauranteResponse> postAux = (ArrayList<RestauranteResponse>) mAdapter.getmItems();

        ArrayList<RestauranteResponse> newList = new ArrayList<>();

        for (int i = 0; i <list.size() ; i++) {
            for (int j = 0; j <list.get(i).getRestaurants().size() ; j++) {
                newList.add(list.get(i).getRestaurants().get(j));
            }

        }

        postAux.addAll(newList);
        mAdapter.setItems(postAux);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showRestaurante(ArrayList<CardRestResponse> list) {

        ArrayList<RestauranteResponse> newList = new ArrayList<>();

        for (int i = 0; i <list.size() ; i++) {
            for (int j = 0; j <list.get(i).getRestaurants().size() ; j++) {
                newList.add(list.get(i).getRestaurants().get(j));
            }

        }

        mAdapter.setItems(newList);
        noList.setVisibility(View.GONE);

        if (this.rvList != null && mAdapter != null) {
            mAdapter.setItems(newList);

            if (list.size() > 0) {
                noList.setVisibility(View.GONE);
            } else {
                noList.setVisibility(View.VISIBLE);
            }

            this.rvList.addOnScrollListener(new RecyclerViewScrollListener() {
                @Override
                public void onScrollUp() {

                }

                @Override
                public void onScrollDown() {

                }

                @Override
                public void onLoadMore() {
                    mPresenter.loadList(id);

                }
            });

        } else {
            noList.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showLoadMore(boolean active) {
        mAdapter.showLoading(active);

    }

    @Override
    public void clickItemRestaurante(RestauranteResponse restauranteResponse) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("restEntity", restauranteResponse);


        Bundle params = new Bundle();
        params.putInt("id_user", mSessionManager.getUserEntity().getId());
        params.putString("name_user", mSessionManager.getUserEntity().getFullName());
        params.putInt("id_restaurant", restauranteResponse.getId());
        params.putString("name_restaurant", restauranteResponse.getName());
        params.putString("date", getDateAndTimeNow());
        params.putString("label", "detail_restaurant");
        params.putString("so", "android");

        mFirebaseAnalytics.logEvent("detail_restaurant", params);

        nextActivity(getActivity(), bundle, RestaurantActivity.class, false);


    }

    @Override
    public boolean isActive() {
        return isAdded();
    }


    @Override
    public void setPresenter(CardRestaurantContract.Presenter mPresenter) {
        this.mPresenter = mPresenter;
    }

    @Override
    public void setLoadingIndicator(final boolean active) {
        if (getView() == null) {
            return;
        }

        final SwipeRefreshLayout srl =
                (SwipeRefreshLayout) getView().findViewById(R.id.refresh_layout);

        // Make sure setRefreshing() is called after the layout is done with everything else.
        srl.post(new Runnable() {
            @Override
            public void run() {
                srl.setRefreshing(active);
            }
        });

        if (active) {
            mProgressDialogCustom.show();
        } else {
            if (mProgressDialogCustom.isShowing()) {
                mProgressDialogCustom.dismiss();
            }
        }

    }

    @Override
    public void showMessage(String message) {
        ((BaseActivity) getActivity()).showMessage(message);
    }

    @Override
    public void showErrorMessage(String message) {
        ((BaseActivity) getActivity()).showMessageError(message);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                Toast.makeText(getActivity(), "Back from fragment", Toast.LENGTH_SHORT).show();
                getActivity().onBackPressed();
        }
        return true;

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
