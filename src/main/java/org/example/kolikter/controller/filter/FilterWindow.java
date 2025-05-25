package org.example.kolikter.controller.filter;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.kolikter.model.BodyType;
import org.example.kolikter.model.DriveType;
import org.example.kolikter.model.FuelType;
import org.example.kolikter.model.TransmissionType;

import java.util.function.Consumer;



public class FilterWindow {

    public static void showFilterDialog(Consumer<CarFilter> onApplyCallback) {
        Stage stage = new Stage();
        stage.setTitle("Filter");
        stage.initModality(Modality.APPLICATION_MODAL);

        VBox root = new VBox(10);
        root.setStyle("-fx-padding: 20");

        TextField brandField = new TextField();
        TextField modelField = new TextField();
        TextField yearField = new TextField();
        TextField colorField = new TextField();
        TextField engineVolumeField = new TextField();
        TextField mileageField = new TextField();
        TextField minPriceField = new TextField();
        TextField maxPriceField = new TextField();

        ComboBox<TransmissionType> transmissionBox = new ComboBox<>();
        transmissionBox.getItems().addAll(TransmissionType.values());
        transmissionBox.setPromptText("");

        ComboBox<DriveType> driveTypeBox = new ComboBox<>();
        driveTypeBox.getItems().addAll(DriveType.values());
        driveTypeBox.setPromptText("");

        ComboBox<BodyType> bodyTypeBox = new ComboBox<>();
        bodyTypeBox.getItems().addAll(BodyType.values());
        bodyTypeBox.setPromptText("");

        ComboBox<FuelType> fuelTypeBox = new ComboBox<>();
        fuelTypeBox.getItems().addAll(FuelType.values());
        fuelTypeBox.setPromptText("");

        root.getChildren().addAll(
                labelWithField("Brand", brandField),
                labelWithField("Model", modelField),
                labelWithField("Year", yearField),
                labelWithField("Color", colorField),
                labelWithField("Engine Volume", engineVolumeField),
                labelWithField("Mileage", mileageField),
                labelWithComboBox("Transmission", transmissionBox),
                labelWithComboBox("Drive Type", driveTypeBox),
                labelWithComboBox("Body Type", bodyTypeBox),
                labelWithComboBox("Fuel Type", fuelTypeBox),
                labelWithField("Min Price", minPriceField),
                labelWithField("Max Price", maxPriceField)
        );

        HBox buttons = new HBox(10);
        Button applyBtn = new Button("Apply");
        Button cancelBtn = new Button("Back to list");
        Button clearBtn = new Button("Clear");
        buttons.getChildren().addAll(applyBtn, cancelBtn, clearBtn);

        root.getChildren().add(buttons);

        applyBtn.setOnAction(e -> {
            CarFilter filter = new CarFilter();
            filter.brand = brandField.getText();
            filter.model = modelField.getText();
            filter.year = parseInt(yearField.getText());
            filter.color = colorField.getText();
            filter.engineVolume = parseDouble(engineVolumeField.getText());
            filter.mileage = parseInt(mileageField.getText());

            filter.transmission = transmissionBox.getValue() != null ? transmissionBox.getValue().name() : null;
            filter.driveType = driveTypeBox.getValue() != null ? driveTypeBox.getValue().name() : null;
            filter.bodyType = bodyTypeBox.getValue() != null ? bodyTypeBox.getValue().name() : null;
            filter.fuelType = fuelTypeBox.getValue() != null ? fuelTypeBox.getValue().name() : null;

            filter.minPrice = parseDouble(minPriceField.getText());
            filter.maxPrice = parseDouble(maxPriceField.getText());

            onApplyCallback.accept(filter);
            stage.close();
        });

        cancelBtn   .setOnAction(e -> {

            stage.close();
        });

        clearBtn.setOnAction(e -> {
            brandField.clear();
            modelField.clear();
            yearField.clear();
            colorField.clear();
            engineVolumeField.clear();
            mileageField.clear();
            minPriceField.clear();
            maxPriceField.clear();

            transmissionBox.getSelectionModel().clearSelection();
            driveTypeBox.getSelectionModel().clearSelection();
            bodyTypeBox.getSelectionModel().clearSelection();
            fuelTypeBox.getSelectionModel().clearSelection();
        });

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.showAndWait();
    }

    private static HBox labelWithField(String label, TextField field) {
        Label lbl = new Label(label);
        lbl.setPrefWidth(120);
        return new HBox(10, lbl, field);
    }

    private static HBox labelWithComboBox(String label, ComboBox<?> comboBox) {
        Label lbl = new Label(label);
        lbl.setPrefWidth(120);
        return new HBox(10, lbl, comboBox);
    }

    private static Integer parseInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            return null;
        }
    }

    private static Double parseDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (Exception e) {
            return null;
        }
    }
}
