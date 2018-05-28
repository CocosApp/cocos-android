package com.cocos.cocosapp.presentation.main.buscador;

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
import com.cocos.cocosapp.R;
import com.cocos.cocosapp.core.LoaderAdapter;
import com.cocos.cocosapp.data.entities.RestauranteResponse;
import com.cocos.cocosapp.utils.OnClickListListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by katherine on 15/05/17.
 */

public class BuscadorAdapter extends LoaderAdapter<RestauranteResponse> implements OnClickListListener {


    private Context context;
    private BuscadorItem restItem;

    public BuscadorAdapter(ArrayList<RestauranteResponse> restEntinties, Context context, BuscadorItem restItem) {
        super(context);
        setItems(restEntinties);
        this.context = context;
        this.restItem = restItem;
    }

    public BuscadorAdapter(ArrayList<RestauranteResponse> restEntinties, Context context) {
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
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new ViewHolder(root, this);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void bindYourViewHolder(RecyclerView.ViewHolder holder, int position) {
        RestauranteResponse restEntinty = getItems().get(position);
        ((ViewHolder) holder).tvName.setText(restEntinty.getName());
        if (restEntinty.getPhoto1()!=null){
            Glide.with(context)
                    .load(restEntinty.getPhoto1())
                    .into(((ViewHolder) holder).back);
        }else{

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


        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.back)
        ImageView back;
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
