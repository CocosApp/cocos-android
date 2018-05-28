package com.cocos.cocosapp;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by junior on 25/12/16.
 */

public class CocosAplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Gotham-Book.otf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );


    }





}
