package com.example.td;

import java.util.ArrayList;

import retrofit2.http.*;
import retrofit2.Call;

public interface ItemsApiService {

    @GET("items")
    Call<ArrayList<ItemModel>> getItems();

    @POST("items")
    Call<Void> createItem(@Body ItemModel item);

    @GET("items/{id}")
    Call<ItemModel> getItem(@Path("id") String id);

    @PUT("items")
    Call<ItemModel> updateItem(@Body ItemModel item);

    @DELETE("items/{id}")
    Call<Void> deleteItem(@Path("id") String id);
}
