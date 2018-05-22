package com.cerezaconsulting.cocosapp.data.remote.request;

import com.cerezaconsulting.cocosapp.data.entities.AccessTokenEntity;
import com.cerezaconsulting.cocosapp.data.entities.UserEntity;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

/**
 * Created by katherine on 10/05/17.
 */

public interface RegisterRequest {

    @FormUrlEncoded
    @POST("register/")
    Call<AccessTokenEntity> registerUser(@Field("email") String email,
                                         @Field("first_name") String first_name,
                                         @Field("last_name") String last_name,
                                         @Field("password") String password);


    @FormUrlEncoded
    @PUT("user/update/")
    Call<UserEntity> editUser(@Header("Authorization") String token,
                              @Field("first_name") String first_name,
                              @Field("last_name") String last_name,
                              @Field("email") String email);

}

