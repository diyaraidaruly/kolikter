package org.example.kolikter.services;

import org.example.kolikter.DAO.UserDAO;

public class UserService extends UserDAO {

    private final UserDAO userDAO = new UserDAO();
    public boolean checkUserLogin(String login, String password){
        return userDAO.checkUserLogin(login, password);
    }

    @Override
    public boolean checkAdminLogin(String password) {
        return  userDAO.checkAdminLogin(password);
    }
}
