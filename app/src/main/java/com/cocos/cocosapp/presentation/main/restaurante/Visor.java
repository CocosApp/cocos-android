package com.cocos.cocosapp.presentation.main.restaurante;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.cocos.cocosapp.R;

/**
 * Created by kath on 09/01/18.
 */

public class Visor extends Activity {
    private WebView web;
    String url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_visor);
        //prefs = getSharedPreferences("ayuntaap", Context.MODE_PRIVATE);
        Bundle bundle = getIntent().getExtras();
        url = bundle.getString("url");
        web = (WebView)findViewById(R.id.webView);
        WebSettings settings = web.getSettings();
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setDomStorageEnabled(true);
        settings.setJavaScriptEnabled(false);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setBuiltInZoomControls(true);
        //String url = prefs.getString("urld","");
        web.loadUrl(url);

    }
}



