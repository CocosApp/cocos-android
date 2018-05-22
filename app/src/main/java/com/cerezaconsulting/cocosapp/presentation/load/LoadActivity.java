package com.cerezaconsulting.cocosapp.presentation.load;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.cerezaconsulting.cocosapp.R;
import com.cerezaconsulting.cocosapp.core.BaseActivity;
import com.cerezaconsulting.cocosapp.data.local.SessionManager;
import com.cerezaconsulting.cocosapp.presentation.auth.LoginActivity;
import com.cerezaconsulting.cocosapp.presentation.main.MainActivity;
import com.cerezaconsulting.cocosapp.presentation.slide.SlideActivity;
import com.crashlytics.android.Crashlytics;
import com.google.firebase.crash.FirebaseCrash;

import java.util.Timer;
import java.util.TimerTask;

import io.fabric.sdk.android.Fabric;


/**
 * Created by katherine on 12/05/17.
 */

public class LoadActivity extends BaseActivity {



    // Set the duration of the splash screen
    private static final long SPLASH_SCREEN_DELAY = 2000;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loader);

        Fabric.with(this, new Crashlytics());
        FirebaseCrash.report(new Exception("My first Android non-fatal error"));


        TimerTask task = new TimerTask() {
            @Override
            public void run() {

              /*  // Start the next activity
                Intent mainIntent = new Intent().setClass(
                        LoadActivity.this, SlideActivity.class);
                startActivity(mainIntent);

                // Close the activity so the user won't able to go back this
                // activity pressing Back button
                finish();*/
                if (savedInstanceState == null)
                    initialProcess();
            }
        };

        // Simulate a long loading process on application startup.
        Timer timer = new Timer();
        timer.schedule(task, SPLASH_SCREEN_DELAY);



    }

    private void initialProcess() {
        SessionManager mSessionManager = new SessionManager(getApplicationContext());
        if(mSessionManager.isLogin()){

            next(this,null, MainActivity.class, true);
        }else{
            next(this,null, SlideActivity.class, true);
        }
    }
}
