package com.cerezaconsulting.cocosapp.presentation.cocos;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneNumberUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.util.Util;
import com.cerezaconsulting.cocosapp.R;
import com.cerezaconsulting.cocosapp.core.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by kath on 28/12/17.
 */

public class FragmentCocos extends BaseFragment {
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.btn_comunicate)
    Button btnComunicate;
    @BindView(R.id.btn_afiliate)
    Button btnAfiliate;
    Unbinder unbinder;

    public FragmentCocos() {
        // Requires empty public constructor
    }

    public static FragmentCocos newInstance() {
        return new FragmentCocos();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_cocos, container, false);
        unbinder = ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btn_comunicate, R.id.btn_afiliate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_comunicate:
                Uri uri = Uri.parse("http://appcocos.com");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                //AbrirWhatsApp();
                break;
            case R.id.btn_afiliate:
                //Toast.makeText(getContext(), "Próximamente", Toast.LENGTH_SHORT).show();
               // AbrirWhatsApp();
                Uri uri2 = Uri.parse("http://appcocos.com/auth/signup");
                Intent intent2 = new Intent(Intent.ACTION_VIEW, uri2);
                startActivity(intent2);
                break;
        }
    }

    private void AbrirWhatsApp()
    {
        PackageManager pm = getContext().getPackageManager();
        try {
            pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
            Uri uri = Uri.parse("smsto:+51935407857");
            Intent i = new Intent(Intent.ACTION_SENDTO, uri);
            i.setPackage("com.whatsapp");
            startActivity(i);
        } catch (PackageManager.NameNotFoundException e) {
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.whatsapp")));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=com.whatsapp")));
            }
        }


    }

}
