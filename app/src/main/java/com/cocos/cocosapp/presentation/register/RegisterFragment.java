package com.cocos.cocosapp.presentation.register;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.cocos.cocosapp.R;
import com.cocos.cocosapp.core.BaseActivity;
import com.cocos.cocosapp.core.BaseFragment;
import com.cocos.cocosapp.data.entities.UserEntity;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by katherine on 12/05/17.
 */

public class RegisterFragment extends BaseFragment implements RegisterContract.View, Validator.ValidationListener {

    @NotEmpty(message = "Este campo no puede ser vacío")
    @Length(max = 250, message = "Cantidad de dígitos no permitida", sequence = 1)
    @Email(message = "Email inválido")
    @BindView(R.id.et_email)
    EditText etEmail;

    @NotEmpty(message = "Este campo no puede ser vacío", sequence = 1)
    @Length(min = 6, max = 30, message = "La contraseña debe ser de al menos 6 dígitos", sequence = 2)
    @BindView(R.id.et_password)
    EditText etPassword;

    @Length(max = 50, message = "Cantidad de dígitos no permitida", sequence = 3)
    @NotEmpty(message = "Este campo no puede ser vacío", sequence = 4)
    @BindView(R.id.et_firstname)
    EditText etFirstname;


    @Length(max = 50, message = "Cantidad de dígitos no permitida", sequence = 5)
    @NotEmpty(message = "Este campo no puede ser vacío", sequence = 6)
    @BindView(R.id.et_lastname)
    EditText etLastname;
    ;

    @BindView(R.id.btn_man)
    RadioButton btnMan;

    @BindView(R.id.btn_woman)
    RadioButton btnWoman;

    @BindView(R.id.btn_create)
    Button btnCreate;
    Unbinder unbinder;

    @NotEmpty(message = "Este campo no puede ser vacío", sequence = 1)
    @Length(min = 6, max = 30, message = "La contraseña debe ser de al menos 6 dígitos", sequence = 2)
    @BindView(R.id.et_password_second)
    TextInputEditText etPasswordSecond;
    @BindView(R.id.tv_terminos)
    TextView tvTerminos;
    @BindView(R.id.tv_politicas)
    TextView tvPoliticas;

    public static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;

    private Validator validator;
    private boolean isLoading = false;
    private ProgressDialog progressDialog;
    private RegisterContract.Presenter mPresenter;
    UserEntity userEntity;
    private String gender;
    private String url;
    public RegisterFragment() {
        // Requires empty public constructor
    }

    public static RegisterFragment newInstance() {
        RegisterFragment fragment = new RegisterFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_register, container, false);
        unbinder = ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creando cuenta...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminateDrawable(getResources().getDrawable(R.drawable.circle_progress));
        validator = new Validator(this);
        validator.setValidationListener(this);

    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void registerSuccessful(final UserEntity userEntity) {
        showMessage("Registro exitoso");
        getActivity().finish();
    }

    @Override
    public void errorRegister(String msg) {
        showErrorMessage(msg);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void setPresenter(RegisterContract.Presenter mPresenter) {
        this.mPresenter = mPresenter;
    }

    @Override
    public void setLoadingIndicator(boolean active) {

        if (progressDialog != null) {

            if (active) {
                progressDialog.show();
            } else {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
        }
    }

    @Override
    public void showMessage(String message) {
        ((BaseActivity) getActivity()).showMessage(message);
    }

    @Override
    public void showErrorMessage(String message) {
        ((BaseActivity) getActivity()).showMessageError(message);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onValidationSucceeded() {

        if (etPassword.getText().toString().equals(etPasswordSecond.getText().toString())) {

            userEntity = new UserEntity(etEmail.getText().toString(), etLastname.getText().toString(),
                    etFirstname.getText().toString(),
                    etPassword.getText().toString());
            mPresenter.registerUser(userEntity);
            isLoading = true;

        } else {
            showMessage("Por favor ingresar las contraseñas iguales");
        }
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(getContext());
            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
            }
        }
    }

    @OnClick({R.id.btn_man, R.id.btn_woman, R.id.btn_create, R.id.tv_terminos, R.id.tv_politicas})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_man:
                gender = "M";
                break;
            case R.id.btn_woman:
                gender = "F";
                break;
            case R.id.btn_create:
                validator.validate();

                break;
            case R.id.tv_terminos:
                url = "http://cocos.cerezaconsulting.com/media/restaurant_pdf/Terminos.pdf";
                if (checkSelfPermission()) {
                        downloadFile(url);
                }
                break;
            case R.id.tv_politicas:
                url = "http://cocos.cerezaconsulting.com/media/restaurant_pdf/Politicas.pdf";
                if (checkSelfPermission()) {
                    downloadFile(url);
                }
                break;
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
                    downloadFile(url);
                } else {
                    //code for deny
                }
                break;
        }
    }


    public void downloadFile(String url) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setTitle("Descargando archivo");
        request.setDescription("File is being downloaded...");
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        String nameOfFile = URLUtil.guessFileName(url, null, MimeTypeMap.getFileExtensionFromUrl(url));
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, nameOfFile);
        DownloadManager manager = (DownloadManager) getContext().getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);
    }
}
