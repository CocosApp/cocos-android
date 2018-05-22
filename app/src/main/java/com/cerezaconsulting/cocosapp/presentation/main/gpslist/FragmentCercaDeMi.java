package com.cerezaconsulting.cocosapp.presentation.main.gpslist;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
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
import com.cerezaconsulting.cocosapp.data.entities.RestauranteResponse;
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
 * Created by kath on 15/12/17.
 */

public class FragmentCercaDeMi extends BaseFragment implements GPSRestaurantContract.View {

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
    @BindView(R.id.tv_text)
    TextView tvText;

    private Double latitude;
    private Double longitude;
    private GPSRestaurantContract.Presenter mPresenter;

    private GPSAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private ProgressDialogCustom mProgressDialogCustom;

    private LocationManager mlocManager;

    private AlertDialog dialogogps;

    public FragmentCercaDeMi() {
    }

    public static FragmentCercaDeMi newInstance(Bundle bundle) {
        FragmentCercaDeMi fragmentCercaDeMi = new FragmentCercaDeMi();
        fragmentCercaDeMi.setArguments(bundle);
        return fragmentCercaDeMi;
    }

    @Override
    public void onResume() {
        super.onResume();
       /* if (latitude != null) {
            mPresenter.startLoad(latitude.toString(), longitude.toString());
        }*/
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = new GPSRestaurantPresenter(this, getContext());
        EventBus.getDefault().register(this);

//        latitude = getArguments().getDouble("latitude");
  //      longitude = getArguments().getDouble("longitude");


       /* if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
        } else {
            locationStart();
        }*/
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        final ScrollChildSwipeRefreshLayout swipeRefreshLayout =
                (ScrollChildSwipeRefreshLayout) rootView.findViewById(R.id.refresh_layout);
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
                    //alertafalta();
                    setLoadingIndicator(false);

                }else{
                    mPresenter.refreshList(latitude.toString(), longitude.toString());
                    setLoadingIndicator(false);

                }

              /* if (latitude == null) {
                   Toast.makeText(getContext(), "Ubicación no encontrada, intente nuevamente por favor...", Toast.LENGTH_SHORT).show();
                   locationStart();
                    //showErrorMessage("Debe activar su gps para buscar el restaurante más cercano");
                    setLoadingIndicator(false);

                } else {
                    mPresenter.refreshList(latitude.toString(), longitude.toString());
                    setLoadingIndicator(false);

                }*/
            }
        });
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mProgressDialogCustom = new ProgressDialogCustom(getContext(), "Obteniendo datos...");
        mLayoutManager = new LinearLayoutManager(getContext());
        rvList.setLayoutManager(mLayoutManager);
        mAdapter = new GPSAdapter(new ArrayList<RestauranteResponse>(), getContext(), (RestItem) mPresenter);
        rvList.setAdapter(mAdapter);
        tvText.setVisibility(View.VISIBLE);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpdateLocation(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
    }

    public void alertafalta() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Advertencia");
        builder.setMessage("El sistema GPS esta desactivado, para continuar presione el boton activar?");
        builder.setCancelable(false);
        builder.setPositiveButton("Activar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                startActivity(new Intent
                        (Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        });
        dialogogps = builder.create();
        dialogogps.show();
    }

/*

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationStart();
                return;
            }
        }
    }
*/

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
                tvText.setVisibility(View.GONE);
            } else {
                noList.setVisibility(View.VISIBLE);
                tvText.setVisibility(View.GONE);

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
                    mPresenter.loadList(latitude.toString(), longitude.toString());

                }
            });

        } else {
            noList.setVisibility(View.VISIBLE);
            tvText.setVisibility(View.VISIBLE);
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
    public void setPresenter(GPSRestaurantContract.Presenter presenter) {
        this.mPresenter = presenter;
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
        EventBus.getDefault().unregister(this);

    }

    /*@Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        ///String Text = "Mi ubicacion actual es: " + "\n Lat = "
        //     + location.getLatitude() + "\n Long = " + location.getLongitude();
        //mensaje1.setText(Text);
        // Toast.makeText(getContext(), location.getLatitude() + "  " + location.getLongitude() + "nuevo", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        switch (i) {
            case LocationProvider.AVAILABLE:
                Log.d("debug", "LocationProvider.AVAILABLE");
                break;
            case LocationProvider.OUT_OF_SERVICE:
                Log.d("debug", "LocationProvider.OUT_OF_SERVICE");
                break;
            case LocationProvider.TEMPORARILY_UNAVAILABLE:
                Log.d("debug", "LocationProvider.TEMPORARILY_UNAVAILABLE");
                break;
        }
    }

    @Override
    public void onProviderEnabled(String s) {
        Toast.makeText(getContext(), "GPS ACTIVADO", Toast.LENGTH_SHORT).show();
        if(dialogogps!=null){
            dialogogps.dismiss();
        }

    }

    @Override
    public void onProviderDisabled(String s) {
        alertafalta();
        //showErrorMessage("Por favor active el gps para una mejor ubicación");
        //Toast.makeText(getContext(), "GPS DESACTIVADO", Toast.LENGTH_SHORT).show();
    }*/
}
