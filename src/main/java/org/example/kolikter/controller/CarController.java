package org.example.kolikter.controller;

import javafx.scene.layout.VBox;
import javafx.scene.control.*;
import javafx.geometry.Insets;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.kolikter.model.*;
import org.example.kolikter.services.CarService;

import javax.swing.*;
import java.sql.SQLException;
import java.util.List;

public class CarController {

    private CarService carService = new CarService();

    private Button filterByPriceButton = new Button("filter by Price");

    private TableView<Car> carTable = new TableView<>();
    private TextField minPriceField = new TextField();
    private TextField maxPriceField = new TextField();
    private TextField car_name = new TextField();
    private TextField carNameToAdd = new TextField();
    private Button filterByBrand = new Button("filter by brand");
    private Button filterButton = new Button("filter");
    private Button addCar = new Button("add car");
    private Button add = new Button("add");
    private Button prev = new Button("previus");
    private VBox layout = new VBox(10);


    public CarController() {
        setupUI();
    }

    public void showTableAndFilter() {
        carTable.setVisible(true);
        filterButton.setVisible(true);
        addCar.setVisible(true);

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
                    setText(String.format("%,.0f", price));
                }
            }
        });

//        Table colums
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

        TableColumn<Car, String> driveTypeCol = new TableColumn<>("Drive Type");
        driveTypeCol.setCellValueFactory(new PropertyValueFactory<>("driveType"));

        TableColumn<Car, String> bodyTypeCol = new TableColumn<>("Body Type");
        bodyTypeCol.setCellValueFactory(new PropertyValueFactory<>("bodyType"));

        TableColumn<Car, String> fuelTypeCol = new TableColumn<>("Fuel Type");
        fuelTypeCol.setCellValueFactory(new PropertyValueFactory<>("fuelType"));

        TableColumn<Car, Status> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

//        getting values from db
        carTable.getColumns().addAll(
                brandCol, modelCol, priceCol,
                yearCol, colorCol, engineVolumeCol,
                mileageCol, transmissionCol,driveTypeCol,
                bodyTypeCol, fuelTypeCol,statusCol
        );
    }


    private void setupUI() {
        setupTable();
        layout.setPadding(new Insets(10));
        layout.setSpacing(10);

        carTable.setVisible(false);
        filterButton.setVisible(false);
        filterByBrand.setVisible(false);
        filterByPriceButton.setVisible(false);
        addCar.setVisible(false);
        add.setVisible(false);
        prev.setVisible(false);

        minPriceField.setPromptText("Min price");
        maxPriceField.setPromptText("Max price");
        car_name.setPromptText("Car name");
        carNameToAdd.setPromptText("");

        filterButton.setOnAction(e -> {
            filterByBrand.setVisible(true);
            filterByPriceButton.setVisible(true);

            if (!layout.getChildren().contains(car_name)) {
                layout.getChildren().addAll(car_name, filterByBrand, minPriceField, maxPriceField, filterByPriceButton);
            }
        });

        addCar.setOnAction(e -> {
            add.setVisible(true);
            prev.setVisible(true);
            addCarForm();
            carTable.setVisible(false);
        });
        //
        add.setOnAction(e -> carTable.setVisible(true));

        filterByBrand.setOnAction(e -> filterByBrand());
        filterByPriceButton.setOnAction(e -> filterByPrice());
        layout.getChildren().addAll(filterButton,addCar,carTable );

    }


    public VBox getView() {
        return layout;
    }

//  filtering by brand name
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

    private void addCarForm() {
        VBox form = new VBox(10);
        form.setPadding(new Insets(10));

//      fields for writing new car`s description
        TextField vinField = new TextField();
        vinField.setPromptText("VIN");

        TextField brandField = new TextField();
        brandField.setPromptText("Brand");

        TextField modelField = new TextField();
        modelField.setPromptText("Model");

        TextField yearField = new TextField();
        yearField.setPromptText("Year");

        TextField colorField = new TextField();
        colorField.setPromptText("Color");

        TextField engineVolumeField = new TextField();
        engineVolumeField.setPromptText("Engine Volume");

        TextField mileageField = new TextField();
        mileageField.setPromptText("Mileage");

        ComboBox<TransmissionType> transmissionBox = new ComboBox<>();
        transmissionBox.getItems().addAll(TransmissionType.values());
        transmissionBox.setPromptText("Transmission");

        ComboBox<DriveType> driveTypeBox = new ComboBox<>();
        driveTypeBox.getItems().addAll(DriveType.values());
        driveTypeBox.setPromptText("Drive Type");

        ComboBox<BodyType> bodyTypeBox = new ComboBox<>();
        bodyTypeBox.getItems().addAll(BodyType.values());
        bodyTypeBox.setPromptText("Body Type");

        TextField priceField = new TextField();
        priceField.setPromptText("Price");

        ComboBox<FuelType> fuelTypeBox = new ComboBox<>();
        fuelTypeBox.getItems().addAll(FuelType.values());
        fuelTypeBox.setPromptText("Fuel Type");

        Button addButton = new Button("Add Car");

        prev.setOnAction(e -> {
            layout.getChildren().removeAll(
                    vinField, brandField, modelField, yearField, colorField,
                    engineVolumeField, mileageField, transmissionBox,
                    driveTypeBox, bodyTypeBox, priceField, fuelTypeBox,
                    addButton, prev
            );
            carTable.setVisible(true); // Кестені қайтадан көрсету
        });



        addButton.setOnAction(e -> {
            try {
                Car car = new Car(
                        Integer.parseInt(vinField.getText()),
                        brandField.getText(),
                        modelField.getText(),
                        Integer.parseInt(yearField.getText()),
                        colorField.getText(),
                        Double.parseDouble(engineVolumeField.getText()),
                        Integer.parseInt(mileageField.getText()),
                        transmissionBox.getValue().toString(),
                        driveTypeBox.getValue().toString(),
                        bodyTypeBox.getValue().toString(),
                        Double.parseDouble(priceField.getText()),
                        fuelTypeBox.getValue().toString());
                ;

                carService.addCar(car);
                layout.getChildren().clear();
                loadAllCars();
                JOptionPane.showMessageDialog(null, "Car added successfully!");

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        });

//      addCar басқаннан кейінгі шығатын полялар мен батырма
        form.getChildren().addAll(
                prev, vinField, brandField, modelField, yearField,
                colorField, engineVolumeField, mileageField,
                transmissionBox, driveTypeBox, bodyTypeBox,
                priceField, fuelTypeBox, addButton
        );

        layout.getChildren().add(form);
    }

//  show all cars in the table
    public void loadAllCars() {
        try {
            List<Car> cars = carService.getAllCars();
            carTable.getItems().setAll(cars);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    filtering by price
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
