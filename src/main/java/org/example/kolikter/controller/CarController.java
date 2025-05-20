package org.example.kolikter.controller;

import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.*;
import javafx.geometry.Insets;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.kolikter.model.*;
import org.example.kolikter.services.CarService;

import javax.swing.*;
import java.sql.SQLException;
import java.util.List;

public class CarController {
    private Stage mainStage;

    private CarService carService = new CarService();

    private Button filterByPriceButton = new Button("Filter by Price");

    private TableView<Car> carTable = new TableView<>();
    private TextField minPriceField = new TextField();
    private TextField maxPriceField = new TextField();
    private TextField car_name = new TextField();
    private Button filterByBrand = new Button("Filter by Brand");
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

        carTable.getColumns().addAll(
                brandCol, modelCol, priceCol,
                yearCol, colorCol, engineVolumeCol,
                mileageCol, transmissionCol, driveTypeCol,
                bodyTypeCol, fuelTypeCol
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

        minPriceField.setPromptText("Min price");
        maxPriceField.setPromptText("Max price");
        car_name.setPromptText("Car name");

        filterButton.setOnAction(e -> {
            if (!layout.getChildren().contains(car_name)) {
                layout.getChildren().addAll(car_name, filterByBrand, minPriceField, maxPriceField, filterByPriceButton);
            }
            filterByBrand.setVisible(true);
            filterByPriceButton.setVisible(true);
        });

        addCar.setOnAction(e -> {
            showAddCarForm();
            carTable.setVisible(false);
            filterButton.setVisible(false);
            filterByBrand.setVisible(false);
            filterByPriceButton.setVisible(false);
            addCar.setVisible(false);
        });

        filterByBrand.setOnAction(e -> filterByBrand());
        filterByPriceButton.setOnAction(e -> filterByPrice());

        layout.getChildren().addAll(filterButton, addCar, carTable);

        // Екі рет басқанда көлік карточкасын көрсету
        carTable.setRowFactory(tv -> {
            TableRow<Car> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
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

    public void filterByBrand() {
        try {
            String br = car_name.getText().trim();
            if (br.isEmpty()) {
                loadAllCars();
            } else {
                List<Car> cars = carService.filterByBrand(br);
                carTable.getItems().setAll(cars);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error filtering by brand: " + e.getMessage());
        }
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
            if (!layout.getChildren().contains(car_name)) {
                layout.getChildren().addAll(car_name, filterByBrand, minPriceField, maxPriceField, filterByPriceButton);
            }
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
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Car added successfully!");
                alert.showAndWait();
                layout.getChildren().remove(formContainer);
                loadAllCars();
                carTable.setVisible(true);
                filterButton.setVisible(true);
                addCar.setVisible(true);
//                if (!layout.getChildren().contains(car_name)) {
//                    layout.getChildren().addAll(car_name, filterByBrand, minPriceField, maxPriceField, filterByPriceButton);
//                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        });

        layout.getChildren().add(formContainer);
    }


    public void loadAllCars() {
        try {
            List<Car> cars = carService.getAllCars();
            carTable.getItems().setAll(cars);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading cars: " + e.getMessage());
        }
    }

    private void filterByPrice() {
        try {
            double min = Double.parseDouble(minPriceField.getText().trim());
            double max = Double.parseDouble(maxPriceField.getText().trim());
            if (min > max) {
                JOptionPane.showMessageDialog(null, "Min price cannot be greater than max price.");
                return;
            }
            List<Car> filteredCars = carService.getCarsByPriceRange(min, max);
            carTable.getItems().setAll(filteredCars);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Please enter valid numeric values for price.");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error filtering by price: " + e.getMessage());
        }
    }

//    public void showCarCardScene(Car car) {
//        VBox root = new VBox(10);
//        root.setPadding(new Insets(15));
//
//        root.getChildren().addAll(
//                new Label("VIN: " + car.getVin()),
//                new Label("Марка: " + car.getBrand()),
//                new Label("Модель: " + car.getModel()),
//                new Label("Жыл: " + car.getYear()),
//                new Label("Түсі: " + car.getColor()),
//                new Label("Қозғалтқыш көлемі: " + car.getEngineVolume() + " л"),
//                new Label("Жүрісі: " + car.getMileage() + " км"),
//                new Label("Қорап: " + car.getTransmission()),
//                new Label("Привод: " + car.getDriveType()),
//                new Label("Кузов: " + car.getBodyType()),
//                new Label("Бағасы: " + String.format("%,.0f ₸", car.getPrice())),
//                new Label("Отын түрі: " + car.getFuelType())
//        );
//
//        Button backButton = new Button("Артқа");
//        backButton.setOnAction(e -> showCarListScene());
//        root.getChildren().add(backButton);
//
//        Scene scene = new Scene(root, 400, 500);
//        mainStage.setTitle(car.getBrand() + " " + car.getModel());
//        mainStage.setScene(scene);
//    }
public void showCarCardScene(Car car) {
    VBox root = new VBox(10);
    root.setPadding(new Insets(15));

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
            new Label("Fuel Type: " + car.getFuelType())
    );

    Button backButton = new Button("Back");
    root.getChildren().add(backButton);

    Scene scene = new Scene(root, 400, 400);
    Stage carStage = new Stage();
    carStage.setTitle("Car Details");
    carStage.setScene(scene);
    carStage.show();

    backButton.setOnAction(e -> carStage.close());
}


    public void showCarListScene() {
        Scene scene = new Scene(layout, 800, 600);
        mainStage.setTitle("Көлік тізімі");
        mainStage.setScene(scene);
    }
}
