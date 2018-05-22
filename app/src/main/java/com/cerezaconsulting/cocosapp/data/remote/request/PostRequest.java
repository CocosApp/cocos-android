package com.cerezaconsulting.cocosapp.data.remote.request;

import com.cerezaconsulting.cocosapp.data.entities.MessageResponse;
import com.cerezaconsulting.cocosapp.data.entities.RestauranteResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by kath on 25/01/18.
 */

public interface PostRequest {
    @FormUrlEncoded
    @POST("favrestaurants/me/")
    Call<RestauranteResponse> sendMyFavorite(@Header("Authorization") String token,
                                             @Field("restaurant") int id);

    @FormUrlEncoded
    @POST("me/devices/fcm/")
    Call<Void> sendMyFCM(@Header("Authorization") String token,
                                        @Field("device_id") String device,
                                        @Field("registration_id") String tokenFCM,
                                        @Field("name") String name,
                                        @Field("type") String type);
    @FormUrlEncoded
    @POST("user/favrestaurant/delete/me")
    Call<Void> sendNoFavorite(@Header("Authorization") String token,
                              @Field("restaurant_id") int id);
}
