package com.cocos.cocosapp.presentation.main.cardrestaurantes;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import com.cocos.cocosapp.R;
import com.cocos.cocosapp.core.BaseActivity;
import com.cocos.cocosapp.utils.ActivityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kath on 28/12/17.
 */

public class CardRestaurantActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.body)
    FrameLayout body;
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        name = bundle.getString("name");
        toolbar.setTitle(name);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        CardRestaurantFragment fragment = (CardRestaurantFragment) getSupportFragmentManager()
                .findFragmentById(R.id.body);

        if (fragment == null) {
            fragment = CardRestaurantFragment.newInstance(getIntent().getExtras());

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    fragment, R.id.body);
        }

        // Create the presenter
        // new LoginPresenter(fragment,this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
