package com.cerezaconsulting.cocosapp.presentation.main.listrestaurante;

import android.location.Location;
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

import com.cerezaconsulting.cocosapp.R;
import com.cerezaconsulting.cocosapp.core.BaseActivity;
import com.cerezaconsulting.cocosapp.core.BaseFragment;
import com.cerezaconsulting.cocosapp.core.RecyclerViewScrollListener;
import com.cerezaconsulting.cocosapp.core.ScrollChildSwipeRefreshLayout;
import com.cerezaconsulting.cocosapp.data.entities.RestEntinty;
import com.cerezaconsulting.cocosapp.data.entities.RestauranteResponse;
import com.cerezaconsulting.cocosapp.data.entities.SubCatEntity;
import com.cerezaconsulting.cocosapp.presentation.main.restaurante.RestaurantActivity;
import com.cerezaconsulting.cocosapp.utils.ProgressDialogCustom;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by katherine on 28/06/17.
 */

public class ListRestaurantFragment extends BaseFragment implements ListRestaurantContract.View {

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
    private ListRestaurantAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private ListRestaurantContract.Presenter mPresenter;
    private ProgressDialogCustom mProgressDialogCustom;
    int id;


    private Double latitude;
    private Double longitude;

    public ListRestaurantFragment() {
        // Requires empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.startLoad(id, "-12.0603787", "-77.0388052", 1000000000);
    }

    public static ListRestaurantFragment newInstance(Bundle bundle) {
        ListRestaurantFragment fragment = new ListRestaurantFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new ListRestaurantPresenter(this, getContext());
        id = (int) getArguments().getSerializable("id");
         latitude = getArguments().getDouble("latitude");
         longitude = getArguments().getDouble("longitude");

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

                if(latitude == null){
                    showMessage("Estamos ubicandolo, vuelva a intentar por favor");
                    mPresenter.startLoad(id, "-12.0603787", "-77.0388052", 1000000000);

                }else{
                    mPresenter.startLoad(id, latitude.toString(), longitude.toString(), 1000000000);
                    setLoadingIndicator(false);
                }                /*if (countryEntity != null) {
                    mPresenter.loadOrdersFromPage(countryEntity.getId(), 1);
                } else {
                    mPresenter.loadOrdersFromPage(cityEntity.getCountryEntity().getId(), 1);
                }*/
            }
        });

        unbinder = ButterKnife.bind(this, root);

       /* AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                HomeFragment fragment = new HomeFragment();
                fragmentTransaction.replace(R.id.contentContainer_1, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });*/

        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mProgressDialogCustom = new ProgressDialogCustom(getContext(), "Obteniendo datos...");
        mLayoutManager = new LinearLayoutManager(getContext());
        rvList.setLayoutManager(mLayoutManager);
        mAdapter = new ListRestaurantAdapter(new ArrayList<RestauranteResponse>(), getContext(), (RestItem) mPresenter);
        rvList.setAdapter(mAdapter);
    }

    @Override
    public void clickItemRestaurante(RestauranteResponse restauranteResponse) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("restEntity", restauranteResponse);
        nextActivity(getActivity(), bundle, RestaurantActivity.class, false);

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
                    mPresenter.loadList(id, latitude.toString(), longitude.toString(), 1000000000);

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
        return isAdded();
    }


    @Override
    public void setPresenter(ListRestaurantContract.Presenter mPresenter) {
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
}
