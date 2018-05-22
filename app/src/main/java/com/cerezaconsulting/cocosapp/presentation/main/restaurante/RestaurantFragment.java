package com.cerezaconsulting.cocosapp.presentation.main.restaurante;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.cerezaconsulting.cocosapp.R;
import com.cerezaconsulting.cocosapp.core.BaseActivity;
import com.cerezaconsulting.cocosapp.core.BaseFragment;
import com.cerezaconsulting.cocosapp.data.entities.IsMyFavouriteRestaurante;
import com.cerezaconsulting.cocosapp.data.entities.MessageResponse;
import com.cerezaconsulting.cocosapp.data.entities.FavouriteResponse;
import com.cerezaconsulting.cocosapp.data.entities.RestEntinty;
import com.cerezaconsulting.cocosapp.data.entities.RestauranteResponse;
import com.cerezaconsulting.cocosapp.data.entities.StatusColorEntity;
import com.cerezaconsulting.cocosapp.data.local.SessionManager;
import com.cerezaconsulting.cocosapp.presentation.main.informacion.InformacionFragment;
import com.cerezaconsulting.cocosapp.presentation.main.promociones.PromoFragment;
import com.cerezaconsulting.cocosapp.utils.ProgressDialogCustom;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.viewpagerindicator.CirclePageIndicator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by katherine on 28/06/17.
 */

public class RestaurantFragment extends BaseFragment implements RestaurantContract.View {
    @BindView(R.id.pager)
    ViewPager pager;
    @BindView(R.id.titles)
    CirclePageIndicator titles;
    @BindView(R.id.container_image)
    RelativeLayout containerImage;
    @BindView(R.id.text_container)
    LinearLayout textContainer;
    @BindView(R.id.layout_content_frame)
    FrameLayout layoutContentFrame;
    Unbinder unbinder;
    @BindView(R.id.im_uber)
    ImageView imUber;
    @BindView(R.id.btn_uber)
    RelativeLayout btnUber;
    @BindView(R.id.im_reserva)
    ImageView imReserva;
    @BindView(R.id.btn_reservar)
    RelativeLayout btnReservar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    public RestauranteResponse restauranteResponse;
    public RestEntinty newRestEntity;
    private InitAdapter initAdapter;
    private RestaurantContract.Presenter mPresenter;

    private ProgressDialogCustom mProgressDialogCustom;
    private int id = 0;
    private SessionManager mSessionManager;
    private Menu menuItem;
    //
    Bundle bundle;
    private int idRestaurante;
    public RestaurantFragment() {
        // Requires empty public constructor

    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getFavoriteRestaurant(restauranteResponse.getId());
    }

    public static RestaurantFragment newInstance(Bundle bundle) {
        RestaurantFragment fragment = new RestaurantFragment();

        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new RestaurantPresenter(this, getContext());
        mSessionManager = new SessionManager(getContext());
        restauranteResponse = (RestauranteResponse) getArguments().getSerializable("restEntity");
        setHasOptionsMenu(true);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_restaurante, container, false);


        FrameLayout frameLayout = rootView.findViewById(R.id.layout_content_frame);
        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View activityView = layoutInflater.inflate(R.layout.layout_tab, null, false);
        View view = layoutInflater.inflate(R.layout.layout_tab, null);
        frameLayout.addView(view);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setBackgroundColor(getResources().getColor(R.color.black));

