package org.example.kolikter.services;

import org.example.kolikter.DAO.UserDAO;
import org.example.kolikter.model.User;

import java.util.List;

public class UserService extends UserDAO {

    private final UserDAO userDAO = new UserDAO();
    public boolean checkUserLogin(String login, String password){
        return userDAO.checkUserLogin(login, password);
    }

    @Override
    public boolean checkAdminLogin(String password) {
        return  userDAO.checkAdminLogin(password);
    }

    @Override
    public List<User> getUsers() {
        return userDAO.getUsers();
    }

    public void deleteUser(String user) {
        userDAO.deleteUser(user);
    }

    public boolean registerUser(User user) {
        return userDAO.registerUser(user);
    }
}
