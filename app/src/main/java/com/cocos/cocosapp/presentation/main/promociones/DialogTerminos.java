package com.cocos.cocosapp.presentation.main.promociones;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cocos.cocosapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by junior on 08/06/16.
 */
public class DialogTerminos extends AlertDialog {

    @BindView(R.id.im_close)
    ImageView imClose;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_text)
    TextView tvText;

    private String msg;

    public DialogTerminos(Context context, Bundle bundle) {
        super(context);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        final View view = inflater.inflate(R.layout.dialog_terminos_descuentos, null);
        setView(view);
        ButterKnife.bind(this, view);
        msg = bundle.getString("msg");

        if(msg != null){
            tvText.setText(msg);
        }


    }


    @OnClick(R.id.im_close)
    public void onViewClicked() {
        dismiss();
    }
}
