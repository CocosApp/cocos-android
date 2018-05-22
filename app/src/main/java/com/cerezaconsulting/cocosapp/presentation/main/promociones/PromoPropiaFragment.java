package com.cerezaconsulting.cocosapp.presentation.main.promociones;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cerezaconsulting.cocosapp.R;
import com.cerezaconsulting.cocosapp.core.BaseActivity;
import com.cerezaconsulting.cocosapp.core.BaseFragment;
import com.cerezaconsulting.cocosapp.data.entities.DescEntity;
import com.cerezaconsulting.cocosapp.data.local.SessionManager;
import com.cerezaconsulting.cocosapp.utils.UtilsFacebook;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by katherine on 28/06/17.
 */

public class PromoPropiaFragment extends BaseFragment {

    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.tv_precio)
    TextView tvPrecio;
    @BindView(R.id.image_container)
    RelativeLayout imageContainer;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.tv_terms)
    TextView tvTerms;
    Unbinder unbinder;
    private DescEntity descEntity;
    private SessionManager mSessionManager;
    private String imageRest;

    public PromoPropiaFragment() {
        // Requires empty public constructor
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    public static PromoPropiaFragment newInstance(Bundle bundle) {
        PromoPropiaFragment fragment = new PromoPropiaFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        descEntity = (DescEntity) getArguments().getSerializable("descEntity");
        imageRest = getArguments().getString("imageRest");
        mSessionManager = new SessionManager(getContext());

        //restEntinty = (RestEntinty) getArguments().getSerializable("restEntity");
        setHasOptionsMenu(true);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_discount, container, false);
        unbinder = ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (descEntity != null) {

            if (descEntity.getPhoto() != null) {
                Glide.with(getContext())
                        .load(descEntity.getPhoto())
                        .into(((image)));
            }else {
                Glide.with(getContext())
                        .load(imageRest)
                        .into(((image)));
            }
            if(descEntity.getPrice()!=null){
                tvPrecio.setText(String.valueOf("S/ " + descEntity.getPrice()));
            }else {
                if(descEntity.getPorc()!=null){
                    tvPrecio.setText(String.valueOf(descEntity.getPorc()+"%"));
                }else{
                    tvPrecio.setText(String.valueOf(descEntity.getPromotion()));
                }
            }
            tvText.setText(descEntity.getDescrip());
            tvTitle.setText(descEntity.getName());
        }


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_share, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_send:
                if(mSessionManager.getUserEntity().getFirst_name().equals("INVITADO")){
                    ((BaseActivity) getActivity()).showMessageError("No se puede compartir como invitado, por favor registrarse para continuar");
                }else{


                    if(descEntity.getPhoto()!=null){

                      shareImage(descEntity.getPhoto(), getContext());

                    }else {
                        Toast.makeText(getContext(), "No contamos con una foto para compartir", Toast.LENGTH_SHORT).show();
                    }
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    static public void shareImage(String url, final Context context) {
        Picasso.with(context).load(url).into(new Target() {
            @Override public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("image/*");
                i.putExtra(Intent.EXTRA_STREAM, getLocalBitmapUri(bitmap, context));
                String shareText = "Todos los descuentos en un solo sito";
                shareText = shareText + " https://play.google.com/store/apps/details?id=com.cerezaconsulting.cocosapp";
                // if you have live app then you can share link like below
                //shareText = shareText + "https://play.google.com/store/apps/details?id=" + context.getPackageName();
                i.putExtra(Intent.EXTRA_TEXT, shareText);
                context.startActivity(Intent.createChooser(i, "Share Image"));
            }

            @Override public void onBitmapFailed(Drawable errorDrawable) { }
            @Override public void onPrepareLoad(Drawable placeHolderDrawable) { }
        });
    }
    static public Uri getLocalBitmapUri(Bitmap bmp, Context context) {
        Uri bmpUri = null;
        try {
            File file =  new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_" + System.currentTimeMillis() + ".png");
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }

    @OnClick(R.id.tv_terms)
    public void onViewClicked() {
        Bundle bundle = new Bundle();
        bundle.putString("msg", descEntity.getTerms_condition());
        DialogTerminos dialogTerminos = new DialogTerminos(getContext(),bundle);
        dialogTerminos.show();
    }
}
