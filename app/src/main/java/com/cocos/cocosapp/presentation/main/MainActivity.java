package com.cocos.cocosapp.presentation.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.cocos.cocosapp.R;
import com.cocos.cocosapp.core.BaseActivity;
import com.cocos.cocosapp.presentation.cocos.FragmentCocos;
import com.cocos.cocosapp.presentation.main.favoritos.FavoritosFragment;
import com.cocos.cocosapp.presentation.main.home.HomeFragment;
import com.cocos.cocosapp.presentation.profile.ProfileFragment;
import com.cocos.cocosapp.utils.ActivityUtils;
import com.cocos.cocosapp.utils.BottomNavigationViewHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kath on 12/12/17.
 */

public class MainActivity extends BaseActivity implements  BottomNavigationView.OnNavigationItemSelectedListener {

    private static final int MENU_HOME = 1;
    private static final int MENU_FAVORITES = 2;
    private static final int MENU_PROFILE = 3;
    private static final int MENU_COCOS = 4;

    BottomNavigationView bottomNavigationView;


    @BindView(R.id.contentContainer_1)
    FrameLayout contentContainer1;
    @BindView(R.id.contentContainer_2)
    FrameLayout contentContainer2;
    @BindView(R.id.contentContainer_3)
    FrameLayout contentContainer3;
    @BindView(R.id.contentContainer_4)
    FrameLayout contentContainer4;
    @BindView(R.id.contentContainer_5)
    FrameLayout contentContainer5;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomBar);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);


        selectedFragment(MENU_HOME);


        HomeFragment fragment = (HomeFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentContainer_1);

        if (fragment == null) {
            fragment = HomeFragment.newInstance();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    fragment, R.id.contentContainer_1);
        }


        FavoritosFragment principalExplorerFragment = (FavoritosFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentContainer_2);

        if (principalExplorerFragment == null) {
            principalExplorerFragment = FavoritosFragment.newInstance();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    principalExplorerFragment, R.id.contentContainer_2);
        }


        ProfileFragment eventsFragment = (ProfileFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentContainer_3);

        if (eventsFragment == null) {
            eventsFragment = ProfileFragment.newInstance();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    eventsFragment, R.id.contentContainer_3);
        }

        FragmentCocos principalProfileFragment = (FragmentCocos) getSupportFragmentManager()
                .findFragmentById(R.id.contentContainer_4);

        if (principalProfileFragment == null) {
            principalProfileFragment = FragmentCocos.newInstance();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    principalProfileFragment, R.id.contentContainer_4);
        }

       /* VetFragment mainVetFragment = (VetFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentContainer_5);

        if (mainVetFragment == null) {
            mainVetFragment = VetFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), mainVetFragment, R.id.contentContainer_5);
        }
        if (first_load){
            selectedFragment(MENU_EVENTS);
        }else {
            selectedFragment(MENU_HOME);
        }*/
    }


    private void selectedFragment(int option) {
        contentContainer1.setVisibility(option == MENU_HOME ? View.VISIBLE : View.GONE);
        contentContainer2.setVisibility(option == MENU_FAVORITES ? View.VISIBLE : View.GONE);
        contentContainer3.setVisibility(option == MENU_PROFILE ? View.VISIBLE : View.GONE);
        contentContainer4.setVisibility(option == MENU_COCOS ? View.VISIBLE : View.GONE);
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.id_home:
                selectedFragment(MENU_HOME);
                break;
            case R.id.id_favoritos:
                selectedFragment(MENU_FAVORITES);
                break;
            case R.id.id_profile:
                selectedFragment(MENU_PROFILE);
                break;
            case R.id.id_cocos:
                selectedFragment(MENU_COCOS);
                break;
            default:
                break;
        }

        return true;

    }
}

