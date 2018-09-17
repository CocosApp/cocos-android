package com.cocos.cocosapp.presentation.main.home;

import android.location.Location;
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
import com.cocos.cocosapp.data.local.SessionManager;
import com.cocos.cocosapp.presentation.main.listrestaurante.ListRestaurantActivity;
import com.cocos.cocosapp.utils.ProgressDialogCustom;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by junior on 27/08/16.
 */
public class SubCategoryFragment extends BaseFragment implements SubcategoryContract.View {

    private static final String TAG = SubCategoryFragment.class.getSimpleName();

    @BindView(R.id.refresh_layout)
    ScrollChildSwipeRefreshLayout refreshLayout;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.noListIcon)
    ImageView noListIcon;
    @BindView(R.id.noListMain)
    TextView noListMain;
    @BindView(R.id.noList)
    LinearLayout noList;


    private SubcategoryContract.Presenter mPresenter;

    private ProgressDialogCustom mProgressDialogCustom;
    private SubCategoryAdapter mPostAdapter;
    private LinearLayoutManager mlinearLayoutManager;
    private SessionManager mSessionManager;
    private FirebaseAnalytics mFirebaseAnalytics;

    private Double latitude, longitude;
    private String date, time;

    public SubCategoryFragment() {
        // Requires empty public constructor
    }

    public static SubCategoryFragment newInstance() {
        return new SubCategoryFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSessionManager = new SessionManager(getActivity());
        mPresenter = new SubCategoryPresenter(this, getContext());
        EventBus.getDefault().register(this);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }


    @Override
    public void onStart() {
        super.onStart();
        Bundle params = new Bundle();
        params.putString("date", getDateAndTimeNow());
        params.putString("label", "category_button");
        params.putString("so", "android");
        mFirebaseAnalytics.logEvent("category_button", params);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_list, container, false);
        ButterKnife.bind(this, root);


        final ScrollChildSwipeRefreshLayout swipeRefreshLayout =
                (ScrollChildSwipeRefreshLayout) root.findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.colorPrimary),
                ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark),
                ContextCompat.getColor(getActivity(), R.color.colorAccent)
        );
        // Set the scrolling view in the custom SwipeRefreshLayout.
        swipeRefreshLayout.setScrollUpChild(rvList);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.refreshPost();
            }
        });

        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mProgressDialogCustom = new ProgressDialogCustom(getContext(), "Ingresando...");
        mPostAdapter = new SubCategoryAdapter(new ArrayList<SubCatEntity>(), getContext(), (SubCategoryItem) mPresenter);
        mlinearLayoutManager = new LinearLayoutManager(getContext());
        mlinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvList.setAdapter(mPostAdapter);
        rvList.setLayoutManager(mlinearLayoutManager);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpdateLocation(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
    }

    @Override
    public void setPresenter(SubcategoryContract.Presenter presenter) {
        mPresenter = presenter;
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
    }

    @Override
    public void showMessage(String msg) {
        ((BaseActivity) getActivity()).showMessage(msg);
    }

    @Override
    public void showErrorMessage(String message) {
        ((BaseActivity) getActivity()).showMessageError(message);
    }


    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void clickItemCategories(SubCatEntity cityEntity) {
        Bundle bundle = new Bundle();
        bundle.putInt("id", cityEntity.getId());
        bundle.putString("name", cityEntity.getName());

        if(latitude == null){
            showMessage("Estamos localizándolo, intente de nuevo por favor");
        }else{
            bundle.putDouble("latitude", latitude);
            bundle.putDouble("longitude", longitude);
        }

        Bundle params = new Bundle();
        params.putInt("id_user", mSessionManager.getUserEntity().getId());
        params.putString("name_user", mSessionManager.getUserEntity().getFullName());
        params.putInt("id_category", cityEntity.getId());
        params.putString("name_category", cityEntity.getName());
        params.putString("date", getDateAndTimeNow());
        params.putString("label", "detail_category");
        params.putString("so", "android");

        mFirebaseAnalytics.logEvent("detail_category", params);

        nextActivity(getActivity(),bundle, ListRestaurantActivity.class, false);
    }


    @Override
    public void showMorePost(ArrayList<SubCatEntity> postEntities) {
        ArrayList<SubCatEntity> postAux = (ArrayList<SubCatEntity>) mPostAdapter.getmItems();
        postAux.addAll(postEntities);
        mPostAdapter.setItems(postAux);
        mPostAdapter.notifyDataSetChanged();
    }

    @Override
    public void showPost(ArrayList<SubCatEntity> postEntities) {
        mPostAdapter.setItems(postEntities);
        noList.setVisibility(View.GONE);

        if (this.rvList != null && mPostAdapter != null) {
            mPostAdapter.setItems(postEntities);

            if (postEntities.size() > 0) {
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
                    mPresenter.loadPost();

                }
            });

        } else {
            noList.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showLoadMore(boolean active) {
        mPostAdapter.showLoading(active);
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);

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
