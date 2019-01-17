package com.example.user.myapplication.modelviews;

import com.example.user.myapplication.api.UserResource;
import com.example.user.myapplication.user.User;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginModelView {
    private static final String TAG = LoginModelView.class.getCanonicalName();

    public Call<User> addUser(User user)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UserResource.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UserResource api = retrofit.create(UserResource.class);
        Call<User> call = api.addUser(user);
        return call;
    }

    public Call<Void> loginUser(String username, String password){
        User user=new User(username,password,"aa");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UserResource.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UserResource api = retrofit.create(UserResource.class);
        Call<Void> call = api.loginUser(user);
        return call;
    }
}