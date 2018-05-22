package com.cerezaconsulting.cocosapp.presentation.main.informacion;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cerezaconsulting.cocosapp.R;
import com.cerezaconsulting.cocosapp.core.BaseFragment;
import com.cerezaconsulting.cocosapp.data.entities.RestEntinty;
import com.cerezaconsulting.cocosapp.data.entities.SubCatEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by katherine on 28/06/17.
 */

public class InformacionFragment extends BaseFragment {

    public static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;

    @BindView(R.id.tv_servicio)
    TextView tvServicio;
    @BindView(R.id.tv_direccion)
    TextView tvDireccion;
    @BindView(R.id.tv_horario)
    TextView tvHorario;
    @BindView(R.id.tv_cell)
    TextView tvCell;
    @BindView(R.id.tv_carta)
    TextView tvCarta;
    Unbinder unbinder;
    @BindView(R.id.tv_llevame)
    TextView tvLlevame;
    Map<String, Integer> perms = new HashMap<>();
    @BindView(R.id.btn_facebook)
    LinearLayout btnFacebook;
    @BindView(R.id.btn_whatsapp)
    LinearLayout btnWhatsapp;
    @BindView(R.id.wifi)
    ImageView wifi;
    @BindView(R.id.aire_acondicionado)
    ImageView aireAcondicionado;
    @BindView(R.id.servicio_discapacitados)
    ImageView servicioDiscapacitados;
    @BindView(R.id.bar)
    ImageView bar;
    @BindView(R.id.valet)
    ImageView valet;
    @BindView(R.id.estacionamiento)
    ImageView estacionamiento;

    private SubCatEntity subCatEntity;
    private String daySelected;
    private ServicesAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private RestEntinty restEntinty;
    private Context mContext;
    private String myHTTPUrl;


    public InformacionFragment() {
        // Requires empty public constructor
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    public static InformacionFragment newInstance() {
        //PromoFragment fragment = new PromoFragment();
        //fragment.setArguments(bundle);
        return new InformacionFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        restEntinty = (RestEntinty) bundle.getSerializable("restEntity");
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_information, container, false);
        unbinder = ButterKnife.bind(this, root);

        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (restEntinty.getMobile() != null) {
            tvCell.setText(restEntinty.getMobile());
        }

        if (restEntinty.getSchedule().size() != 0) {
            tvHorario.setText(restEntinty.getSchedule().get(0).getName());
        } else {
            tvHorario.setText("Sin horario registrado");
        }

        if (restEntinty.getAddress() != null) {
            tvDireccion.setText(restEntinty.getAddress());
        }

        for (int i = 0; i <restEntinty.getService().size() ; i++) {
            UiServices(restEntinty.getService().get(i).getName());
        }

        wifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Wi - fi", Toast.LENGTH_SHORT).show();

            }
        });

        servicioDiscapacitados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Servicios Discapacitados", Toast.LENGTH_SHORT).show();

            }
        });

        valet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Valet Parking", Toast.LENGTH_SHORT).show();

            }
        });

        estacionamiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Estacionamiento", Toast.LENGTH_SHORT).show();

            }
        });

       /* aireAcondicionado.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                Toast.makeText(getContext(), "Aire Acondicionado", Toast.LENGTH_SHORT).show();
                return false;
            }
        });*/
        bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Bar", Toast.LENGTH_SHORT).show();

            }
        });

        aireAcondicionado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Aire Acondicionado", Toast.LENGTH_SHORT).show();

            }
        });




    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.tv_llevame, R.id.tv_carta, R.id.btn_facebook, R.id.btn_whatsapp})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_llevame:
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=" + restEntinty.getLatitude() + "," + restEntinty.getLongitude()));
                getContext().startActivity(i);
                break;

            case R.id.tv_carta:
                myHTTPUrl = restEntinty.getFood_letter();
                if (checkSelfPermission()) {
                    if (myHTTPUrl != null) {
                        downloadFile();

                    } else {
                        Toast.makeText(getContext(), "No cuenta con carta disponible", Toast.LENGTH_SHORT).show();
                    }
                }

                break;
            case R.id.btn_facebook:

                if (restEntinty.getFacebook() != null) {

                    PackageManager pm = getContext().getPackageManager();
                    startActivity(newFacebookIntent(pm, restEntinty.getFacebook(), getContext()));
                } else {
                    Toast.makeText(getContext(), "Facebook no disponible", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btn_whatsapp:
//restEntinty.getWhatsapp().equals("")&&
                if (restEntinty.getWhatsapp().equals("")) {
                    Toast.makeText(getContext(), "Whatsapp no disponible", Toast.LENGTH_SHORT).show();
                } else {
                    AbrirWhatsApp(restEntinty.getWhatsapp());
                }
                break;
        }
    }

    @NonNull
    public static Intent newFacebookIntent(PackageManager pm, String url, Context context) {
        Uri uri = Uri.parse(url);
        try {
            ApplicationInfo applicationInfo = pm.getApplicationInfo("com.facebook.katana", 0);
            if (applicationInfo.enabled) {
                // http://stackoverflow.com/a/24547437/1048340
                uri = Uri.parse("fb://facewebmodal/f?href=" + url);
            } else {
                Toast.makeText(context, "No cuenta con una aplicación compatible", Toast.LENGTH_SHORT).show();
            }
        } catch (PackageManager.NameNotFoundException ignored) {
        }
        return new Intent(Intent.ACTION_VIEW, uri);
    }

    private void AbrirWhatsApp(String number) {
        PackageManager pm = getContext().getPackageManager();
        try {
            pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
            Uri uri = Uri.parse("smsto:" + number);
            Intent i = new Intent(Intent.ACTION_SENDTO, uri);
            i.setPackage("com.whatsapp");
            startActivity(i);
        } catch (PackageManager.NameNotFoundException e) {
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.whatsapp")));
            } catch (ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=com.whatsapp")));
            }
        }


    }


    public boolean checkSelfPermission() {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getContext());
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permisos Necesarios!");
                    alertBuilder.setMessage("Para descargar el archivo es necesario brindar los permisos correspondientes");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) getContext(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();
                } else {
                    ActivityCompat.requestPermissions((Activity) getContext(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    downloadFile();
                } else {
                    //code for deny
                }
                break;
        }
    }


    public void downloadFile() {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(myHTTPUrl));
        request.setTitle("Descargando archivo");
        request.setDescription("File is being downloaded...");
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        String nameOfFile = URLUtil.guessFileName(myHTTPUrl, null, MimeTypeMap.getFileExtensionFromUrl(myHTTPUrl));
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, nameOfFile);
        DownloadManager manager = (DownloadManager) getContext().getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);
    }


    @Override
    public void onPause() {
        super.onPause();
    }


    public void UiServices(String id) {

        switch (id) {
            case "Aire acondicionado":
                aireAcondicionado.setColorFilter(getResources().getColor(R.color.colorPrimary));
                break;

            case "Bar":
                bar.setColorFilter(getResources().getColor(R.color.colorPrimary));
                break;

            case "Estacionamiento":
                estacionamiento.setColorFilter(getResources().getColor(R.color.colorPrimary));
                break;

            case "Servicios para discapacitados":
                servicioDiscapacitados.setColorFilter(getResources().getColor(R.color.colorPrimary));
                break;

            case "Valet Parking":
                valet.setColorFilter(getResources().getColor(R.color.colorPrimary));
                break;

            case "Wi-Fi":
                wifi.setColorFilter(getResources().getColor(R.color.colorPrimary));
                break;
        }


    }


}
