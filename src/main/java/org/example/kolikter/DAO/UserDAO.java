package org.example.kolikter.DAO;

import org.example.kolikter.model.Car;
import org.example.kolikter.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    public boolean checkUserLogin(String login, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ? AND role = 'CUSTOMER'";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, login);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean checkAdminLogin(String password) {
        String query = "SELECT * FROM users WHERE username = 'admin' AND password = ? AND role = 'ADMIN'";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
    }

    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                users.add(extractUserFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    private User extractUserFromResultSet(ResultSet rs) throws SQLException {
        User user = new User();
        user.setFull_name(rs.getString("full_name"));
        user.setAge(rs.getInt("age"));
        user.setCity(rs.getString("city"));
        user.setPhone_num(rs.getString("phone_num"));
        user.setLogin(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        return user;
    }

    public void deleteUser(String user) {
        String sql = "DELETE FROM users WHERE login = ?";
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
