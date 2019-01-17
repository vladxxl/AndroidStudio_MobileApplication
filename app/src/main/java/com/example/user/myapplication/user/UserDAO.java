package com.example.user.myapplication.user;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

@Dao
public interface UserDAO {
    @Insert
    void insert(User account);

    @Query("SELECT * FROM users2 WHERE users2.username LIKE :username")
    User getAccount(String username);
}
