package com.example.user.myapplication.api;

import com.example.user.myapplication.user.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserResource {
    String IP = "172.30.119.120:8080";
    String BASE_URL = "http://" + IP ;

    @POST("/api/v1/users/addUser")
    Call<User> addUser(@Body User user);

    @POST("/login")
    Call<Void> loginUser(@Body User user);
}
