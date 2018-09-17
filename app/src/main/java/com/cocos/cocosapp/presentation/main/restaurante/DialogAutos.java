package com.cocos.cocosapp.presentation.main.restaurante;


import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cocos.cocosapp.R;
import com.cocos.cocosapp.data.local.SessionManager;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by katherine on 23/03/17.
 */

public class DialogAutos extends AlertDialog {


    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.btn_uber)
    LinearLayout btnUber;
    @BindView(R.id.btn_easy)
    LinearLayout btnEasy;
    @BindView(R.id.container_taxi)
    LinearLayout containerTaxi;
    @BindView(R.id.tv_cancelar)
    TextView tvCancelar;
    @BindView(R.id.btn_cabify)
    LinearLayout btnCabify;
    private Context mContext;

    private String date, time;
    private FirebaseAnalytics mFirebaseAnalytics;
    private SessionManager mSessionManager;
    private int idRestaurant;
    private String nameRestaurant;


    //private CreateMenuContract.View mView;

    public DialogAutos(Context context, int idRestaurant, String nameRestaurant) {
        super(context);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        final View view = inflater.inflate(R.layout.dialog_cars, null);
        ButterKnife.bind(this, view);
        setView(view);
        this.idRestaurant = idRestaurant;
        this.nameRestaurant = nameRestaurant;
        mContext = context;
        mSessionManager = new SessionManager(context);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);


        //this.mView = mView;

    }

    @OnClick({R.id.btn_uber, R.id.btn_cabify, R.id.btn_easy, R.id.tv_cancelar})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_uber:


                Bundle paramsUber = new Bundle();
                paramsUber.putInt("id_user", mSessionManager.getUserEntity().getId());
                paramsUber.putString("name_user", mSessionManager.getUserEntity().getFullName());
                paramsUber.putInt("id_restaurant", idRestaurant);
                paramsUber.putString("name_restaurant", nameRestaurant);
                paramsUber.putString("date", getDateAndTimeNow());
                paramsUber.putString("label", "take_uber");
                paramsUber.putString("so", "android");

                mFirebaseAnalytics.logEvent("take_uber", paramsUber);

                PackageManager pm = getContext().getPackageManager();
                try {
                    pm.getPackageInfo("com.ubercab", PackageManager.GET_ACTIVITIES);
                    String uri = "uber://?action=setPickup";
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(uri));
                    mContext.startActivity(intent);
                } catch (PackageManager.NameNotFoundException e) {
                    try {
                        mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.ubercab")));
                    } catch (ActivityNotFoundException anfe) {
                        mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=com.ubercab")));
                    }
                }
                break;
            case R.id.btn_cabify:

                Bundle paramsCabify = new Bundle();
                paramsCabify.putInt("id_user", mSessionManager.getUserEntity().getId());
                paramsCabify.putString("name_user", mSessionManager.getUserEntity().getFullName());
                paramsCabify.putInt("id_restaurant", idRestaurant);
                paramsCabify.putString("name_restaurant", nameRestaurant);
                paramsCabify.putString("date", getDateAndTimeNow());
                paramsCabify.putString("label", "take_cabify");
                paramsCabify.putString("so", "android");

                mFirebaseAnalytics.logEvent("take_cabify", paramsCabify);

                PackageManager pm2 = getContext().getPackageManager();
                try {
                    pm2.getPackageInfo("com.cabify.rider", PackageManager.GET_ACTIVITIES);
                    String uri = "cabify://cabify";
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(uri));
                    mContext.startActivity(intent);
                } catch (PackageManager.NameNotFoundException e) {
                    try {
                        mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.cabify.rider")));
                    } catch (ActivityNotFoundException anfe) {
                        mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=com.cabify.rider")));
                    }
                }
                break;
            case R.id.btn_easy:
                Intent i3 = new Intent(Intent.ACTION_VIEW);
                i3.setData(Uri.parse("https://play.google.com/store/apps/details?id=br.com.easytaxi"));
                mContext.startActivity(i3);
                break;
            case R.id.tv_cancelar:
                dismiss();
                break;
        }
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

    public void LaunchComponent(String packageName) {
/*       PackageManager pm = getContext().getPackageManager();
        try {

            Intent i;
            i = pm.getLaunchIntentForPackage(packageName);
            i.addCategory(Intent.CATEGORY_LAUNCHER);
            mContext.startActivity(i);
           *//* pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(packageName));
            i.setPackage(packageName);
            mContext.startActivity(i);*//*
        } catch (PackageManager.NameNotFoundException e) {
            try {
                mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+packageName)));
            } catch (android.content.ActivityNotFoundException anfe) {
                mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id="+packageName)));
            }
        */
    }


}

    /* PackageManager pm = getContext().getPackageManager();
        try {
            pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
            Uri uri = Uri.parse("smsto:"+ number);
            Intent i = new Intent(Intent.ACTION_SENDTO, uri);
            i.setPackage("com.whatsapp");
            startActivity(i);
        } catch (PackageManager.NameNotFoundException e) {
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.whatsapp")));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=com.whatsapp")));
            }
        }*/

