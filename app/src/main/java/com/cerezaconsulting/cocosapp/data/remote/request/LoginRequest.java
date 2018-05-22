package com.cerezaconsulting.cocosapp.data.remote.request;


import com.cerezaconsulting.cocosapp.data.entities.AccessTokenEntity;
import com.cerezaconsulting.cocosapp.data.entities.UploadResponse;
import com.cerezaconsulting.cocosapp.data.entities.UserEntity;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by katherine on 10/05/17.
 */

public interface LoginRequest {
    @FormUrlEncoded
    @POST("login/")
    Call<AccessTokenEntity> login(@Field("email") String email, @Field("password") String password);

    @GET("user/retrieve/")
    Call<UserEntity> getUser(@Header("Authorization") String token);


    @FormUrlEncoded
    @POST("login/mobile/facebook/")
    Call<AccessTokenEntity> loginUserFacebook(@Field("access_token") String tokenFace);


    @FormUrlEncoded
    @POST("login/mobile/google-oauth2/")
    Call<AccessTokenEntity> loginGmail(@Field("access_token") String access_token);

    @FormUrlEncoded
    @POST("recovery/")
    Call<UserEntity> recovery(@Field("email") String email);

    @PUT("user/{pk}/photo/")
    Call<UploadResponse> updatePhoto(@Header("Authorization") String token,
                                     @Path("pk") int id,
                                     @Body RequestBody body);


}
