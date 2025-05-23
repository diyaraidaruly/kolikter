package org.example.kolikter.controller;

import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.*;
import javafx.geometry.Insets;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.kolikter.controller.filter.FilterWindow;
import org.example.kolikter.model.*;
import org.example.kolikter.services.CarService;

import javax.swing.*;
import java.sql.SQLException;
import java.util.List;

public class CarController {
        private Stage mainStage;

        private CarService carService = new CarService();

        private TableView<Car> carTable = new TableView<>();
        private Button filterButton = new Button("Filter");
        private Button addCar = new Button("Add Car");

        private VBox layout = new VBox(10);

        public CarController(Stage stage) {
            this.mainStage = stage;
            setupUI();
        }

        public CarController() {
            setupUI();
        }

        public void showTableAndFilter() {
            carTable.setVisible(true);
            filterButton.setVisible(true);
            if (User.isAdmin){
                addCar.setVisible(true);
            }else {
                addCar.setVisible(false);
            }
        }

    public void showCarListScene() {
        VBox newLayout = new VBox();
        if(User.isAdmin) {
            newLayout.getChildren().addAll(filterButton, addCar, carTable);
        }else{
            newLayout.getChildren().addAll(filterButton, carTable);

        }

        Scene scene = new Scene(newLayout, 800, 600);
        mainStage.setTitle("Cars list");
        mainStage.setScene(scene);
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
            // admin can see descripton from the table
            if(User.isAdmin) {
                carTable.getColumns().addAll(
                        brandCol, modelCol, priceCol,
                        yearCol,colorCol,engineVolumeCol,mileageCol,transmissionCol,driveTypeCol,bodyTypeCol,fuelTypeCol
                );
                //users can see only 5 feauture from table
            }else{
                carTable.getColumns().addAll(
                        brandCol, modelCol, priceCol,
                        yearCol,engineVolumeCol
                );
            }
        }

        private void setupUI() {
            setupTable();
            layout.setPadding(new Insets(10));
            layout.setSpacing(10);

            carTable.setVisible(false);
            filterButton.setVisible(false);
            addCar.setVisible(false);

            filterButton.setOnAction(e -> {

                FilterWindow.showFilterDialog(filter -> {
                    List<Car> results = null;
                    try {
                        results = carService.filterCars(filter);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    carTable.getItems().setAll(results);
                });
            });

            addCar.setOnAction(e -> {
                showAddCarForm();
                carTable.setVisible(false);
                filterButton.setVisible(false);
                addCar.setVisible(false);
            });

            layout.getChildren().addAll(filterButton, addCar, carTable);

            //басқанда көлік карточкасын көрсету
            carTable.setRowFactory(tv -> {
                TableRow<Car> row = new TableRow<>();
                row.setOnMouseClicked(event -> {
                    if (event.getClickCount() == 1 && (!row.isEmpty())) {
                        Car rowData = row.getItem();
                        showCarCardScene(rowData);
                    }
                });
                return row;
            });
        }

        public VBox getView() {
            return layout;
        }


        private void showAddCarForm() {
            VBox formContainer = new VBox(10);
            formContainer.setPadding(new Insets(10));

            // Back button at the top
            Button backButton = new Button("Back");
            formContainer.getChildren().add(backButton);

            GridPane formGrid = new GridPane();
            formGrid.setHgap(10);
            formGrid.setVgap(10);
            formGrid.setPadding(new Insets(10));

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

            // Layout the fields in a grid
            formGrid.add(new Label("VIN:"), 0, 0);
            formGrid.add(vinField, 1, 0);

            formGrid.add(new Label("Brand:"), 0, 1);
            formGrid.add(brandField, 1, 1);

            formGrid.add(new Label("Model:"), 0, 2);
            formGrid.add(modelField, 1, 2);

            formGrid.add(new Label("Year:"), 0, 3);
            formGrid.add(yearField, 1, 3);

            formGrid.add(new Label("Color:"), 0, 4);
            formGrid.add(colorField, 1, 4);

            formGrid.add(new Label("Engine Volume:"), 2, 0);
            formGrid.add(engineVolumeField, 3, 0);

            formGrid.add(new Label("Mileage:"), 2, 1);
            formGrid.add(mileageField, 3, 1);

            formGrid.add(new Label("Transmission:"), 2, 2);
            formGrid.add(transmissionBox, 3, 2);

            formGrid.add(new Label("Drive Type:"), 2, 3);
            formGrid.add(driveTypeBox, 3, 3);

            formGrid.add(new Label("Body Type:"), 2, 4);
            formGrid.add(bodyTypeBox, 3, 4);

            formGrid.add(new Label("Price:"), 0, 5);
            formGrid.add(priceField, 1, 5);

            formGrid.add(new Label("Fuel Type:"), 2, 5);
            formGrid.add(fuelTypeBox, 3, 5);

            formContainer.getChildren().addAll(formGrid, addButton);

            backButton.setOnAction(e -> {
                layout.getChildren().remove(formContainer);
                carTable.setVisible(true);
                filterButton.setVisible(true);
                addCar.setVisible(true);
                loadAllCars();
            });

            addButton.setOnAction(e -> {
                try {
                    Car car = new Car(
                            Integer.parseInt(vinField.getText().trim()),
                            brandField.getText().trim(),
                            modelField.getText().trim(),
                            Integer.parseInt(yearField.getText().trim()),
                            colorField.getText().trim(),
                            Double.parseDouble(engineVolumeField.getText().trim()),
                            Integer.parseInt(mileageField.getText().trim()),
                            transmissionBox.getValue().toString(),
                            driveTypeBox.getValue().toString(),
                            bodyTypeBox.getValue().toString(),
                            Double.parseDouble(priceField.getText().trim()),
                            fuelTypeBox.getValue().toString()
                    );

                    carService.addCar(car);
                    showAlert("Success", "Car added successfully!");
                    layout.getChildren().remove(formContainer);
                    loadAllCars();
                    carTable.setVisible(true);
                    filterButton.setVisible(true);
                    addCar.setVisible(true);

                } catch (Exception ex) {
                    ex.printStackTrace();
                    showAlert("Error", "Error adding car!");
                }
            });

            layout.getChildren().add(formContainer);
        }


        public void loadAllCars() {
            try {
                List<Car> cars = carService.getAllCars();
                carTable.getItems().setAll(cars);
            } catch (SQLException e) {
                showAlert("Error", "Error loading cars!");
            }
        }

    public void showCarCardScene(Car car) {
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));

