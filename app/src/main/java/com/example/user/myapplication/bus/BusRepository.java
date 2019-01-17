package com.example.user.myapplication.bus;

import android.arch.lifecycle.LiveData;

import java.util.List;

public class BusRepository {
    private final BusDAO busDAO;
    private static BusRepository instance;
    private LiveData<Bus> busLiveData;

    private BusRepository(BusDAO busDAO)
    {
        this.busDAO = busDAO;
    }

    public static BusRepository getInstance(BusDAO busDAO)
    {
        if(instance == null)
        {
            instance = new BusRepository(busDAO);
        }
        return instance;
    }

    public List<Bus> getAllBusses(){
        return busDAO.getAllBusses();
    }

    public void updateBus(Integer number,Boolean added){
        busDAO.updateBus(number,added);
    }


    public void addBus(Bus bus)
    {
        busDAO.insert(bus);
    }

    public List<Bus> getBusses(int howMany, int start){
        return busDAO.getBusses(howMany,start);
    }

    public void deleteBus(int number){ busDAO.deleteBus(number);}
}
