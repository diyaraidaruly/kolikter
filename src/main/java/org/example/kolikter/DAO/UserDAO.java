package org.example.kolikter.DAO;

import org.example.kolikter.model.User;

import java.sql.*;

public class UserDAO {
    public boolean checkLogin(String login, String password) {
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
}
