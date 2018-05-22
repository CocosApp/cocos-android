package com.cerezaconsulting.cocosapp.presentation.main.favoritos;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cerezaconsulting.cocosapp.R;
import com.cerezaconsulting.cocosapp.core.BaseActivity;
import com.cerezaconsulting.cocosapp.core.BaseFragment;
import com.cerezaconsulting.cocosapp.core.RecyclerViewScrollListener;
import com.cerezaconsulting.cocosapp.core.ScrollChildSwipeRefreshLayout;
import com.cerezaconsulting.cocosapp.data.entities.RestauranteResponse;
import com.cerezaconsulting.cocosapp.data.entities.SubCatEntity;
import com.cerezaconsulting.cocosapp.presentation.main.buscador.ActivityBuscador;
import com.cerezaconsulting.cocosapp.presentation.main.restaurante.RestaurantActivity;
import com.cerezaconsulting.cocosapp.utils.ProgressDialogCustom;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by katherine on 28/06/17.
 */

public class FavoritosFragment extends BaseFragment implements FavoritosContract.View {


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
    @BindView(R.id.toolbar_search)
    Toolbar toolbarSearch;
    @BindView(R.id.search_view)
    MaterialSearchView searchView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.search_bar)
    RelativeLayout searchBar;
    private SubCatEntity subCatEntity;
    private String daySelected;
    private FavoritosAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private FavoritosContract.Presenter mPresenter;
    private ProgressDialogCustom mProgressDialogCustom;
    private String text;

    public FavoritosFragment() {
        // Requires empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.startLoad();
    }

    public static FavoritosFragment newInstance() {

        return new FavoritosFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new FavoritosPresenter(this, getContext());
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_list_favorites, container, false);
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
                mPresenter.startLoad();
                /*if (countryEntity != null) {
                    mPresenter.loadOrdersFromPage(countryEntity.getId(), 1);
                } else {
                    mPresenter.loadOrdersFromPage(cityEntity.getCountryEntity().getId(), 1);
                }*/
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
        mAdapter = new FavoritosAdapter(new ArrayList<RestauranteResponse>(), getContext(), (RestItem) mPresenter);
        rvList.setAdapter(mAdapter);

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {
                mPresenter.getSearch("A");
            }
        });

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText != null && !newText.isEmpty()) {
                    text = newText;
                    mPresenter.getSearch(newText);
                    mAdapter.notifyDataSetChanged();

                } else {
                    //if search text is null
                    //return default
                    mPresenter.getSearch("A");

                }
                return true;
            }

        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_search:
                searchView.setMenuItem(item);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void getFavouritesRestaurantResponse(ArrayList<RestauranteResponse> list) {
        mAdapter.setItems(list);
        if (list != null) {
            noList.setVisibility((list.size() > 0) ? View.GONE : View.VISIBLE);
        }
        rvList.addOnScrollListener(new RecyclerViewScrollListener() {
            @Override
            public void onScrollUp() {
                //mPresenter.loadfromNextPage();

            }

            @Override
            public void onScrollDown() {

            }

            @Override
            public void onLoadMore() {
                //mPresenter.loadfromNextPage();

               /* if (countryEntity != null) {
                    mPresenter.loadfromNextPage(countryEntity.getId());
                } else {
                    mPresenter.loadfromNextPage(cityEntity.getCountryEntity().getId());
                }*/
            }
        });
    }

    @Override
    public void clickItemRestaurante(RestauranteResponse restEntinty) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("restEntity", restEntinty);
        nextActivity(getActivity(), bundle, RestaurantActivity.class, false);

    }

    @Override
    public void responseDeleteRestaurants() {
        showMessage("Restaurante quitado de favoritos de forma exitosa");
        mPresenter.getFavouritesRestaurants();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }


    @Override
    public void setPresenter(FavoritosContract.Presenter mPresenter) {
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

    @OnClick({R.id.title, R.id.search_view, R.id.search_bar})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title:
                nextActivity(getActivity(), null, ActivityBuscador.class, false);
                break;
            case R.id.search_view:
                nextActivity(getActivity(), null, ActivityBuscador.class, false);
                break;
            case R.id.search_bar:
                nextActivity(getActivity(), null, ActivityBuscador.class, false);
                break;
        }
    }

//        nextActivity(getActivity(), null, ActivityBuscador.class, false);

}
