package com.cerezaconsulting.cocosapp.presentation.auth;

import android.os.Bundle;

import com.cerezaconsulting.cocosapp.utils.ActivityUtils;
import com.cerezaconsulting.cocosapp.R;
import com.cerezaconsulting.cocosapp.core.BaseActivity;


public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clear);

        LoginFragment fragment = (LoginFragment) getSupportFragmentManager()
                .findFragmentById(R.id.body);

        if (fragment == null) {
            fragment = LoginFragment.newInstance();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    fragment, R.id.body);
        }

        // Create the presenter
        new LoginPresenter(fragment,this);
    }


}
