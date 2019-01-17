package com.example.user.myapplication.user;

import android.arch.lifecycle.LiveData;

public class UserRepository {
    private final UserDAO userDAO;
    private static UserRepository instance;
    private LiveData<User> userAccountLiveData;

    private UserRepository(UserDAO userDAO)
    {
        this.userDAO = userDAO;
    }

    public static UserRepository getInstance(UserDAO userAccountDao)
    {
        if(instance == null)
        {
            instance = new UserRepository(userAccountDao);
        }
        return instance;
    }

    public String isValidAccount(String username, String password)
    {
        User user = userDAO.getAccount(username);
        if(user.getPassword().equals(password))
            return null;
        return user.getType();
    }

    public void addUser(String username, String password)
    {
        User account = new User(username, password,"aa");
        userDAO.insert(account);
    }
}
