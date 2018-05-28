package com.cocos.cocosapp.presentation.main.buscador;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cocos.cocosapp.R;
import com.cocos.cocosapp.core.BaseActivity;
import com.cocos.cocosapp.core.RecyclerViewScrollListener;
import com.cocos.cocosapp.core.ScrollChildSwipeRefreshLayout;
import com.cocos.cocosapp.data.entities.RestauranteResponse;
import com.cocos.cocosapp.presentation.main.restaurante.RestaurantActivity;
import com.cocos.cocosapp.utils.ProgressDialogCustom;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kath on 11/01/18.
 */

public class ActivityBuscador extends BaseActivity implements BuscadorContract.View, MaterialSearchView.OnQueryTextListener, MaterialSearchView.SearchViewListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.search_view)
    MaterialSearchView searchView;
    @BindView(R.id.appbar)
    RelativeLayout appbar;
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
    @BindView(R.id.title)
    TextView title;

    private BuscadorAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private BuscadorContract.Presenter mPresenter;
    private ProgressDialogCustom mProgressDialogCustom;

    private String text;

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.loadList("A");
        searchView.setOnQueryTextListener(this);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_list_toolbar);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(" ");
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));

        mPresenter = new BuscadorPresenter(this, getApplicationContext());
        final ScrollChildSwipeRefreshLayout swipeRefreshLayout =
                (ScrollChildSwipeRefreshLayout) findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(this, R.color.black),
                ContextCompat.getColor(this, R.color.dark_gray),
                ContextCompat.getColor(this, R.color.black)
        );
        // Set the scrolling view in the custom SwipeRefreshLayout.
        swipeRefreshLayout.setScrollUpChild(rvList);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.refreshList(text);
                /*if (countryEntity != null) {
                    mPresenter.loadOrdersFromPage(countryEntity.getId(), 1);
                } else {
                    mPresenter.loadOrdersFromPage(cityEntity.getCountryEntity().getId(), 1);
                }*/
            }
        });

        mProgressDialogCustom = new ProgressDialogCustom(getApplicationContext(), "Obteniendo datos...");
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvList.setLayoutManager(mLayoutManager);
        mAdapter = new BuscadorAdapter(new ArrayList<RestauranteResponse>(), getApplicationContext(), (BuscadorItem) mPresenter);
        rvList.setAdapter(mAdapter);
        searchView.setOnSearchViewListener(this);
        searchView.setHint("Encuentra tu descuento...");
        searchView.setOnQueryTextListener(this);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchView.showSearch(true);
            }
        });

        toolbar.setPressed(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        //MenuItem item = menu.findItem(R.id.action_search);
        //searchView.setMenuItem(item);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        searchView.setMenuItem(item);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void clickItemRestaurante(RestauranteResponse restEntinty) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("restEntity", restEntinty);
        nextActivity(ActivityBuscador.this, bundle, RestaurantActivity.class, false);
    }

    @Override
    public void showMoreRestaurante(ArrayList<RestauranteResponse> list) {
        ArrayList<RestauranteResponse> postAux = (ArrayList<RestauranteResponse>) mAdapter.getmItems();
        postAux.addAll(list);
        mAdapter.setItems(postAux);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showRestaurante(ArrayList<RestauranteResponse> list) {
        mAdapter.setItems(list);
        noList.setVisibility(View.GONE);

        if (this.rvList != null && mAdapter != null) {
            mAdapter.setItems(list);

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
                    mPresenter.loadList(text);

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
    public boolean isActive() {
        return true;
    }

    @Override
    public void setPresenter(BuscadorContract.Presenter presenter) {
        this.mPresenter = mPresenter;
    }

    @Override
    public void setLoadingIndicator(final boolean active) {

        final SwipeRefreshLayout srl =
                (SwipeRefreshLayout) findViewById(R.id.refresh_layout);
        // Make sure setRefreshing() is called after the layout is done with everything else.
        srl.post(new Runnable() {
            @Override
            public void run() {
                srl.setRefreshing(active);
            }
        });

      /*  if (active) {
            mProgressDialogCustom.show();
        } else {
            if (mProgressDialogCustom.isShowing()) {
                mProgressDialogCustom.dismiss();
            }
        }*/
    }

    @Override
    public void showMessage(String message) {
        showMessage(message);
    }

    @Override
    public void showErrorMessage(String message) {
        showMessageError(message);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        if (query != null && !query.isEmpty()) {
            text = query;
            mPresenter.getRestaurante(1, text);
            //mPresenter.loadList(1,newText);
            // mAdapter.notifyDataSetChanged();

        } else {
            //if search text is null
            //return default
            mPresenter.loadList("A");
            mAdapter.notifyDataSetChanged();
        }
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText != null && !newText.isEmpty()) {
            text = newText;
            mPresenter.getRestaurante(1, text);
            //mPresenter.loadList(1,newText);
            // mAdapter.notifyDataSetChanged();

        } else {
            //if search text is null
            //return default
            mPresenter.loadList("A");
            mAdapter.notifyDataSetChanged();
        }
        return true;
    }

    @Override
    public void onSearchViewShown() {
    }

    @Override
    public void onSearchViewClosed() {
        mPresenter.loadList("A");
        mAdapter.notifyDataSetChanged();
    }
}