        Button buyButton = new Button("Buy");
        Button deleteButton = new Button("Delete");

        if (User.isAdmin) {
            root.getChildren().addAll(
                    new Label("VIN: " + car.getVin()),
                    new Label("Brand: " + car.getBrand()),
                    new Label("Model: " + car.getModel()),
                    new Label("Year: " + car.getYear()),
                    new Label("Color: " + car.getColor()),
                    new Label("Engine Volume: " + car.getEngineVolume() + " L"),
                    new Label("Mileage: " + car.getMileage() + " km"),
                    new Label("Transmission: " + car.getTransmission()),
                    new Label("Drive Type: " + car.getDriveType()),
                    new Label("Body Type: " + car.getBodyType()),
                    new Label("Price: " + String.format("%,.0f", car.getPrice()) + " ₸"),
                    new Label("Fuel Type: " + car.getFuelType()),
                    buyButton, deleteButton
            );
        } else {
            root.getChildren().addAll(
                    new Label("VIN: " + car.getVin()),
                    new Label("Brand: " + car.getBrand()),
                    new Label("Model: " + car.getModel()),
                    new Label("Year: " + car.getYear()),
                    new Label("Color: " + car.getColor()),
                    new Label("Engine Volume: " + car.getEngineVolume() + " L"),
                    new Label("Mileage: " + car.getMileage() + " km"),
                    new Label("Transmission: " + car.getTransmission()),
                    new Label("Drive Type: " + car.getDriveType()),
                    new Label("Body Type: " + car.getBodyType()),
                    new Label("Price: " + String.format("%,.0f", car.getPrice()) + " ₸"),
                    new Label("Fuel Type: " + car.getFuelType()),
                    buyButton);
        }

        Scene scene = new Scene(root, 400, 450);
        Stage carStage = new Stage();
        carStage.setTitle("Car Details");
        carStage.setScene(scene);
        carStage.show();

        Button backButton = new Button("Back");
        root.getChildren().add(backButton);

        backButton.setOnAction(e -> {
            carStage.close();
            showCarListScene();
        });

        buyButton.setOnAction(e -> {
            try {
                carService.buyCar(car.getVin());
                showAlert("Success", "Car bought successfully!");

            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        deleteButton.setOnAction(e -> {
            try {
                carService.deleteCar(car.getVin());
                showAlert("Success", "Car deleted successfully!");
                loadAllCars();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });


    }
    public void showAlert(String title, String content)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }



}
