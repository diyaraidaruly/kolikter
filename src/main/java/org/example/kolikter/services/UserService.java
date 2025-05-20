package org.example.kolikter.services;

import org.example.kolikter.DAO.UserDAO;

public class UserService extends UserDAO {

    private final UserDAO userDAO = new UserDAO();
    public boolean checkLogin(String login, String password){
        return userDAO.checkLogin(login, password);
    }
}
