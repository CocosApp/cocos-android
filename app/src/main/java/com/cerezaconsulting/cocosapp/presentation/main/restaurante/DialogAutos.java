package com.cerezaconsulting.cocosapp.presentation.main.restaurante;


import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cerezaconsulting.cocosapp.R;

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

    //private CreateMenuContract.View mView;

    public DialogAutos(Context context) {
        super(context);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        final View view = inflater.inflate(R.layout.dialog_cars, null);
        ButterKnife.bind(this, view);
        setView(view);
        mContext = context;

        //this.mView = mView;

    }

    @OnClick({R.id.btn_uber, R.id.btn_cabify, R.id.btn_easy, R.id.tv_cancelar})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_uber:
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

