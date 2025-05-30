package org.example.kolikter.DAO;

import org.example.kolikter.controller.filter.CarFilter;
import org.example.kolikter.model.Car;
import org.example.kolikter.model.User;

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
        // users can see only available cars
        String sql = "SELECT * FROM car WHERE Status = 'AVAILABLE'";
        //admin can see all cars
        if(User.isAdmin){
            sql = "SELECT * FROM car";
        }
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


    public List<Car> filterCars(CarFilter filter) {
        List<Car> cars = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM car WHERE 1=1");
        List<Object> parameters = new ArrayList<>();

        if (filter.brand != null && !filter.brand.isEmpty()) {
            sql.append(" AND brand = ?");
            parameters.add(filter.brand);
        }
        if (filter.model != null && !filter.model.isEmpty()) {
            sql.append(" AND model = ?");
            parameters.add(filter.model);
        }
        if (filter.year != null) {
            sql.append(" AND year = ?");
            parameters.add(filter.year);
        }
        if (filter.color != null && !filter.color.isEmpty()) {
            sql.append(" AND color = ?");
            parameters.add(filter.color);
        }
        if (filter.engineVolume != null) {
            sql.append(" AND engineVolume = ?");
            parameters.add(filter.engineVolume);
        }
        if (filter.mileage != null) {
            sql.append(" AND mileage <= ?");
            parameters.add(filter.mileage);
        }
        if (filter.transmission != null && !filter.transmission.isEmpty()) {
            sql.append(" AND transmission = ?");
            parameters.add(filter.transmission);
        }
        if (filter.driveType != null && !filter.driveType.isEmpty()) {
            sql.append(" AND drive_type = ?");
            parameters.add(filter.driveType);
        }
        if (filter.bodyType != null && !filter.bodyType.isEmpty()) {
            sql.append(" AND body_type = ?");
            parameters.add(filter.bodyType);
        }
        if (filter.fuelType != null && !filter.fuelType.isEmpty()) {
            sql.append(" AND fuel_type = ?");
            parameters.add(filter.fuelType);
        }
        if (filter.minPrice != null) {
            sql.append(" AND price >= ?");
            parameters.add(filter.minPrice);
        }
        if (filter.maxPrice != null) {
            sql.append(" AND price <= ?");
            parameters.add(filter.maxPrice);
        }

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < parameters.size(); i++) {
                stmt.setObject(i + 1, parameters.get(i));
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                cars.add(extractCarFromResultSet(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cars;
    }


    @Override
    public void buyCar(int vin) throws SQLException {
        String sql = "UPDATE car SET status = 'SOLD' WHERE vin = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, vin);
            stmt.executeUpdate();
        }
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
