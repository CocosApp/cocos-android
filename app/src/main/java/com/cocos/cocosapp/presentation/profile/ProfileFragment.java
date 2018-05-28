package com.cocos.cocosapp.presentation.profile;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cocos.cocosapp.R;
import com.cocos.cocosapp.core.BaseActivity;
import com.cocos.cocosapp.core.BaseFragment;
import com.cocos.cocosapp.data.entities.UploadResponse;
import com.cocos.cocosapp.data.entities.UserEntity;
import com.cocos.cocosapp.data.local.SessionManager;
import com.cocos.cocosapp.presentation.load.LoadActivity;
import com.cocos.cocosapp.utils.ImagePicker;
import com.cocos.cocosapp.utils.ProgressDialogCustom;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by katherine on 19/05/17.
 */

public class ProfileFragment extends BaseFragment implements ProfileContract.View, EasyPermissions.PermissionCallbacks {

    private static final int PERMISSION_CAMERA_AND_WRITE_EXTERNAL_STORAGE = 180;
    private static final int PICK_IMAGE_ID = 234;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.iv_settings)
    ImageView ivSettings;
    @BindView(R.id.ly_action_edit)
    RelativeLayout lyActionEdit;
    @BindView(R.id.ly_image_profile)
    RelativeLayout lyImageProfile;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.im_name)
    ImageView imName;
    @BindView(R.id.tv_name_detail)
    EditText tvNameDetail;
    @BindView(R.id.ly_personal)
    LinearLayout lyPersonal;
    @BindView(R.id.im_email)
    ImageView imEmail;
    @BindView(R.id.tv_email_detail)
    EditText tvEmailDetail;
    @BindView(R.id.ly_email)
    LinearLayout lyEmail;
    @BindView(R.id.im_cel)
    ImageView imCel;
    @BindView(R.id.btn_save)
    Button btnSave;
    Unbinder unbinder;
    @BindView(R.id.photo_profile)
    CircleImageView photoProfile;
    @BindView(R.id.iv_edit)
    ImageView ivEdit;
    @BindView(R.id.tv_apellido_detail)
    EditText tvApellidoDetail;
    @BindView(R.id.send_image)
    ImageView sendImage;
    @BindView(R.id.container_photo)
    RelativeLayout containerPhoto;
    @BindView(R.id.ly_apellido)
    LinearLayout lyApellido;
    @BindView(R.id.btn_close_sesion)
    Button btnCloseSesion;


    private SessionManager mSessionManager;
    private ProfileContract.Presenter mPresenter;
    private ProgressDialogCustom mProgressDialogCustom;
    private Bitmap mBitmap;
    private UserEntity userEntity;


    public ProfileFragment() {
        // Requires empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSessionManager = new SessionManager(getContext());
        mPresenter = new ProfilePresenter(this, getContext());

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        unbinder = ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mProgressDialogCustom = new ProgressDialogCustom(getContext(), "Actualizando información...");

        tvName.setText(mSessionManager.getUserEntity().getFullName());
        tvNameDetail.setText(mSessionManager.getUserEntity().getFirst_name());
        tvApellidoDetail.setText(mSessionManager.getUserEntity().getLast_name());
        tvEmailDetail.setText(mSessionManager.getUserEntity().getEmail());


        if (mSessionManager.getUserEntity().getPicture() == null) {
            Glide.with(this)
                    .load(getResources().getDrawable(R.drawable.icon_user))
                    .into(photoProfile);
        } else {

            Glide.with(this)
                    .load(mSessionManager.getUserEntity().getPicture())
                    .into(photoProfile);
        }

        if (mSessionManager.getUserEntity().getFirst_name().equals("INVITADO")) {
            ivEdit.setFocusable(false);
            ivEdit.setClickable(false);
            lyActionEdit.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @AfterPermissionGranted(PERMISSION_CAMERA_AND_WRITE_EXTERNAL_STORAGE)
    private void methodRequiresTwoPermission() {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this.getContext(), perms)) {
            Intent chooseImageIntent = ImagePicker.getPickImageIntent(getActivity());
            startActivityForResult(chooseImageIntent, PICK_IMAGE_ID);
        } else {
            EasyPermissions.requestPermissions(this, getResources().getString(R.string.perm_camera),
                    PERMISSION_CAMERA_AND_WRITE_EXTERNAL_STORAGE, perms);
        }

    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        methodRequiresTwoPermission();
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case PICK_IMAGE_ID:
                    mBitmap = ImagePicker.getImageFromResult(getActivity(), resultCode, data);
                    ImagePicker.saveToChangesToExternalCacheDirProfile(mBitmap, getActivity(), 100);

                    File image = null;
                    if (mBitmap != null) {
                        image = ImagePicker.getTempFile(getActivity());
                    }
                    mPresenter.updatePhoto(image);
                    break;
            }
        }
    }

    @Override
    public void updateUser(UserEntity userEntity) {
        // mPresenter.editUser(userEntity, mSessionManager.getUserToken());
    }

    @Override
    public void editSuccessful(UserEntity userEntity) {

        UserEntity newUserEntity = mSessionManager.getUserEntity();
        newUserEntity.setFirst_name(userEntity.getFirst_name());
        newUserEntity.setLast_name(userEntity.getLast_name());
        newUserEntity.setEmail(userEntity.getEmail());
        mSessionManager.setUser(newUserEntity);
        tvName.setText(newUserEntity.getFullName());
        tvNameDetail.setText(newUserEntity.getFirst_name());
        tvEmailDetail.setText(newUserEntity.getEmail());
        tvApellidoDetail.setText(newUserEntity.getLast_name());
        //EventBus.getDefault().post(newUserEntity);
        showMessage("Tus datos han sido actualizados con éxito");
        tvNameDetail.setEnabled(false);
        tvApellidoDetail.setEnabled(false);
        tvEmailDetail.setEnabled(false);
        btnSave.setVisibility(View.GONE);
        btnCloseSesion.setVisibility(View.VISIBLE);
    }

    @Override
    public void ShowSessionInformation(UserEntity userEntity) {
        if (userEntity != null) {
            this.userEntity = userEntity;
           /* Glide.with(getContext())
                    .load(userEntity.getPicture())
                    .transform(new CircleTransform(getContext()))
                    .into(photoProfile);
            tvName.setText(userEntity.getFullName());
            tvNameDetail.setText(userEntity.getFullName());
            tvEmailDetail.setText(userEntity.getEmail());
            tvCelDetail.setText(userEntity.getCellphone());*/
            /*
            if (AccessToken.getCurrentAccessToken() != null) {

                // if (isOnline()) {

                GraphRequest request = GraphRequest.newMeRequest(
                        AccessToken.getCurrentAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {
                                //  response.getJSONObject();
                                try {
                                    if (isAdded()) {
                                        JSONObject json_cover = object.getJSONObject("cover");
                                        String source = (String) json_cover.get("source");
                                        Glide.with(getContext()).load(source).into(frontCover);
                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "cover");
                request.setParameters(parameters);
                request.executeAsync();


            }*/


        } else {
            //photoProfile.setBackgroundResource(R.drawable.photo_guide);
            //frontCover.setBackgroundResource(R.color.colorPrimary);
        }
    }

    @Override
    public void updateProfileImage(UploadResponse body) {

        //photoProfile.setImageBitmap(BitmapCircleUtil.getCircularBitmap(mBitmap));
        //userEntity.setPicture(body.getPhoto());
        UserEntity userEntityNew = new UserEntity(mSessionManager.getUserEntity().getEmail(),
                mSessionManager.getUserEntity().getLast_name(), mSessionManager.getUserEntity().getFirst_name(),
                mSessionManager.getUserEntity().getPassword());
        userEntityNew.setPicture(body.getPhoto());
        userEntityNew.setId(mSessionManager.getUserEntity().getId());
        mSessionManager.setUser(userEntityNew);
        Intent intent = new Intent();
        getActivity().setResult(Activity.RESULT_OK, intent);
        Glide.with(this)
                .load(body.getPhoto())
                .into(photoProfile);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void setPresenter(ProfileContract.Presenter mPresenter) {
        this.mPresenter = mPresenter;
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        if (getView() == null) {
            return;
        }

        if (mProgressDialogCustom != null) {

            if (active) {
                mProgressDialogCustom.show();
            } else {
                if (mProgressDialogCustom.isShowing()) {
                    mProgressDialogCustom.dismiss();
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


    @OnClick({R.id.iv_settings, R.id.btn_save
            ,R.id.btn_close_sesion,R.id.iv_edit, R.id.send_image})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_settings:
                break;
            case R.id.btn_save:
                UserEntity userEntity = new UserEntity(tvEmailDetail.getText().toString(), tvApellidoDetail.getText().toString(),
                        tvNameDetail.getText().toString(), mSessionManager.getUserEntity().getPassword());

                mPresenter.editUser(userEntity, mSessionManager.getUserToken());

                break;

            case R.id.iv_edit:
                tvNameDetail.setEnabled(true);
                tvApellidoDetail.setEnabled(true);
                btnSave.setEnabled(true);
                btnSave.setVisibility(View.VISIBLE);
                btnCloseSesion.setVisibility(View.GONE);
                btnSave.setBackground(getResources().getDrawable(R.drawable.button_square_border_blue));

                break;

            case R.id.send_image:
                methodRequiresTwoPermission();
                break;
            case R.id.btn_close_sesion:
                CloseSession();
                break;
        }
    }

    private void CloseSession() {
        mSessionManager.closeSession();
        newActivityClearPreview(getActivity(), null, LoadActivity.class);
    }

}
