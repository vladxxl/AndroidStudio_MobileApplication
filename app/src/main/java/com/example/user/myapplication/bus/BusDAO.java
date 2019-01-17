package com.example.user.myapplication.bus;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface BusDAO {
    @Insert
    void insert(Bus bus);

    @Query("SELECT * FROM busses LIMIT:howMany OFFSET:start")
    List<Bus> getBusses(int howMany,int start);

    @Query("SELECT * FROM busses")
    List<Bus> getAllBusses();

    @Query("UPDATE busses SET added=:added WHERE busses.number=:number")
    void updateBus(Integer number,Boolean added);

    @Query("DELETE FROM busses WHERE busses.number=:number")
    void deleteBus(Integer number);
}
