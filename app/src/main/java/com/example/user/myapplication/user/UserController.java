package com.example.user.myapplication.user;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.support.annotation.NonNull;

public class UserController extends ViewModel {


    private UserRepository userRepository;

    public UserController(Context context) {
        userRepository = UserRepository.getInstance(UserDatabase.getAppDatabase(context).userAccountDao());
    }

    public void createUser(String username, String password) {
        userRepository.addUser(username, password);
    }

    public String checkValidLogin(String username, String password) {
        return userRepository.isValidAccount(username, password);
    }

    public static class Factory implements ViewModelProvider.Factory {
        private final Context ctxt;

        public Factory(Context ctxt) {
            this.ctxt = ctxt.getApplicationContext();
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new UserController(ctxt);
        }
    }
}


