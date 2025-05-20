package org.example.kolikter.services;

import org.example.kolikter.DAO.CarDAO;
import org.example.kolikter.DAO.IDAO;
import org.example.kolikter.model.Car;
import java.sql.SQLException;
import java.util.List;

public class CarService implements IDAO {

    private final CarDAO carDAO = new CarDAO();

    @Override
    public List<Car> getAllCars() throws SQLException {
        return carDAO.getAllCars();
    }

    @Override
    public List<Car> getCarsByPriceRange(double minPrice, double maxPrice) throws SQLException {
        return carDAO.getCarsByPriceRange(minPrice, maxPrice);
    }

    @Override
    public void buyCar(int vin) throws SQLException {
        carDAO.buyCar(vin);
    }

    @Override
    public void addCar(Car car) throws SQLException {
        carDAO.addCar(car);
    }

    @Override
    public Car getCarByVin(int id) {
        return carDAO.getCarByVin(id);
    }

    @Override
    public void deleteCar(int id) throws SQLException {
        carDAO.deleteCar(id);
    }

    @Override
    public List<Car> filterByBrand(String brand) throws SQLException {
        return  carDAO.filterByBrand(brand);
    }

}
