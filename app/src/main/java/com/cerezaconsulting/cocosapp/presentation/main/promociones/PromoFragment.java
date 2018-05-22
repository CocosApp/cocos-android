package com.cerezaconsulting.cocosapp.presentation.main.promociones;

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
import com.cerezaconsulting.cocosapp.data.entities.DescEntity;
import com.cerezaconsulting.cocosapp.data.entities.RestEntinty;
import com.cerezaconsulting.cocosapp.data.entities.SubCatEntity;
import com.cerezaconsulting.cocosapp.presentation.main.listrestaurante.ListRestaurantAdapter;
import com.cerezaconsulting.cocosapp.presentation.main.listrestaurante.ListRestaurantContract;
import com.cerezaconsulting.cocosapp.presentation.main.listrestaurante.ListRestaurantPresenter;
import com.cerezaconsulting.cocosapp.presentation.main.listrestaurante.RestItem;
import com.cerezaconsulting.cocosapp.presentation.main.restaurante.RestaurantActivity;
import com.cerezaconsulting.cocosapp.utils.ProgressDialogCustom;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by katherine on 28/06/17.
 */

public class PromoFragment extends BaseFragment implements PromoContract.View {


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
    private PromoAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private PromoContract.Presenter mPresenter;
    private ProgressDialogCustom mProgressDialogCustom;
    private RestEntinty restEntinty;

    public PromoFragment() {
        // Requires empty public constructor
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    public static PromoFragment newInstance() {
        //PromoFragment fragment = new PromoFragment();
        //fragment.setArguments(bundle);
        return new PromoFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new PromoPresenter(this, getContext());
        Bundle bundle = getArguments();
        restEntinty = (RestEntinty) bundle.getSerializable("restEntity");
        //restEntinty = (RestEntinty) getArguments().getSerializable("restEntity");
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
                if(restEntinty.getDiscount()!=null){
                    mAdapter.setItems(restEntinty.getDiscount());
                    setLoadingIndicator(false);

                }
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
        mAdapter = new PromoAdapter(new ArrayList<DescEntity>(), getContext(), (PromoItem) mPresenter);
        rvList.setAdapter(mAdapter);

        if(restEntinty.getDiscount()!=null){
            mAdapter.setItems(restEntinty.getDiscount());
        }

    }


    @Override
    public void getListPromo(ArrayList<DescEntity> list) {
        mAdapter.setItems(restEntinty.getDiscount());
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
    public void clickItemPromo(DescEntity descEntity) {


        if(descEntity.getPorc()!=null && descEntity.getCard()!=null){

            Bundle bundle = new Bundle();
            bundle.putString("msg", descEntity.getTerms_condition());
            DialogTerminos dialogTerminos = new DialogTerminos(getContext(),bundle);
            dialogTerminos.show();

        }else{
            Bundle bundle = new Bundle();
            bundle.putSerializable("descEntity", descEntity);
            bundle.putString("imageRest", restEntinty.getPhoto1());
            nextActivity(getActivity(), bundle, PromoPropiaActivity.class, false);
        }


    }

    @Override
    public boolean isActive() {
        return isAdded();
    }


    @Override
    public void setPresenter(PromoContract.Presenter mPresenter) {
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
}
