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
    private TextField car_name = new TextField();
    private Button filterByBrand = new Button("filter by brand");
    private Button filterButton = new Button("filter");
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
        priceCol.setCellFactory(tc -> new TableCell<Car, Double>() {
            @Override
            protected void updateItem(Double price, boolean empty) {
                super.updateItem(price, empty);
                if (empty || price == null) {
                    setText(null);
                } else {
                    setText(String.format("%,.0f", price)); // 15000000 -> 15 000 000
                }
            }
        });

        TableColumn<Car, Integer> yearCol = new TableColumn<>("Year");
        yearCol.setCellValueFactory(new PropertyValueFactory<>("year"));

        TableColumn<Car, String> colorCol = new TableColumn<>("Color");
        colorCol.setCellValueFactory(new PropertyValueFactory<>("color"));

        TableColumn<Car, Double> engineVolumeCol = new TableColumn<>("Engine Volume");
        engineVolumeCol.setCellValueFactory(new PropertyValueFactory<>("engineVolume"));

        TableColumn<Car, Integer> mileageCol = new TableColumn<>("Mileage");
        mileageCol.setCellValueFactory(new PropertyValueFactory<>("mileage"));

        TableColumn<Car, String> transmissionCol = new TableColumn<>("Transmission");
        transmissionCol.setCellValueFactory(new PropertyValueFactory<>("transmission"));

        carTable.getColumns().addAll(
                brandCol, modelCol, priceCol,
                yearCol, colorCol, engineVolumeCol,
                mileageCol, transmissionCol
        );
    }


    private void setupUI() {
        minPriceField.setPromptText("min price");
        maxPriceField.setPromptText("Мax price");
        car_name.setPromptText("car name");

        filterButton.setOnAction(e -> filterByPrice());
        filterByBrand.setOnAction(e -> filterByBrand());

        layout.setPadding(new Insets(10));
        layout.getChildren().addAll(minPriceField, maxPriceField, filterButton, carTable);
        layout.getChildren().addAll(car_name, filterByBrand);

    }

    public VBox getView() {
        return layout;
    }

    public void filterByBrand() {

        try{
            String br = car_name.getText();
            if (br.equals("")) {
                carTable.getItems().setAll(carService.getAllCars());
            }
            else{
                List<Car> cars = carService.filterByBrand(br);
                carTable.getItems().setAll(cars);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
