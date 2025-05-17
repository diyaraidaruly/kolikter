package org.example.kolikter.DAO;

import org.example.kolikter.model.Car;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarDAO implements IDAO {

    @Override
    public void addCar(Car car) {
        String sql = "INSERT INTO car (vin, brand, model, year, color, engineVolume, mileage, transmission, driveType, bodyType, price, fuelType) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, car.getVin());
            stmt.setString(2, car.getBrand());
            stmt.setString(3, car.getModel());
            stmt.setInt(4, car.getYear());
            stmt.setString(5, car.getColor());
            stmt.setDouble(6, car.getEngineVolume());
            stmt.setInt(7, car.getMileage());
            stmt.setString(8, car.getTransmission());
            stmt.setString(9, car.getDriveType());
            stmt.setString(10, car.getBodyType());
            stmt.setDouble(11, car.getPrice());
            stmt.setString(12, car.getFuelType());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Car getCarByVin(int vin) {
        String sql = "SELECT * FROM car WHERE vin = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, vin);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return extractCarFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Car> getAllCars() {
        List<Car> cars = new ArrayList<>();
        String sql = "SELECT * FROM car WHERE Status = 'Available'";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                cars.add(extractCarFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }



    @Override
    public void deleteCar(int id) {
        String sql = "DELETE FROM car WHERE vin = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Car> getCarsByPriceRange(double minPrice, double maxPrice)  throws SQLException {
        String sql = "SELECT * FROM car WHERE price BETWEEN ? AND ?";
        List<Car> cars = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, minPrice);
            stmt.setDouble(2, maxPrice);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    cars.add(extractCarFromResultSet(rs));
                }
            }
        }
        return cars;
    }


    private Car extractCarFromResultSet(ResultSet rs) throws SQLException {
        Car car = new Car();
        car.setVin(rs.getInt("vin"));
        car.setBrand(rs.getString("brand"));
        car.setModel(rs.getString("model"));
        car.setYear(rs.getInt("year"));
        car.setColor(rs.getString("color"));
        car.setEngineVolume(rs.getDouble("engineVolume"));
        car.setMileage(rs.getInt("mileage"));
        car.setTransmission(rs.getString("transmission"));
        car.setDriveType(rs.getString("driveType"));
        car.setBodyType(rs.getString("bodyType"));
        car.setPrice(rs.getDouble("price"));
        car.setFuelType(rs.getString("fuelType"));
        return car;
    }


}
