package com.cocos.cocosapp.data.remote.request;

import com.cocos.cocosapp.data.entities.CardEntity;
import com.cocos.cocosapp.data.entities.CardRestResponse;
import com.cocos.cocosapp.data.entities.FavouriteResponse;
import com.cocos.cocosapp.data.entities.IsMyFavouriteRestaurante;
import com.cocos.cocosapp.data.entities.RestEntinty;
import com.cocos.cocosapp.data.entities.RestauranteResponse;
import com.cocos.cocosapp.data.entities.StatusColorEntity;
import com.cocos.cocosapp.data.entities.SubCatEntity;
import com.cocos.cocosapp.data.entities.trackholder.TrackEntityHolder;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;


/**
 * Created by katherine on 12/06/17.
 */

public interface ListRequest {
    @GET("subcategory/list")
    Call<TrackEntityHolder<SubCatEntity>> getCategories(@Header("Authorization") String token,
                                                        @Query("page") int numberPage);

    @GET("restaurantBySubcategory/{pk}/list/")
    Call<TrackEntityHolder<RestauranteResponse>> getListRestaurantes(@Header("Authorization") String token,
                                                                     @Path("pk") int id,

                                                                     @Query("lat") String latitude,
                                                                     @Query("long") String longitude,
                                                                     @Query("rad") int rad,
                                                                     @Query("page") int numberPage);

    @GET("restaurant/RUD/{pk}/")
    Call<RestEntinty> getRestaurant(@Header("Authorization") String token, @Path("pk") int id);



    @GET("user/favourite/restaurant/")
    Call<FavouriteResponse> getMyFavouriteRestaurantes(@Header("Authorization") String token);


    @GET("card/list/")
    Call<TrackEntityHolder<CardEntity>> getCards(@Header("Authorization") String token,
                                                      @Query("page") int numberPage);


    @GET("restaurantGPS/list/")
    Call<TrackEntityHolder<RestauranteResponse>> getRestaurantesByGPS(@Header("Authorization") String token,
                                                                      @Query("lat") String latitude,
                                                                      @Query("long") String longitude,
                                                                      @Query("rad") int rad,
                                                                      @Query("page") int numberPage);
    @GET("card/{pk}/discount/restaurant")
    Call<TrackEntityHolder<CardRestResponse>> getCardsRestaurantes(@Header("Authorization") String token,
                                                                   @Path("pk") int id,
                                                                   @Query("page") int numberPage);


    @GET("restaurant/list/")
    Call<TrackEntityHolder<RestauranteResponse>> getRestaurantes(@Header("Authorization") String token,
                                                         @Query("page") int numberPage,
                                                         @Query("search") String search);


    @GET("user/favourite/restaurant/")
    Call<TrackEntityHolder<RestauranteResponse>> getSearchFavoritesRestaurantes(@Header("Authorization") String token,
                                                                                @Query("search") String search);

    @GET("favrestaurants/me/")
    Call<IsMyFavouriteRestaurante> getFavoriteRestaurants(@Header("Authorization") String token);


    @GET("favrestaurant/{pk}/statuscolor")
    Call<StatusColorEntity> getMyFavoriteStatus(@Header("Authorization") String token,
                                                @Path("pk") int idRestaurante);
}
