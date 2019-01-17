package com.example.user.myapplication.modelviews;

import com.example.user.myapplication.api.BusResource;
import com.example.user.myapplication.bus.Bus;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BusModelView {
    public Call<List<Bus>> getBusses()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BusResource.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        BusResource api = retrofit.create(BusResource.class);
        Call<List<Bus>> call = api.getBusses();
        return call;
    }

    public Call<List<Bus>> getBussesPaginated(Integer start,Integer howMany, String token){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BusResource.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        BusResource api = retrofit.create(BusResource.class);
        Call<List<Bus>> call = api.getBussesPaginated(start,howMany,token);
        return call;
    }

    public Call<Bus> addBus(Bus bus, String token){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BusResource.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        BusResource api = retrofit.create(BusResource.class);
        Call<Bus> call = api.addBus(bus,token);
        return call;
    }
}
