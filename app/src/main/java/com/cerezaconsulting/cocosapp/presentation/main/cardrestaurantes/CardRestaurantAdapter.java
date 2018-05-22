package com.cerezaconsulting.cocosapp.presentation.main.cardrestaurantes;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cerezaconsulting.cocosapp.R;
import com.cerezaconsulting.cocosapp.core.LoaderAdapter;
import com.cerezaconsulting.cocosapp.data.entities.RestauranteResponse;
import com.cerezaconsulting.cocosapp.presentation.main.buscador.BuscadorAdapter;
import com.cerezaconsulting.cocosapp.utils.OnClickListListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by katherine on 15/05/17.
 */

public class CardRestaurantAdapter extends LoaderAdapter<RestauranteResponse> implements OnClickListListener {


    private Context context;
    private CardRestItem restItem;

    public CardRestaurantAdapter(ArrayList<RestauranteResponse> restEntinties, Context context, CardRestItem restItem) {
        super(context);
        setItems(restEntinties);
        this.context = context;
        this.restItem = restItem;
    }

    public CardRestaurantAdapter(ArrayList<RestauranteResponse> restEntinties, Context context) {
        super(context);
        setItems(restEntinties);
        this.context = context;
    }

    public ArrayList<RestauranteResponse> getItems() {
        return (ArrayList<RestauranteResponse>) getmItems();
    }

    @Override
    public long getYourItemId(int position) {
        return getmItems().get(position).getId();
    }

    @Override
    public RecyclerView.ViewHolder getYourItemViewHolder(ViewGroup parent) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_restaurant, parent, false);
        return new ViewHolder(root, this);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void bindYourViewHolder(RecyclerView.ViewHolder holder, int position) {
        RestauranteResponse restauranteResponse = getItems().get(position);
        ((ViewHolder) holder).tvName.setText(restauranteResponse.getName());
        if(restauranteResponse.getSubcategory().size()>0){
            ((ViewHolder) holder).tvCategoty.setText(restauranteResponse.getSubcategory().get(0).getDescrip());
        }else {
            ((ViewHolder) holder).tvCategoty.setText("Sin categoria");

        }
        if (restauranteResponse.getPhoto1() != null) {
            Glide.with(context)
                    .load(restauranteResponse.getPhoto1())
                    .into(((ViewHolder) holder).back);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ((ViewHolder) holder).back.setImageDrawable(context.getDrawable(R.drawable.cocos_app_default));
            }else{
                ((ViewHolder) holder).back.setImageDrawable(context.getResources().getDrawable(R.drawable.cocos_app_default));
            }
        }
    }

    @Override
    public void onClick(int position) {
        RestauranteResponse restEntinty = getItems().get(position);
        restItem.clickItem(restEntinty);
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.back)
        ImageView back;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_categoty)
        TextView tvCategoty;

        private OnClickListListener onClickListListener;

        ViewHolder(View itemView, OnClickListListener onClickListListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.onClickListListener = onClickListListener;
            this.itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onClickListListener.onClick(getAdapterPosition());
        }
    }
}
