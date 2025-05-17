package org.example.kolikter.controller;

import javafx.scene.layout.VBox;
import javafx.scene.control.*;
import javafx.geometry.Insets;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.kolikter.model.Car;
import org.example.kolikter.services.CarService;

import java.sql.SQLException;
import java.util.List;

public class CarController {

    private CarService carService = new CarService();

    private TableView<Car> carTable = new TableView<>();
    private TextField minPriceField = new TextField();
    private TextField maxPriceField = new TextField();
    private Button filterButton = new Button("Сүзу");
    private VBox layout = new VBox(10);

    public CarController() {
        setupTable();
        setupUI();
    }

    private void setupTable() {
        TableColumn<Car, String> brandCol = new TableColumn<>("Brand");
        brandCol.setCellValueFactory(new PropertyValueFactory<>("brand"));

        TableColumn<Car, String> modelCol = new TableColumn<>("Model");
        modelCol.setCellValueFactory(new PropertyValueFactory<>("model"));

        TableColumn<Car, Double> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        carTable.getColumns().addAll(brandCol, modelCol, priceCol);
    }

    private void setupUI() {
        minPriceField.setPromptText("Мин баға");
        maxPriceField.setPromptText("Макс баға");

        filterButton.setOnAction(e -> filterByPrice());

        layout.setPadding(new Insets(10));
        layout.getChildren().addAll(minPriceField, maxPriceField, filterButton, carTable);
    }

    public VBox getView() {
        return layout;
    }

    public void loadAllCars() {
        try {
            List<Car> cars = carService.getAllCars();
            carTable.getItems().setAll(cars);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void filterByPrice() {
        try {
            double min = Double.parseDouble(minPriceField.getText());
            double max = Double.parseDouble(maxPriceField.getText());
            List<Car> filteredCars = carService.getCarsByPriceRange(min, max);
            carTable.getItems().setAll(filteredCars);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
