package com.cocos.cocosapp.presentation.main.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cocos.cocosapp.R;
import com.cocos.cocosapp.core.BaseActivity;
import com.cocos.cocosapp.core.BaseFragment;
import com.cocos.cocosapp.core.RecyclerViewScrollListener;
import com.cocos.cocosapp.core.ScrollChildSwipeRefreshLayout;
import com.cocos.cocosapp.data.entities.SubCatEntity;
import com.cocos.cocosapp.presentation.main.listrestaurante.ListRestaurantActivity;
import com.cocos.cocosapp.utils.ProgressDialogCustom;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by katherine on 28/06/17.
 */

public class CategoryFragment extends BaseFragment implements CategoryContract.View {


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
    private SubCategoryAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private CategoryContract.Presenter mPresenter;
    private ProgressDialogCustom mProgressDialogCustom;

    public CategoryFragment() {
        // Requires empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.startLoad();
      /*  if (countryEntity != null) {
            mPresenter.startLoad(countryEntity.getId());
        } else {
            mPresenter.startLoad(cityEntity.getCountryEntity().getId());
        }*/
    }


    public static CategoryFragment newInstance(Bundle bundle) {
        CategoryFragment fragment = new CategoryFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new CategoryPresenter(this, getContext());

        //idCountry =  (int) getArguments().getSerializable("id_country");
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
                mPresenter.loadOrdersFromPage( 1);
            }
        });

        mPresenter.startLoad();

        unbinder = ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mProgressDialogCustom = new ProgressDialogCustom(getContext(), "Obteniendo datos...");
        mLayoutManager = new LinearLayoutManager(getContext());
        rvList.setLayoutManager(mLayoutManager);
        mAdapter = new SubCategoryAdapter(new ArrayList<SubCatEntity>(), getContext(), (SubCategoryItem) mPresenter);
        rvList.setAdapter(mAdapter);
        mPresenter.startLoad();
    }


    @Override
    public void getListCategories(ArrayList<SubCatEntity> list) {
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
                mPresenter.loadfromNextPage();

               /* if (countryEntity != null) {
                    mPresenter.loadfromNextPage(countryEntity.getId());
                } else {
                    mPresenter.loadfromNextPage(cityEntity.getCountryEntity().getId());
                }*/
            }
        });
    }

    @Override
    public void clickItemCategories(SubCatEntity cityEntity) {
/*
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();

        Bundle bundle = new Bundle();
        bundle.putInt("id", cityEntity.getId());
        RestaurantFragment fragment2 = new RestaurantFragment();
        fragment2.setArguments(bundle);
        fragmentTransaction.replace(R.id.contentContainer_1, fragment2)
                .commit();*/
       /* FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putInt("id", cityEntity.getId());
        RestaurantFragment fragment2 = new RestaurantFragment();
        fragment2.setArguments(bundle);
        fragmentTransaction.replace(R.id.contentContainer_1, fragment2);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();*/
        Bundle bundle = new Bundle();
        bundle.putInt("id", cityEntity.getId());
        bundle.putString("name", cityEntity.getName());
        nextActivity(getActivity(),bundle, ListRestaurantActivity.class, false);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }


    @Override
    public void setPresenter(CategoryContract.Presenter mPresenter) {
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
