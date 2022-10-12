package com.example.crudretrofitapp.interfaces;

import com.example.crudretrofitapp.dto.ProductDto;
import com.example.crudretrofitapp.model.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CRUDInterface {

    @GET("product")
    Call<List<Product>> getAll();

    @GET("product/{id}")
    Call<Product> getOne(@Path("id") int id);

    @POST("product")
    Call<Product> create(@Body ProductDto productDto);

    @PUT("product/{id}")
    Call<Product> edit(@Path("id") int id, @Body ProductDto dto);

    @DELETE("product/{id}")
    Call<Product> delete(@Path("id") int id);
}
