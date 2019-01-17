package com.example.user.myapplication.bus;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.support.annotation.NonNull;

import java.util.List;


public class BusController extends ViewModel{
    private BusRepository busRepository;

    public BusController(Context context) {

        busRepository = BusRepository.getInstance(BusDatabase.getAppDatabase(context).busDAO());
    }

    public void createBus(Bus bus) {
        busRepository.addBus(bus);
    }

    public List<Bus> getBusses(int howMany,int start){
        return busRepository.getBusses(howMany, start);
    }

    public List<Bus> getAllBusses(){
        return busRepository.getAllBusses();
    }

    public void updateBus(Integer number,Boolean added){
        busRepository.updateBus(number,added);
    }


    public static class Factory implements ViewModelProvider.Factory {
        private final Context ctxt;

        public Factory(Context ctxt) {
            this.ctxt = ctxt.getApplicationContext();
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new BusController(ctxt);
        }
    }

    public void deleteBus(int number){ busRepository.deleteBus(number);}
}
