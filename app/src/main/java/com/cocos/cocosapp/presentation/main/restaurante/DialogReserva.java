package com.cocos.cocosapp.presentation.main.restaurante;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cocos.cocosapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by junior on 08/06/16.
 */
public class DialogReserva extends AlertDialog {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.question)
    TextView question;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.tv_aceptar)
    TextView tvAceptar;
    @BindView(R.id.ly_cancelar)
    LinearLayout lyCancelar;
    @BindView(R.id.ly_aceptar)
    LinearLayout lyAceptar;
    private String tlf;

    public DialogReserva(Context context, Bundle bundle) {
        super(context);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        final View view = inflater.inflate(R.layout.dialog_confirm_reserva, null);
        setView(view);
        ButterKnife.bind(this, view);
        tlf = bundle.getString("tlf");
    }

    @OnClick({R.id.ly_cancelar, R.id.ly_aceptar})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ly_cancelar:
                dismiss();
                break;
            case R.id.ly_aceptar:
                aceptar();
                dismiss();
                break;
        }
    }

    public void aceptar() {
        Uri number = Uri.parse("tel:" + tlf);
        Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
        getContext().startActivity(callIntent);
    }
}