        idRestaurante = restauranteResponse.getId();

        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mProgressDialogCustom = new ProgressDialogCustom(getContext(), "Obteniendo datos...");
        mPresenter.getRestaurante(restauranteResponse.getId());


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_favoritos, menu);
        menuItem = menu;
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_favorito:
                //Toast.makeText(getActivity(), "Favoritos", Toast.LENGTH_SHORT).show();

                if(mSessionManager.getUserEntity().getFirst_name().equals("INVITADO")){
                    ((BaseActivity) getActivity()).showMessageError("No se puede agregar a favoritos como invitado, por favor registrarse para continuar");
                }else{
                    mPresenter.sendMyFavoriteRestaurant(newRestEntity.getId());
                }

                return true;
            case R.id.action_send:
                //Toast.makeText(getActivity(), "Compartir", Toast.LENGTH_SHORT).show();

                if(mSessionManager.getUserEntity().getFirst_name().equals("INVITADO")){
                    ((BaseActivity) getActivity()).showMessageError("No se puede compartir como invitado, por favor registrarse para continuar");
                }else{
                   shareImage(restauranteResponse.getPhoto1(), getContext());
                }

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    static public void shareImage(String url, final Context context) {
        Picasso.with(context).load(url).into(new Target() {
            @Override public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("image/*");
                i.putExtra(Intent.EXTRA_STREAM, getLocalBitmapUri(bitmap, context));
                String shareText = "Todos los descuentos en un solo sito";
                shareText = shareText + " https://play.google.com/store/apps/details?id=com.cerezaconsulting.cocosapp";
                // if you have live app then you can share link like below
                //shareText = shareText + "https://play.google.com/store/apps/details?id=" + context.getPackageName();
                i.putExtra(Intent.EXTRA_TEXT, shareText);
                context.startActivity(Intent.createChooser(i, "Share Image"));

            }
            @Override public void onBitmapFailed(Drawable errorDrawable) { }
            @Override public void onPrepareLoad(Drawable placeHolderDrawable) { }
        });
    }
    static public Uri getLocalBitmapUri(Bitmap bmp, Context context) {
        Uri bmpUri = null;
        try {
            File file =  new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_" + System.currentTimeMillis() + ".png");
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        PromoFragment promoFragment = new PromoFragment();
        promoFragment.setArguments(bundle);

        InformacionFragment informacionFragment = new InformacionFragment();
        informacionFragment.setArguments(bundle);

        adapter.addFragment(promoFragment, "Descuentos");
        adapter.addFragment(informacionFragment, "Información");
        viewPager.setAdapter(adapter);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void getRestaurante(RestEntinty restEntinty) {
        newRestEntity = restEntinty;
        id = newRestEntity.getId();

        bundle = new Bundle();
        bundle.putSerializable("restEntity", restEntinty);

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);


        initAdapter = new InitAdapter(getActivity(), newRestEntity);
        pager.setAdapter(initAdapter);
        titles.notifyDataSetChanged();
        titles.setViewPager(pager);

    }

    @Override
    public void myFavoriteResponse(RestauranteResponse restauranteResponse) {
        if(restauranteResponse != null){
            showMessage("Restaurante asignado a favoritos con éxito");
            mPresenter.getFavoriteRestaurant(idRestaurante);
        }


    }

    @Override
    public void getFavoriteStatusResponse(StatusColorEntity statusColorEntity) {
        if(statusColorEntity.isIs_color()){
            menuItem.getItem(0).setIcon(getResources().getDrawable(R.drawable.ic_favorito));
        }
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void setPresenter(RestaurantContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        if (getView() == null) {
            return;
        }

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

    @OnClick({R.id.btn_uber, R.id.btn_reservar})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_uber:/*
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.ubercab"));
                startActivity(i);*/
                DialogAutos dialogAutos = new DialogAutos(getContext());
                dialogAutos.show();

                break;
            case R.id.btn_reservar:
                Bundle newBundle = new Bundle();
                newBundle.putString("tlf", newRestEntity.getMobile());
                DialogReserva dialogReserva = new DialogReserva(getContext(), newBundle);
                dialogReserva.show();
                break;
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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }

    public static class InitAdapter extends PagerAdapter {

        Context context;
        ArrayList<String> list;
        @BindView(R.id.im_slide)
        ImageView imSlide;
        private LayoutInflater layoutInflater;
        String item;
        RestEntinty rest;


        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public InitAdapter(Context context, RestEntinty restEntinty) {
            this.context = context;
            this.list = new ArrayList<>();
            this.layoutInflater = LayoutInflater.from(context);
            this.rest = restEntinty;
            list.add(rest.getPhoto1());
            list.add(rest.getPhoto2());
            list.add(rest.getPhoto3());

        }

        public Object instantiateItem(final ViewGroup collection, final int position) {
            item = list.get(position);
            final View view = layoutInflater.inflate(R.layout.item_slide, collection, false);
            ButterKnife.bind(this, view);
            Glide.with(context)
                    .load(item)
                    .into(imSlide);
            //imSlide.setImageDrawable(item);
            collection.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        public void destroyItem(ViewGroup collection, int position,
                                Object view) {
            collection.removeView((View) view);
        }
    }


}
