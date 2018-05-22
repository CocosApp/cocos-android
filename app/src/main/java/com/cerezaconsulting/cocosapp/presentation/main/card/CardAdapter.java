package com.cerezaconsulting.cocosapp.presentation.main.card;

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
import com.cerezaconsulting.cocosapp.data.entities.CardEntity;
import com.cerezaconsulting.cocosapp.presentation.main.buscador.BuscadorAdapter;
import com.cerezaconsulting.cocosapp.presentation.main.favoritos.FavoritosAdapter;
import com.cerezaconsulting.cocosapp.utils.OnClickListListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by katherine on 15/05/17.
 */

public class CardAdapter extends LoaderAdapter<CardEntity> implements OnClickListListener {


    private Context context;
    private CardItem cardItem;

    public CardAdapter(ArrayList<CardEntity> cardEntity, Context context, CardItem cardItem) {
        super(context);
        setItems(cardEntity);
        this.context = context;
        this.cardItem = cardItem;
    }

    public CardAdapter(ArrayList<CardEntity> cityEntities, Context context) {
        super(context);
        setItems(cityEntities);
        this.context = context;

    }

    public ArrayList<CardEntity> getItems() {
        return (ArrayList<CardEntity>) getmItems();
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
        CardEntity cardEntity = getItems().get(position);
        ((ViewHolder) holder).tvName.setText(cardEntity.getName());
        if (cardEntity.getPhoto() != null) {
            Glide.with(context)
                    .load(cardEntity.getPhoto())
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

        CardEntity cardEntity = getItems().get(position);
        cardItem.clickItem(cardEntity);


    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {



        @BindView(R.id.back)
        ImageView back;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv)
        TextView tv;
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
