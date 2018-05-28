package com.cocos.cocosapp.presentation.main.home;

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
import com.cocos.cocosapp.data.entities.SubCatEntity;
import com.cocos.cocosapp.utils.OnClickListListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by katherine on 15/05/17.
 */

public class SubCategoryAdapter extends LoaderAdapter<SubCatEntity> implements OnClickListListener {


    private Context context;
    private SubCategoryItem citiesItem;

    public SubCategoryAdapter(ArrayList<SubCatEntity> cityEntities, Context context, SubCategoryItem categoryItem) {
        super(context);
        setItems(cityEntities);
        this.context = context;
        this.citiesItem = categoryItem;
    }

    public SubCategoryAdapter(ArrayList<SubCatEntity> cityEntities, Context context) {
        super(context);
        setItems(cityEntities);
        this.context = context;

    }

    public ArrayList<SubCatEntity> getItems() {
        return (ArrayList<SubCatEntity>) getmItems();
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
        SubCatEntity subCatEntity = getItems().get(position);
        ((ViewHolder) holder).tvName.setText(subCatEntity.getName());

        if (subCatEntity.getPhoto()!=null){
            Glide.with(context)
                    .load(subCatEntity.getPhoto())
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

        SubCatEntity subCatEntity = getItems().get(position);
        citiesItem.clickItem(subCatEntity);
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
