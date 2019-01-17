package com.example.user.myapplication.user;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;

import static com.example.user.myapplication.user.User.TABLE_NAME;

@Entity(tableName = TABLE_NAME)
public class User implements Serializable {
    public static final String TABLE_NAME = "users2";

    @PrimaryKey
    @NonNull
    String username;
    String password;
    String type;


    @NonNull
    public String getUsername() {
        return username;
    }

    public void setUsername(@NonNull String username) {
        this.username = username;
    }


    public User(String username, String password, String type)
    {
        this.username = username;
        this.password = password;
        this.type=type;

    }

    public String getUserId() {
        return username;
    }

    public void setUserId(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public String getType(){
        return type;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
