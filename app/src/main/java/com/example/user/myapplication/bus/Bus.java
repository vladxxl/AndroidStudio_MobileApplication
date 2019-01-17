package com.example.user.myapplication.bus;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import static com.example.user.myapplication.bus.Bus.TABLE_NAME;

@Entity(tableName = TABLE_NAME)
public class Bus {
    public static final String TABLE_NAME = "busses";

    @PrimaryKey
    @NonNull
    Integer number;
    String route;
    Boolean added;

    public Bus(Integer number, String route, Boolean added)
    {
        this.number = number;
        this.route = route;
        this.added=added;
    }

    public Integer getBusNumber() {
        return number;
    }

    public void setBusNumber(Integer number) {
        this.number = number;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public Boolean getAdded(){
        return added;
    }

    @Override
    public String toString(){
        return this.number.toString()+" "+this.route+" "+this.added.toString();
    }
}
