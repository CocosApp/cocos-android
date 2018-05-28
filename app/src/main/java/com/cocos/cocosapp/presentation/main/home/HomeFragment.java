package com.cocos.cocosapp.presentation.main.home;

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
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cocos.cocosapp.R;
import com.cocos.cocosapp.core.BaseFragment;
import com.cocos.cocosapp.presentation.main.buscador.ActivityBuscador;
import com.cocos.cocosapp.presentation.main.card.CardFragment;
import com.cocos.cocosapp.presentation.main.gpslist.FragmentCercaDeMi;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by kath on 12/12/17.
 */

public class HomeFragment extends BaseFragment implements LocationListener {


    Unbinder unbinder;
    @BindView(R.id.layout_content_frame)
    FrameLayout layoutContentFrame;
    @BindView(R.id.toolbar_search)
    Toolbar toolbarSearch;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.search_bar)
    RelativeLayout searchBar;
    private Toolbar toolbar;
    private ActionBarDrawerToggle mDrawerToggle;
    private TabLayout tabLayout;
    private ViewPager viewPager;


    private Double latitude;
    private Double longitude;
    private LocationManager mlocManager;

    private AlertDialog dialogogps;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
        } else {
            locationStart();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        EventBus.getDefault().register(this);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        FrameLayout frameLayout = rootView.findViewById(R.id.layout_content_frame);
        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View activityView = layoutInflater.inflate(R.layout.layout_tab, null, false);

        View view = layoutInflater.inflate(R.layout.layout_tab, null);

        frameLayout.addView(view);

        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        viewPager.setOffscreenPageLimit(3);

        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);

        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Subscribe
    public void onUpdateLocation(Location location) {

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // locationStart();


    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());

        adapter.addFragment(new SubCategoryFragment(), "Categoria");
        adapter.addFragment(new FragmentCercaDeMi(), "Cerca de mi");
        adapter.addFragment(new CardFragment(), "Beneficios");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        if (mlocManager != null) {
            mlocManager.removeUpdates(this);
        }
        //        nextActivity(getActivity(), null, ActivityBuscador.class, false);

    }

    @OnClick({R.id.toolbar_search, R.id.title, R.id.search_bar})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_search:
                nextActivity(getActivity(), null, ActivityBuscador.class, false);

                break;
            case R.id.title:
                nextActivity(getActivity(), null, ActivityBuscador.class, false);

                break;
            case R.id.search_bar:
                nextActivity(getActivity(), null, ActivityBuscador.class, false);

                break;
        }
    }

    private void locationStart() {
        mlocManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        //Localizacion Local = new Localizacion();
        //Local.setMainActivity(this);
        final boolean gpsEnabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!gpsEnabled) {
            alertafalta();
        }
        //}
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
            return;
        }
        mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        //mensaje1.setText("Localización agregada");
        //mensaje2.setText("");

    }

    public void alertafalta() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Advertencia");
        builder.setMessage("El sistema GPS esta desactivado, para continuar presione el boton activar?");
        builder.setCancelable(false);
        builder.setPositiveButton("Activar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                getActivity().startActivity(new Intent
                        (Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        });
        dialogogps = builder.create();
        dialogogps.show();
    }


    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationStart();
                return;
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        EventBus.getDefault().post(location);

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
        if (dialogogps != null) {
            dialogogps.dismiss();
            dialogogps = null;
        }
    }

    @Override
    public void onProviderDisabled(String s) {
        if (dialogogps == null) {

            if (s.equals("gps")) {
                alertafalta();
            }
        }

    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mlocManager != null) {
            mlocManager.removeUpdates(this);
        }
        EventBus.getDefault().unregister(this);

    }
}
