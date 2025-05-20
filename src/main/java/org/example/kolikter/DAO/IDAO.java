package org.example.kolikter.DAO;

import org.example.kolikter.model.Car;

import java.sql.SQLException;
import java.util.List;

public interface IDAO {
    void addCar(Car car) throws SQLException;
    Car getCarByVin(int id);
    List<Car> getAllCars() throws SQLException;
    void deleteCar(int id) throws SQLException;
    List<Car> filterByBrand(String brand) throws SQLException;
    List<Car> getCarsByPriceRange(double minPrice, double maxPrice)  throws SQLException;
    void buyCar(int vin) throws SQLException;
}
