package com.cerezaconsulting.cocosapp.core;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;


import com.cerezaconsulting.cocosapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by katherine.
 */
public class LoaderViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.progressbar)
    ProgressBar mProgressBar;

    public LoaderViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}