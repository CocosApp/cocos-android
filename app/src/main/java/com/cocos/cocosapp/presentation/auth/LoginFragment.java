package com.cocos.cocosapp.presentation.auth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cocos.cocosapp.R;
import com.cocos.cocosapp.core.BaseActivity;
import com.cocos.cocosapp.core.BaseFragment;
import com.cocos.cocosapp.data.entities.UserEntity;
import com.cocos.cocosapp.presentation.auth.dialogs.DialogForgotPassword;
import com.cocos.cocosapp.presentation.main.PermisosActivity;
import com.cocos.cocosapp.presentation.register.RegisterActivity;
import com.cocos.cocosapp.utils.FacebookAuth;
import com.cocos.cocosapp.utils.GoogleAuth;
import com.cocos.cocosapp.utils.ProgressDialogCustom;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by junior on 27/08/16.
 */
public class LoginFragment extends BaseFragment implements LoginContract.View, Validator.ValidationListener,
        GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = LoginActivity.class.getSimpleName();


    CallbackManager mCallbackManager;

    @NotEmpty(message = " ")
    @Email(message = "Email inválido")
    @BindView(R.id.et_email)
    EditText etEmail;

    @NotEmpty(message = "Contraseña inválida")
    @Password(message = "Debe contener mínimo 6 dígitos")
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.login_button)
    Button loginButton;
    @BindView(R.id.google_button)
    Button googleButton;
    Unbinder unbinder;
    @BindView(R.id.et_register)
    TextView etRegister;
    @BindView(R.id.tv_forgot_pass)
    TextView tvForgotPass;
    @BindView(R.id.tv_invited)
    TextView tvInvited;
    private LoginContract.Presenter mPresenter;
    private ProgressDialogCustom mProgressDialogCustom;
    private DialogForgotPassword dialogForgotPassword;
    private Validator validator;
    private boolean isLoading = false;


    private FacebookAuth facebookAuth;
    private GoogleAuth googleAuth;
    private static final int RC_SIGN_IN = 007;


    GoogleSignInClient mGoogleSignInClient;
    private GoogleApiClient mGoogleApiClient;


    public LoginFragment() {
        // Requires empty public constructor
    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCallbackManager = CallbackManager.Factory.create();
        //LoginManager.getInstance().registerCallback(mCallbackManager, this);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getResources().getString(R.string.google_auth_key))
                .requestEmail()
                .requestProfile()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .enableAutoManage(getActivity(), this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();



    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_login_complete, container, false);
        dialogForgotPassword = new DialogForgotPassword(getContext(), this);
        unbinder = ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mProgressDialogCustom = new ProgressDialogCustom(getContext(), "Ingresando...");
        validator = new Validator(this);
        validator.setValidationListener(this);
    }


    @Override
    public void setPresenter(LoginContract.Presenter mPresenter) {
        this.mPresenter = mPresenter;
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        if (getView() == null) {
            return;
        }
        if (active) {
            mProgressDialogCustom.show();
        } else {
            if (mProgressDialogCustom.isShowing()) {
                mProgressDialogCustom.dismiss();
            }
        }
    }

    @Override
    public void showMessage(String msg) {
        ((BaseActivity) getActivity()).showMessage(msg);
    }

    @Override
    public void showErrorMessage(String message) {
        ((BaseActivity) getActivity()).showMessageError(message);
    }

    @Override
    public void loginSuccessful(UserEntity userEntity) {
        newActivityClearPreview(getActivity(), null, PermisosActivity.class);
        showMessage("Login exitoso");
    }

    @Override
    public void errorLogin(String msg) {
        showErrorMessage(msg);
    }

    @Override
    public void showDialogForgotPassword() {
        dialogForgotPassword.show();
    }

    @Override
    public void showSendEmail(String email) {
        mPresenter.sendEmail(email);
        dialogForgotPassword.dismiss();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result_one = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            int statusCodeOne = result_one.getStatus().getStatusCode();
            Log.e("data gmail",result_one.isSuccess()+" - "+statusCodeOne);
            if (result_one.isSuccess()) {
                GoogleSignInAccount acct = result_one.getSignInAccount();
                // Get account information

                Log.e("GMAIL",acct.getDisplayName()+",, "+acct.getEmail()+" ... "+acct.getIdToken());
                mPresenter.loginUserGoogle(acct.getIdToken());
            }
        }

        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == RC_SIGN_IN) {
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                int statusCode = result.getStatus().getStatusCode();
                Log.e("data gmail",result.isSuccess()+" - "+statusCode);
                if (result.isSuccess()) {
                    GoogleSignInAccount acct = result.getSignInAccount();
                    // Get account information

                    Log.e("GMAIL",acct.getDisplayName()+",, "+acct.getEmail()+" ... "+acct.getIdToken());
                    mPresenter.loginUserGoogle(acct.getIdToken());
                }
            }else{
                facebookAuth.onActivityResult(requestCode, resultCode, data);

            }

        }


    }


    @OnClick({R.id.btn_login, R.id.login_button, R.id.et_register, R.id.tv_forgot_pass, R.id.google_button, R.id.tv_invited})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                validator.validate();
                break;
            case R.id.login_button:
                facebookAuth = new FacebookAuth(loginButton, this) {
                    @Override
                    public void onRegistrationComplete(LoginResult loginResult) {
                        String access_token_facebook = loginResult.getAccessToken().getToken();
                        if (access_token_facebook != null || !access_token_facebook.equals("")) {
                            mPresenter.loginUserFacebook(access_token_facebook);
                            AccessToken.setCurrentAccessToken(loginResult.getAccessToken());


                        } else {
                            showErrorMessage("Algo sucedió mal al intentar loguearse");
                        }
                    }
                };
                // LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"));
                break;

            case R.id.google_button:

                signIn();

              /* googleAuth = new GoogleAuth(googleButton , getActivity()) {
                    @Override
                    public void onRegistrationComplete(GoogleSignInResult result) {

                        GoogleSignInAccount acct = result.getSignInAccount();
                        mPresenter.loginUserGoogle(acct.getIdToken());
                        int statusCode = result.getStatus().getStatusCode();
                        Log.e("data gmail",result.isSuccess()+" - "+statusCode);
                        if (result.isSuccess()) {
                            GoogleSignInAccount acct = result.getSignInAccount();
                            // Get account information

                            Log.e("GMAIL",acct.getDisplayName()+",, "+acct.getEmail()+" ... "+acct.getIdToken());
                            mPresenter.loginUserGoogle(acct.getIdToken());
                         }
                    }
                };*/
                break;
            case R.id.et_register:
                nextActivity(getActivity(), null, RegisterActivity.class, false);
                break;
            case R.id.tv_forgot_pass:
                showDialogForgotPassword();
                break;

            case R.id.tv_invited:
                mPresenter.loginUser("invitado@gmail.com", "12345");
                break;
        }
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }

    @Override
    public void onValidationSucceeded() {
        mPresenter.loginUser(etEmail.getText().toString(), etPassword.getText().toString());
        isLoading = true;


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
                Toast.makeText(getContext(), "Por favor ingrese lo campos correctamente", Toast.LENGTH_SHORT).show();
            }
        }
    }



    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}