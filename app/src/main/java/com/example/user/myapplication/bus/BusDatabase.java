package com.example.user.myapplication.bus;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Bus.class}, version = 1)
public abstract class BusDatabase extends RoomDatabase {

    public abstract BusDAO busDAO();
    public static BusDatabase INSTANCE;

    public static BusDatabase getAppDatabase(Context context)
    {
        if(INSTANCE == null)
        {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), BusDatabase.class, "bus-database").allowMainThreadQueries().build();

        }

        return INSTANCE;

    }

    public static void destroyInstance()
    {
        INSTANCE = null;
    }

}