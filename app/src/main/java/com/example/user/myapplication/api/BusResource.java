package com.example.user.myapplication.api;

import com.example.user.myapplication.bus.Bus;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface BusResource {
    String BASE_URL = AppResource.BASE_URL;

    @POST("busses")
    Call<Bus> addBus(@Body Bus bus, @Header("Authorization") String token);

    @GET("busses")
    Call<List<Bus>> getBusses();

    @GET("busses/bussesPaginated")
    Call<List<Bus>> getBussesPaginated(@Query("start") Integer start, @Query("howMany") Integer howMany, @Header("Authorization") String token);
}
