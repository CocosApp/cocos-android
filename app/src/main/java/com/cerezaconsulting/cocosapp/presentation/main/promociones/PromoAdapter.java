package com.cerezaconsulting.cocosapp.presentation.main.promociones;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cerezaconsulting.cocosapp.R;
import com.cerezaconsulting.cocosapp.core.LoaderAdapter;
import com.cerezaconsulting.cocosapp.data.entities.DescEntity;
import com.cerezaconsulting.cocosapp.utils.OnClickListListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by katherine on 15/05/17.
 */

public class PromoAdapter extends LoaderAdapter<DescEntity> implements OnClickListListener {


    private Context context;
    private PromoItem promoItem;

    public PromoAdapter(ArrayList<DescEntity> descEntities, Context context, PromoItem promoItem) {
        super(context);
        setItems(descEntities);
        this.context = context;
        this.promoItem = promoItem;
    }

    public ArrayList<DescEntity> getItems() {
        return (ArrayList<DescEntity>) getmItems();
    }

    @Override
    public long getYourItemId(int position) {
        return getmItems().get(position).getId();
    }

    @Override
    public RecyclerView.ViewHolder getYourItemViewHolder(ViewGroup parent) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_promo, parent, false);
        return new ViewHolder(root, this);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void bindYourViewHolder(RecyclerView.ViewHolder holder, int position) {
        DescEntity descEntity = getItems().get(position);
        if (descEntity.getCard()!=null){

            if(descEntity.getPorc() != null){
                ((ViewHolder) holder).tvName.setText(descEntity.getCard().getName());
                ((ViewHolder) holder).tvDesc.setText(descEntity.getPorc()+"%");
            }else{
                if(descEntity.getPrice()!= null){
                    ((ViewHolder) holder).tvName.setText(descEntity.getCard().getName()+" "+descEntity.getName());
                    ((ViewHolder) holder).tvDesc.setText("S/ "+ descEntity.getPrice());

                }else{
                    ((ViewHolder) holder).tvName.setText(descEntity.getCard().getName()+" "+descEntity.getName());
                    ((ViewHolder) holder).tvDesc.setText(descEntity.getPromotion());
                }
            }
        }else{
            ((ViewHolder) holder).tvName.setText(descEntity.getName());

            if(descEntity.getPorc() != null){
                ((ViewHolder) holder).tvDesc.setText(descEntity.getPorc()+"%");
            }else{
                if(descEntity.getPrice()!= null){
                    ((ViewHolder) holder).tvDesc.setText("S/ "+ descEntity.getPrice());

                }else{
                    ((ViewHolder) holder).tvDesc.setText(descEntity.getPromotion());
                }
            }
        }
    }

    @Override
    public void onClick(int position) {

        DescEntity descEntity = getItems().get(position);
        promoItem.clickItem(descEntity);
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_desc)
        TextView tvDesc;
        @BindView(R.id.container)
        RelativeLayout container;

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
