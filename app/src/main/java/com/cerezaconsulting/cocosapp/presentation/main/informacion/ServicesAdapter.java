package com.cerezaconsulting.cocosapp.presentation.main.informacion;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cerezaconsulting.cocosapp.R;
import com.cerezaconsulting.cocosapp.core.LoaderAdapter;
import com.cerezaconsulting.cocosapp.data.entities.ServEntity;
import com.cerezaconsulting.cocosapp.presentation.main.buscador.BuscadorAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by katherine on 15/05/17.
 */

public class ServicesAdapter extends LoaderAdapter<ServEntity> {


    private Context context;

    public ServicesAdapter(ArrayList<ServEntity> servEntities, Context context) {
        super(context);
        setItems(servEntities);
        this.context = context;
    }

    public ArrayList<ServEntity> getItems() {
        return (ArrayList<ServEntity>) getmItems();
    }

    @Override
    public long getYourItemId(int position) {
        return getmItems().get(position).getId();
    }

    @Override
    public RecyclerView.ViewHolder getYourItemViewHolder(ViewGroup parent) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_service, parent, false);
        return new ViewHolder(root);
    }

    @Override
    public void bindYourViewHolder(RecyclerView.ViewHolder holder, int position) {
        ServEntity servEntity = getItems().get(position);



        switch (servEntity.getId()){
            case 1:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ((ViewHolder) holder).image.setImageDrawable(context.getDrawable(R.drawable.ic_wifi));
                }else{
                    ((ViewHolder) holder).image.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_wifi));
                }
                break;

            case 2:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ((ViewHolder) holder).image.setImageDrawable(context.getDrawable(R.drawable.ic_aire));
                }else{
                    ((ViewHolder) holder).image.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_aire));
                }

                break;

            case 3:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ((ViewHolder) holder).image.setImageDrawable(context.getDrawable(R.drawable.ic_estacionamiento));
                }else{
                    ((ViewHolder) holder).image.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_estacionamiento));
                }

                break;

            case 4:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ((ViewHolder) holder).image.setImageDrawable(context.getDrawable(R.drawable.ic_valet));
                }else{
                    ((ViewHolder) holder).image.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_valet));
                }

                break;

            case 5:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ((ViewHolder) holder).image.setImageDrawable(context.getDrawable(R.drawable.ic_valet));
                }else{
                    ((ViewHolder) holder).image.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_valet));
                }

                break;

            case 6:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ((ViewHolder) holder).image.setImageDrawable(context.getDrawable(R.drawable.ic_asiento_reservado));
                }else{
                    ((ViewHolder) holder).image.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_asiento_reservado));
                }

                break;
        }
    }


    static class ViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.image)
        ImageView image;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

}
